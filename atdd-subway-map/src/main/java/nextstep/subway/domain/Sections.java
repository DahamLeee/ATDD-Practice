package nextstep.subway.domain;

import nextstep.subway.exception.AddSectionException;
import nextstep.subway.exception.SectionNotFoundException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();

    public void addSection(Section section) {
        if (sections.isEmpty()) {
            this.sections.add(section);
            return;
        }

        matchLastStationAndNewUpStation(section.getUpStation());

        this.sections.add(section);
    }

    private void matchLastStationAndNewUpStation(Station upStation) {
        if (!lastSection().getDownStation().equals(upStation)) {
            throw new AddSectionException("기존 노선의 종점역과 신규 구간의 상행역이 일치하지 않습니다.");
        }
    }

    private Section lastSection() {
        return sections.stream()
                .filter(section -> matchSectionsUpStation(section.getDownStation()))
                .findFirst()
                .orElseThrow(() -> new SectionNotFoundException("하행 종점 구간이 존재하지 않습니다."));
    }

    private boolean matchSectionsUpStation(Station downStation) {
        return sections.stream()
                .noneMatch(matchUpStation(downStation));
    }

    private Predicate<Section> matchUpStation(Station station) {
        return section -> section.getUpStation().equals(station);
    }

    public List<Station> allStations() {
        if (this.sections.isEmpty()) {
            return Collections.emptyList();
        }

        Station upStation = findFirstUpStation();
        List<Station> stations = new ArrayList<>();
        stations.add(upStation);

        while (true) {
            Station currentUpStation = upStation;
            Optional<Section> section = findSectionAsUpStation(currentUpStation);

            if (section.isEmpty()) {
                break;
            }

            upStation = section.get().getDownStation();
            stations.add(upStation);
        }

        return stations;
    }

    private Station findFirstUpStation() {
        List<Station> upStations = this.sections.stream()
                .map(Section::getUpStation)
                .collect(Collectors.toList());

        List<Station> downStations = this.sections.stream()
                .map(Section::getDownStation)
                .collect(Collectors.toList());

        return upStations.stream()
                .filter(it -> !downStations.contains(it))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private Optional<Section> findSectionAsUpStation(Station currentUpStation) {
        return this.sections.stream()
                .filter(it -> it.isSameUpStation(currentUpStation))
                .findFirst();
    }
}
