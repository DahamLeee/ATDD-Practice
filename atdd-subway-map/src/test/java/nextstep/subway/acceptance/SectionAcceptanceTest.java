package nextstep.subway.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("지하철 구간 관련 기능")
public class SectionAcceptanceTest extends AcceptanceTest {

    /**
     * Given 지하철 노선을 생성하고
     * When 지하철 구간을 추가하면
     * Then 지하철 노선 조회시 추가 된 지하철 역을 조회할 수 있다.
     */
    @DisplayName("지하철 구간 등록")
    @Test
    void addSection() {

    }

    /**
     * Given 지하철 노선을 생성하고
     * When 기존 노선의 종점역과 새로운 노선의 상행선이 일치하지 않도록 추가하면
     * Then 지하철 구간 등록은 예외를 발생시킨다.
     */
    @DisplayName("지하철 구간 등록 예외(하행역과 상행역 불일치)")
    @Test
    void addSectionUnmatchedException() {

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
