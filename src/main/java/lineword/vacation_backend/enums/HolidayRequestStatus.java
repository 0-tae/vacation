package lineword.vacation_backend.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HolidayRequestStatus {

    WAIT("wait"), // 대기
    ACCEPTED("accepted"); // 승인완료

    private final String status;
}
