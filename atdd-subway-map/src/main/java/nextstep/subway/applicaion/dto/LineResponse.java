package nextstep.subway.applicaion.dto;

import nextstep.subway.domain.Line;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LineResponse {

    private Long id;
    private String name;
    private String color;

    private List<StationResponse> stations;

    private LineResponse() { }

    private LineResponse(Line line) {
        this.id = line.getId();
        this.name = line.getName();
        this.color = line.getColor();
        this.stations = line.allStations()
                .stream()
                .map(StationResponse::createStationResponse)
                .collect(Collectors.toList());
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
