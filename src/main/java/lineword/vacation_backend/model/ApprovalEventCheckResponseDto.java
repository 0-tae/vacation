package lineword.vacation_backend.model;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApprovalEventCheckResponseDto {
    private int notificationId;
    private String message;
    private LocalDateTime createdAt;
}
