package lineword.vacation_backend.exception;


import lineword.vacation_backend.exception.global.HolidayHttpStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationNotFoundException extends RuntimeException{
    String message;
    HolidayHttpStatus status = HolidayHttpStatus.BAD_REQUEST;

    public NotificationNotFoundException(int memberId){
        this.message = String.format("알림 찾을 수 없습니다. Member Id: %d",memberId);
    }
}
