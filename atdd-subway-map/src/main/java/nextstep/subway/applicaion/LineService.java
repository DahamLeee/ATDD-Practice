package nextstep.subway.applicaion;

import nextstep.subway.applicaion.dto.LineChangeRequest;
import nextstep.subway.applicaion.dto.LineRequest;
import nextstep.subway.applicaion.dto.LineResponse;
import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import nextstep.subway.domain.Station;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class LineService {

    private final LineRepository lineRepository;
    private final StationService stationService;

    public LineService(LineRepository lineRepository, StationService stationService) {
        this.lineRepository = lineRepository;
        this.stationService = stationService;
    }

    @Transactional
    public LineResponse saveLine(LineRequest request) {
        Station upStation = stationService.findStationById(request.getUpStationId());
        Station downStation = stationService.findStationById(request.getDownStationId());

        Line savedLine = lineRepository.save(
                Line.of(request.getName(), request.getColor())
        );

        savedLine.addSection(new Line.SectionBuilder()
                .upStation(upStation)
                .downStation(downStation)
                .distance(request.getDistance())
                .build()
        );

        return LineResponse.from(savedLine);
    }

    public List<LineResponse> findAllLines() {
        return lineRepository.findAll()
                .stream()
                .map(LineResponse::from)
                .collect(Collectors.toList());
    }

    public LineResponse findLine(Long lineId) {
        Line findLine = findLineById(lineId);
        return LineResponse.from(findLine);
    }

    private Line findLineById(Long lineId) {
        return lineRepository.findById(lineId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 노선입니다."));
    }

    @Transactional
    public void changeLine(Long id, LineChangeRequest request) {
        Line findLine = findLineById(id);
        findLine.change(request.getName(), request.getColor());
    }

    @Transactional
    public void deleteLine(Long id) {
        lineRepository.deleteById(id);
    }

    @Transactional
    public void addSection(Long id, SectionRequest request) {
        Line findLine = findLineById(id);

        Station upStation = stationService.findStationById(request.getUpStationId());
        Station downStation = stationService.findStationById(request.getDownStationId());

        findLine.addSection(
                new Line.SectionBuilder()
                        .upStation(upStation)
                        .downStation(downStation)
                        .distance(request.getDistance())
        );
    }

    @Transactional
    public void deleteSection(Long lineId, Long stationId) {
        Line findLine = findLineById(lineId);
        Station findStation = stationService.findStationById(stationId);

        findLine.deleteSection(findStation);
    }
}
