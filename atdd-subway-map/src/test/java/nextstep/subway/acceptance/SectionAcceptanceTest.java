package nextstep.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.applicaion.dto.LineRequest;
import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.applicaion.dto.StationRequest;
import nextstep.subway.applicaion.dto.StationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static nextstep.subway.acceptance.LineSteps.지하철_구간_추가;
import static nextstep.subway.acceptance.LineSteps.지하철_노선_생성;
import static nextstep.subway.acceptance.LineSteps.지하철_노선_조회;
import static nextstep.subway.acceptance.StationSteps.지하철역_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("지하철 구간 관련 기능")
public class SectionAcceptanceTest extends AcceptanceTest {

    private Long 강남역;
    private Long 신논현역;
    private Long 정자역;
    private Long 판교역;

    @BeforeEach
    void initData() {
        강남역 = 지하철역_생성(StationRequest.from("강남역")).jsonPath().getLong("id");
        신논현역 = 지하철역_생성(StationRequest.from("신논현역")).jsonPath().getLong("id");
        정자역 = 지하철역_생성(StationRequest.from("정자역")).jsonPath().getLong("id");
        판교역 = 지하철역_생성(StationRequest.from("판교역")).jsonPath().getLong("id");
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 지하철 구간을 추가하면
     * Then 지하철 노선 조회시 추가 된 지하철 역을 조회할 수 있다.
     */
    @DisplayName("지하철 구간 등록")
    @Test
    void addSection() {
        // given
        Long 신분당선 = 지하철_노선_생성(LineRequest.of("신분당선", "bg-red-600", 강남역, 신논현역, 10))
                .jsonPath()
                .getLong("id");

        // when
        지하철_구간_추가(신분당선, SectionRequest.of(신논현역, 정자역, 15));

        // then
        List<StationResponse> stations =
                지하철_노선_조회(신분당선).jsonPath().getList("stations", StationResponse.class);

        assertAll(
                () -> assertThat(stations).hasSize(3),
                () -> assertThat(stations)
                        .extracting("name")
                        .containsAnyOf("강남역", "신논현역", "정자역")
        );
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 기존 노선의 종점역과 새로운 노선의 상행선이 일치하지 않도록 추가하면
     * Then 지하철 구간 등록은 예외를 발생시킨다.
     */
    @DisplayName("지하철 구간 등록 예외(하행역과 상행역 불일치)")
    @Test
    void addSectionUnmatchedException() {
        // given
        Long 신분당선 = 지하철_노선_생성(LineRequest.of("신분당선", "bg-red-600", 강남역, 신논현역, 10))
                .jsonPath()
                .getLong("id");

        // when
        ExtractableResponse<Response> response = 지하철_구간_추가(신분당선, SectionRequest.of(정자역, 판교역, 15));

        // then
        String exceptionMessage = response.jsonPath().getString("message");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(exceptionMessage).isEqualTo("기존 노선의 종점역과 신규 구간의 상행역이 일치하지 않습니다.")
        );
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 새로운 노선이 이미 존재하는 노선의 중복되는 역을 추가하면
     * Then 지하철 노선은 정상적으로 추가되지 않는다.
     */
    @DisplayName("지하철 구간 등록 예외(이미 존재하는 역 등록)")
    @Test
    void addSectionAlreadyExistsStationException() {

    }

    /**
     * Given 지하철 노선을 생성하고
     * Given 지하철 구간을 추가하고
     * When 노선의 마지막 구간을 제거하면
     * Then 지하철 노선 조회시 해당 구간 정보는 삭제된다.
     */
    @DisplayName("지하철 구간 삭제")
    @Test
    void deleteSection() {

    }

    /**
     * Given 지하철 노선을 생성하고
     * Given 지하철 구간을 추가하고
     * When 구간에 존재하지 않는 역을 삭제하면
     * Then 지하철 구간 삭제 오류가 발생한다.
     */
    @DisplayName("지하철 구간 삭제 예외(존재하지 않는 역 삭제)")
    @Test
    void deleteSectionWhenNotExistsStation() {

    }

    /**
     * Given 지하철 노선을 생성하고
     * When 지하철 구간을 삭제하면
     * Then 지하철 구간 삭제 오류가 발생한다.
     */
    @DisplayName("지하철 구간 삭제 오류(구간이 1개인 노선)")
    @Test
    void deleteSectionOnlyOneSectionException() {

    }
}
