package nextstep.subway.applicaion.dto;

public class ErrorResult {

    private String message;

    private ErrorResult() { }

    private ErrorResult(String message) {
        this.message = message;
    }

    public static ErrorResult from(String message) {
        return new ErrorResult(message);
    }

    public String getMessage() {
        return message;
    }
}
