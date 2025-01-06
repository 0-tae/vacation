package lineword.vacation_backend.exception;


import lineword.vacation_backend.domain.Member;
import lineword.vacation_backend.exception.global.HolidayHttpStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HolidayNotFoundByMemberException extends RuntimeException{
    String message;
    HolidayHttpStatus status = HolidayHttpStatus.NOT_FOUND;

    public HolidayNotFoundByMemberException(Member member){
        this.message = String.format("휴가를 찾을 수 없습니다. 멤버: %s",member);
    }
}
