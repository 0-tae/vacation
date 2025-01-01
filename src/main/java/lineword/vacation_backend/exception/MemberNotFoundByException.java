package lineword.vacation_backend.exception;


import lineword.vacation_backend.exception.global.HolidayHttpStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberNotFoundByException extends RuntimeException{
    String message;
    HolidayHttpStatus status = HolidayHttpStatus.BAD_REQUEST;

    public MemberNotFoundByException(int id){
        this.message = String.format("유저를 찾을 수 없습니다. 아이디: %d",id);
    }
}
