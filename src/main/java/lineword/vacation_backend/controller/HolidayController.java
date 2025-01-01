package lineword.vacation_backend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lineword.vacation_backend.domain.HolidayRequest;
import lineword.vacation_backend.exception.InValidScheduleException;
import lineword.vacation_backend.model.HolidayApplyRequestDto;
import lineword.vacation_backend.model.response_standard.BackendApiResponseDto;
import lineword.vacation_backend.service.HolidayService;
import lineword.vacation_backend.service.HolidayUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HolidayController {

    private final HolidayService holidayService;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping("/api/vacation/apply")
    @Operation(summary = "사용자 휴가 신청",
            description = "사용자가 휴가를 신청합니다. 휴가 신청 이후 이벤트가 발생하며, 결재권자에게 알림 메시지를 전송합니다.")
    @ApiResponse(responseCode = "400", description = "Invalid User ID supplied")
    public ResponseEntity createHolidayRequest(@RequestBody HolidayApplyRequestDto requestDto){

        // TODO: 휴가 신청으로 인해 일어나는 로직 작성
        // 휴가 시간 유효성 검증
        if(HolidayUtils.isValid(requestDto)){
            throw new InValidScheduleException();
        }

        //  1. 잔여 휴가 조정 및 HolidayRequest DB에 저장
        HolidayRequest holidayRequest = holidayService.createHolidayRequest(requestDto);

        //  2. 휴가 신청 이벤트 발생(이벤트 종류 기입 필요)


        //  3. 결재권자에게 알림 메시지 전송
        return ResponseEntity.ok(BackendApiResponseDto.body());
    }
}
