package nextstep.subway.unit;

import nextstep.subway.applicaion.LineService;
import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import nextstep.subway.domain.Section;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
public class LineServiceTest {

    private static final String NEW_BUN_DANG = "신분당선";
    private static final String BG_RED_600 = "bg-red-600";

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private LineService lineService;

    @Test
    void addSection() {
        // given
        // stationRepository와 lineRepository를 활용하여 초기값 셋팅
        Long 강남역 = stationRepository.save(new Station("강남역")).getId();
        Long 신논현역 = stationRepository.save(new Station("신논현역")).getId();
        Line 신분당선 = lineRepository.save(new Line(NEW_BUN_DANG, BG_RED_600));

        // when
        // lineService.addSection 호출
        lineService.addSection(신분당선.getId(), SectionRequest.of(강남역, 신논현역, 10));

        // then
        // line.getSections 메서드를 통해 검증
        List<Section> sections = 신분당선.getSections();

        assertAll(
                () -> assertThat(sections).hasSize(1),
                () -> assertThat(sections)
                        .extracting("upStation.name")
                        .containsAnyOf("강남역"),
                () -> assertThat(sections)
                        .extracting("downStation.name")
                        .containsAnyOf("신논현역")
        );
    }
}
