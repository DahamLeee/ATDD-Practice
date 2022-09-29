package nextstep.subway.ui;

import nextstep.subway.applicaion.LineService;
import org.springframework.stereotype.Controller;

@Controller
public class LineController {

    private final LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

}
