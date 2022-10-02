package nextstep.subway.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();

    public void addSection(Section section) {
        this.sections.add(section);
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
