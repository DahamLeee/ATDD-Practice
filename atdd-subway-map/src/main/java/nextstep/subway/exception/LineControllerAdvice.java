package nextstep.subway.exception;

import nextstep.subway.applicaion.dto.ErrorResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LineControllerAdvice {

    @ExceptionHandler(AddSectionException.class)
    public ResponseEntity<ErrorResult> addSectionException(AddSectionException e) {
        return ResponseEntity.ok(ErrorResult.from(e.getMessage()));
    }

    @ExceptionHandler(DeleteSectionException.class)
    public ResponseEntity<ErrorResult> deleteSectionException(DeleteSectionException e) {
        return ResponseEntity.ok(ErrorResult.from(e.getMessage()));
    }
}
