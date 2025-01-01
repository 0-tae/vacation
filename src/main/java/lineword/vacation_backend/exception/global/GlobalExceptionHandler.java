package lineword.vacation_backend.exception.global;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
//    @ExceptionHandler(ChatSessionNotFoundException.class)
//    protected ResponseEntity handleChatSessionNotFoundException(ChatSessionNotFoundException ex){
//        log.warn("[{}, response: {}]",ex.getMessage(),ex.getStatus().getCode());
//        return ResponseEntity
//                .status(ex.getStatus().getCode())
//                .body(BackendApiResponseDto.builder()
//                    .resultCode(ex.getStatus().getCode())
//                    .resultMessage(ex.getMessage())
//                    .build());
//    }
}

