package lineword.vacation_backend.model;

import lineword.vacation_backend.domain.*;
import lineword.vacation_backend.enums.HolidayRequestStatus;
import lineword.vacation_backend.enums.HolidayTypes;
import lineword.vacation_backend.service.HolidayUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Builder
public class HolidayApplyRequestDto {
    private String email; // 유저 이메일
    private String type; // 휴가 종류
    private String unit; // 휴가 단위(일, 시간)
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String note; // 휴가 사유
    private int receiverId; // 인수자 이름

    public int getCalculatedUsingLeaveCounts() {
        boolean isTimeUnitHoliday = (unit == "시간 단위");

        // 시간 단위 연차일 경우
        if (isTimeUnitHoliday) {
            return HolidayUtils.getHolidayTimes(startTime, endTime);
        }

        return HolidayUtils.getHolidayTimesFromDates(startDate, endDate);
    }

    public HolidayRequest toEntity(Approver approver, Member receiver, Holiday holiday) {
        // 입력된 type이 연가인지 공가인지 확인
        int remainedLeave = holiday.getRemainedLeave();

        return HolidayRequest.builder()
                .approver(approver)
                .previousRemainedLeave(remainedLeave)
                .afterRemainedLeave(remainedLeave - getCalculatedUsingLeaveCounts())
                .requestLeave(getCalculatedUsingLeaveCounts())
                .type(type)
                .unit(unit)
                .startDate(startDate)
                .endDate(endDate)
                .startTime(startTime)
                .endTime(endTime)
                .receiver(receiver)
                .note(note)
                .member(approver.getMember())
                .status(HolidayRequestStatus.WAIT.getStatus()).build();
    }

}
