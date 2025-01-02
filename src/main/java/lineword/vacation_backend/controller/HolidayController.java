package lineword.vacation_backend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lineword.vacation_backend.domain.HolidayRequest;
import lineword.vacation_backend.exception.InValidScheduleException;
import lineword.vacation_backend.model.FetchHolidayRequestResponseDto;
import lineword.vacation_backend.model.HolidayApplyRequestDto;
import lineword.vacation_backend.model.response_standard.BackendApiResponseDto;
import lineword.vacation_backend.service.HolidayService;
import lineword.vacation_backend.service.HolidayUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HolidayController {
    private final HolidayService holidayService;

    @PostMapping("/api/vacation/apply")
    @Operation(summary = "사용자 휴가 신청",
            description = "사용자가 휴가를 신청합니다. 휴가 신청 이후 이벤트가 발생하며, Event Notice를 저장합니다.")
    @ApiResponse(responseCode = "400", description = "")
    public ResponseEntity createHolidayRequest(@RequestBody HolidayApplyRequestDto requestDto){
        if(HolidayUtils.isValid(requestDto)){
            throw new InValidScheduleException();
        }

        HolidayRequest holidayRequest = holidayService.createHolidayRequest(requestDto);

        return ResponseEntity.ok(BackendApiResponseDto.body(holidayRequest));
    }

    @GetMapping("/api/vacation/request/{memberId}")
    @Operation(summary = "사용자 휴가 신청 내역 목록",
            description = "입력된 사용자 ID에 대한 휴가 신청 내역 목록을 반환합니다. 관리자의 경우 모두의 휴가신청 내역을 반환합니다.")
    @ApiResponse(responseCode = "400", description = "")
    public ResponseEntity fetchHolidayRequest(@PathVariable int memberId,@RequestParam int pageNumber){
        List<FetchHolidayRequestResponseDto> result =
                holidayService.fetchHolidayRequestAllByMemberId(memberId, pageNumber);

        return ResponseEntity.ok(BackendApiResponseDto.body(result));
    }
}
