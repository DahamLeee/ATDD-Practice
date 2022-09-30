package nextstep.subway.applicaion.dto;

import nextstep.subway.domain.Line;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineResponse {

    private Long id;
    private String name;
    private String color;

    private final List<StationResponse> stations = new ArrayList<>();

    private LineResponse() { }

    private LineResponse(Line line) {
        this.id = line.getId();
        this.name = line.getName();
        this.color = line.getColor();
        stations.add(StationResponse.createStationResponse(line.getUpStation()));
        stations.add(StationResponse.createStationResponse(line.getDownStation()));
    }

    public static LineResponse from(Line line) {
        return new LineResponse(line);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<StationResponse> getStations() {
        return Collections.unmodifiableList(stations);
    }
}
