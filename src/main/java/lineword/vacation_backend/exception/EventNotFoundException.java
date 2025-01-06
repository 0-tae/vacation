package lineword.vacation_backend.exception;


import lineword.vacation_backend.domain.Member;
import lineword.vacation_backend.exception.global.HolidayHttpStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventNotFoundException extends RuntimeException{
    String message;
    HolidayHttpStatus status = HolidayHttpStatus.NOT_FOUND;

    public EventNotFoundException(String event){
        this.message = String.format("이벤트를 찾을 수 없습니다. 입력된 이벤트: %s",event);
    }
}
