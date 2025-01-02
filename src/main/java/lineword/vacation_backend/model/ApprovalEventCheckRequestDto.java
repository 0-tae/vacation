package lineword.vacation_backend.model;


import lombok.Getter;

@Getter
public class ApprovalEventCheckRequestDto {
    // TODO: 인증 토큰 확인 방식을 채택할까?
    private int userId;
    private int eventId;
}
