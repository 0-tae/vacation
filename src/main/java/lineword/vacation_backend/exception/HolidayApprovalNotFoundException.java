package lineword.vacation_backend.exception;


import lineword.vacation_backend.domain.HolidayRequest;
import lineword.vacation_backend.exception.global.HolidayHttpStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HolidayApprovalNotFoundException extends RuntimeException{
    String message;
    HolidayHttpStatus status = HolidayHttpStatus.NOT_FOUND;

    public HolidayApprovalNotFoundException(HolidayRequest holidayRequest){
        this.message = String.format("휴가 신청에 대한 승인 현황을 찾을 수 없습니다. 휴가 신청 객체: %s",holidayRequest);
    }
}
