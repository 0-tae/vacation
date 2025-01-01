package lineword.vacation_backend.service;

import lineword.vacation_backend.model.HolidayApplyRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class HolidayUtils {

    public static int getHolidayTimesFromDates(LocalDate startDate, LocalDate endDate){
        int WORKING_TIMES = 8;
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);
        return days * WORKING_TIMES;
    }

    public static int getHolidayTimes(LocalTime startTime, LocalTime endTime){
        return endTime.getHour() - startTime.getHour();
    }

    public static boolean isValid(HolidayApplyRequestDto requestDto){
        return requestDto.getEndDate().isBefore(requestDto.getStartDate()) ||
                requestDto.getEndTime().isBefore(requestDto.getEndTime());
    }
}
