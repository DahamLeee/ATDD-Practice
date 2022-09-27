package nextstep.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.applicaion.dto.StationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class StationSteps {

    public static ExtractableResponse<Response> 지하철역_생성(StationRequest stationRequest) {
        return RestAssured.given().log().all()
                .body(stationRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/stations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    public static ExtractableResponse<Response> 지하철역_목록_조회() {
        return RestAssured.given().log().all()
                .when().get("/stations")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }
}
