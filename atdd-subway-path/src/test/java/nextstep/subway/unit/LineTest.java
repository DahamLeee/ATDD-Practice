package nextstep.subway.unit;

import nextstep.subway.domain.Line;
import nextstep.subway.domain.Section;
import nextstep.subway.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class LineTest {

    private Station 강남역;
    private Station 신논현역;

    @BeforeEach
    void setUp() {
        강남역 = new Station("강남역");
        신논현역 = new Station("신논현역");
    }

    @Test
    void addSection() {
        Line line = new Line("신분당선", "bg-red-600");
        line.addSection(new Section(line, 강남역, 신논현역, 10));

        assertAll(
                () -> assertThat(line.getSections()).hasSize(1),
                () -> assertThat(line.getSections())
                        .extracting("upStation")
                        .containsAnyOf(강남역),
                () -> assertThat(line.getSections())
                        .extracting("downStation")
                        .containsAnyOf(신논현역)
        );
    }

    @Test
    void getStations() {
    }

    @Test
    void removeSection() {
    }
}
