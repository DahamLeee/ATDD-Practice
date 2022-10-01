package nextstep.subway.applicaion.dto;

public class LineChangeRequest {

    private String name;
    private String color;

    private LineChangeRequest() { }

    private LineChangeRequest(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public static LineChangeRequest of(String name, String color) {
        return new LineChangeRequest(name, color);
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
