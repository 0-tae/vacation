package lineword.vacation_backend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lineword.vacation_backend.model.ApprovalEventCheckRequestDto;
import lineword.vacation_backend.model.ApprovalEventCheckResponseDto;
import lineword.vacation_backend.model.response_standard.BackendApiResponseDto;
import lineword.vacation_backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class EventController {
    private final EventService eventService;

    @PostMapping("/api/event/approval-request-check")
    @Operation(summary = "사용자 휴가 승인 요청 알림 확인",
            description = "사용자의 휴가 승인 요청에 대한 알림을 확인합니다.")
    @ApiResponse(responseCode = "404", description = "알림이 존재하지 않습니다.")
    public ResponseEntity createHolidayRequest(@RequestBody ApprovalEventCheckRequestDto requestDto){
        List<ApprovalEventCheckResponseDto> result =
                eventService.checkApprovalNotification(requestDto);

        return ResponseEntity.ok(BackendApiResponseDto.body(result));
    }
}
