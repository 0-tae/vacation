package lineword.vacation_backend.exception;


import lineword.vacation_backend.exception.global.HolidayHttpStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberNotFoundByEmailException extends RuntimeException{
    String message;
    HolidayHttpStatus status = HolidayHttpStatus.NOT_FOUND;

    public MemberNotFoundByEmailException(String email){
        this.message = String.format("유저를 찾을 수 없습니다. 이메일: %s",email);
    }
}
