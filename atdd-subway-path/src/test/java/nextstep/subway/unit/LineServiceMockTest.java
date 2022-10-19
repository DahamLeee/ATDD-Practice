package nextstep.subway.unit;

import nextstep.subway.applicaion.LineService;
import nextstep.subway.applicaion.StationService;
import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static nextstep.subway.fixture.ConstStation.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LineServiceMockTest {

    private static final String NEW_BUN_DANG = "신분당선";
    private static final String BG_RED_600 = "bg-red-600";
    @Mock
    private LineRepository lineRepository;
    @Mock
    private StationService stationService;

    @Test
    void addSection() {
        // given
        // lineRepository, stationService stub 설정을 통해 초기값 셋팅
        when(stationService.findById(1L)).thenReturn(강남역);
        when(stationService.findById(2L)).thenReturn(신논현역);
        when(lineRepository.findById(1L)).thenReturn(Optional.of(new Line(NEW_BUN_DANG, BG_RED_600)));
        LineService lineService = new LineService(lineRepository, stationService);

        // when
        // lineService.addSection 호출
        lineService.addSection(1L, SectionRequest.of(1L, 2L, 10));

        // then
        // line.findLineById 메서드를 통해 검증
        Line findLine = lineRepository.findById(1L).get();
        assertThat(findLine.getSections()).hasSize(1);
    }
}
