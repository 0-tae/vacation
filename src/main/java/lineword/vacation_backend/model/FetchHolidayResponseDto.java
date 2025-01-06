package lineword.vacation_backend.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FetchHolidayResponseDto {
    private Integer totalLeave;
    private Integer remainedLeave;
    private Integer usedLeave;
    private Integer remainedOfficialLeave;
    private Integer usedOfficialLeave;
}
