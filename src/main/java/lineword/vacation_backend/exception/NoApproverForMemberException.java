package lineword.vacation_backend.exception;


import lineword.vacation_backend.domain.Member;
import lineword.vacation_backend.exception.global.HolidayHttpStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoApproverForMemberException extends RuntimeException{
    String message;
    HolidayHttpStatus status = HolidayHttpStatus.BAD_REQUEST;

    public NoApproverForMemberException(Member member){
        this.message = String.format("해당 멤버에 대해 결재권자가 존재하지 않습니다. "+member);
    }
}
