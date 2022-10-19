package nextstep.subway.domain;

import nextstep.subway.exception.AddSectionException;
import nextstep.subway.exception.SectionNotFoundException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Embeddable
public class Sections {

    @OneToMany(
            mappedBy = "line",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();

    public void addSection(Section section) {
        if (sections.isEmpty()) {
            this.sections.add(section);
            return;
        }

        checkDuplicateSection(section);
        matchLastStationAndNewUpStation(section.getUpStation());
    }

    private void checkDuplicateSection(Section section) {
        sections.stream()
                .filter(it -> it.isSameUpStation(section.getDownStation()) || it.isSameDownStation(section.getDownStation()))
                .findFirst()
                .ifPresent(it -> {
                    throw new AddSectionException("새로운 구간의 하행역은 해당 노선에 등록되어 있는 역일 수 없습니다.");
                });
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

    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }
}
