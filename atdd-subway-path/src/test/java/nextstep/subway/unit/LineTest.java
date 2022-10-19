package nextstep.subway.unit;

import nextstep.subway.domain.Line;
import nextstep.subway.domain.Section;
import org.junit.jupiter.api.Test;

import static nextstep.subway.fixture.ConstStation.강남역;
import static nextstep.subway.fixture.ConstStation.신논현역;
import static nextstep.subway.fixture.ConstStation.정자역;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class LineTest {

    @Test
    void addSection() {
        Line line = new Line("신분당선", "bg-red-600");
        line.addSection(new Section(line, 강남역, 신논현역, 10));
        line.addSection(new Section(line, 신논현역, 정자역, 20));

        assertAll(
                () -> assertThat(line.getSections()).hasSize(2),
                () -> assertThat(line.getSections())
                        .extracting("upStation")
                        .containsAnyOf(강남역, 신논현역),
                () -> assertThat(line.getSections())
                        .extracting("downStation")
                        .containsAnyOf(신논현역, 정자역)
        );
    }

    @Test
    void getStations() {
    }

    @Test
    void removeSection() {
    }
}
