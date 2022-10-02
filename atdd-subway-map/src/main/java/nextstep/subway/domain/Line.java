package nextstep.subway.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collections;
import java.util.List;

@Entity
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;

    @Embedded
    private Sections sections = new Sections();

    protected Line() { }

    private Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public static Line of(String name, String color) {
        return new Line(name, color);
    }

    public void addSection(SectionBuilder sectionBuilder) {
        sections.addSection(Section.of(this, sectionBuilder.upStation, sectionBuilder.downStation, sectionBuilder.distance));
    }

    public void change(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public List<Station> allStations() {
        return Collections.unmodifiableList(sections.allStations());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public static class SectionBuilder {
        private Station upStation;
        private Station downStation;
        private int distance;

        public SectionBuilder() { }

        public SectionBuilder(SectionBuilder sectionBuilder) {
            this.upStation = sectionBuilder.upStation;
            this.downStation = sectionBuilder.downStation;
            this.distance = sectionBuilder.distance;
        }

        public SectionBuilder upStation(Station upStation) {
            this.upStation = upStation;
            return this;
        }

        public SectionBuilder downStation(Station downStation) {
            this.downStation = downStation;
            return this;
        }

        public SectionBuilder distance(int distance) {
            this.distance = distance;
            return this;
        }

        public SectionBuilder build() {
            return new SectionBuilder(this);
        }
    }
}
