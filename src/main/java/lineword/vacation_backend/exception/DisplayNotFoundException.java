package lineword.vacation_backend.exception;


import lineword.vacation_backend.exception.global.HolidayHttpStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DisplayNotFoundException extends RuntimeException{
    String message;
    HolidayHttpStatus status = HolidayHttpStatus.NOT_FOUND;

    public DisplayNotFoundException(Integer id){
        this.message = String.format("디스플레이를 찾을 수 없습니다. ID: %d",id);
    }
}
