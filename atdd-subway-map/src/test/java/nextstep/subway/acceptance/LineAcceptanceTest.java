package nextstep.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.applicaion.dto.LineRequest;
import nextstep.subway.applicaion.dto.StationRequest;
import nextstep.subway.applicaion.dto.StationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nextstep.subway.acceptance.LineSteps.지하철_노선_목록_조회;
import static nextstep.subway.acceptance.LineSteps.지하철_노선_생성;
import static nextstep.subway.acceptance.LineSteps.지하철_노선_조회;
import static nextstep.subway.acceptance.StationSteps.지하철역_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("지하철 노선 관련 기능")
public class LineAcceptanceTest extends AcceptanceTest {

    private Long 강남역;
    private Long 신논현역;
    private Long 정자역;

    @BeforeEach
    void initData() {
        강남역 = 지하철역_생성(StationRequest.from("강남역")).jsonPath().getLong("id");
        신논현역 = 지하철역_생성(StationRequest.from("신논현역")).jsonPath().getLong("id");
        정자역 = 지하철역_생성(StationRequest.from("정자역")).jsonPath().getLong("id");
    }

    /**
     * When 지하철 노선을 생성하면
     * Then 지하철 노선 목록 조회 시 노선을 찾을 수 있다.
     */
    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createLine() {
        // when
        지하철_노선_생성(LineRequest.of("신분당선", "bg-red-600", 강남역, 신논현역, 10));

        // then
        List<String> lineNames = 지하철_노선_목록_조회().jsonPath().getList("name", String.class);

        assertAll(
                () -> assertThat(lineNames).hasSize(1),
                () -> assertThat(lineNames).containsAnyOf("신분당선")
        );
    }

    /**
     * Given 2개의 지하철 노선을 생성하고
     * When 지하철 노선 목록을 조회하면
     * Then 지하철 노선 목록 조회 시 2개의 노선을 조회할 수 있다.
     */
    @DisplayName("지하철 노선 목록을 조회한다.")
    @Test
    void getLines() {
        // given
        지하철_노선_생성(LineRequest.of("신분당선", "bg-red-600", 강남역, 신논현역, 10));
        지하철_노선_생성(LineRequest.of("분당선", "bg-yellow-600", 신논현역, 정자역, 20));

        // when
        ExtractableResponse<Response> response = 지하철_노선_목록_조회();

        // then
        List<String> lineNames = response.jsonPath().getList("name", String.class);

        assertAll(
                () -> assertThat(lineNames).hasSize(2),
                () -> assertThat(lineNames).containsAnyOf("신분당선", "분당선")
        );
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 생성한 지하철 노선을 조회하면
     * Then 생성한 지하철 노선의 정보를 응답받을 수 있다.
     */
    @DisplayName("특정 지하철 노선을 조회한다.")
    @Test
    void getSpecificLine() {
        // given
        Long 신분당선 = 지하철_노선_생성(LineRequest.of("신분당선", "bg-red-600", 강남역, 신논현역, 10))
                .jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> response = 지하철_노선_조회(신분당선);

        // then
        String lineName = response.jsonPath().getString("name");
        List<StationResponse> stations = response.jsonPath().getList("stations", StationResponse.class);

        assertAll(
                () -> assertThat(lineName).isEqualTo("신분당선"),
                () -> assertThat(stations).hasSize(2),
                () -> assertThat(stations).extracting("name").containsAnyOf("강남역", "신논현역")
        );
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 생성한 지하철 노선을 수정하면
     * Then 해당 지하철 노선 정보는 수정된다
     */
    @DisplayName("지하철 노선을 수정한다.")
    @Test
    void updateLine() {
        // given
        // when
        // then
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 생성한 지하철 노선을 삭제하면
     * Then 해당 지하철 노선 정보는 삭제된다
     */
    @DisplayName("지하철 노선을 삭제한다.")
    @Test
    void deleteLine() {
        // given
        // when
        // then
    }
}
