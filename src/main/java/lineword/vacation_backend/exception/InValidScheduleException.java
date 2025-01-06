package lineword.vacation_backend.exception;


import lineword.vacation_backend.exception.global.HolidayHttpStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class InValidScheduleException extends RuntimeException{
    String message;
    HolidayHttpStatus status = HolidayHttpStatus.BAD_REQUEST;

    public InValidScheduleException(){
        this.message = String.format("올바르지 않은 시간입니다. 다시 설정 해주세요.");
    }
}
