package lineword.vacation_backend.exception;


import lineword.vacation_backend.exception.global.HolidayHttpStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoRemainedHolidayException extends RuntimeException{
    String message;
    HolidayHttpStatus status = HolidayHttpStatus.BAD_REQUEST;

    public NoRemainedHolidayException(int givenRemainedHoliday){
        this.message = String.format("잔여 휴가가 부족합니다. 남은 휴가일 수 %d",givenRemainedHoliday);
    }
}
