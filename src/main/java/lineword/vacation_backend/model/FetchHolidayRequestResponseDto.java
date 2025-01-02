package lineword.vacation_backend.model;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class FetchHolidayRequestResponseDto {
    private Integer requestId;
    private Integer previousRemainedLeave;
    private Integer afterRemainedLeave;
    private Integer requestLeave;

    private String type;
    private String unit;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private String note;

    private String firstApproverName;
    private String secApproverName;
    private String receiverName;
    private String memberName;
    private Boolean firstApproved;
    private LocalDateTime firstApprovedAt;
    private Boolean secApproved;
    private LocalDateTime secApprovedAt;

    private String status;
    private LocalDateTime createdAt;
}
