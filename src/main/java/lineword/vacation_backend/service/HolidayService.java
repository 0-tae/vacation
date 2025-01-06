package lineword.vacation_backend.service;


import lineword.vacation_backend.domain.*;
import lineword.vacation_backend.enums.HolidayTypes;
import lineword.vacation_backend.exception.*;
import lineword.vacation_backend.model.FetchHolidayRequestResponseDto;
import lineword.vacation_backend.model.FetchHolidayResponseDto;
import lineword.vacation_backend.model.HolidayApplyRequestDto;
import lineword.vacation_backend.model.HolidayApprovalPatchRequestDto;
import lineword.vacation_backend.repository.HolidayRepository;
import lineword.vacation_backend.repository.HolidayRequestApprovalRepository;
import lineword.vacation_backend.repository.HolidayRequestDisplayRepository;
import lineword.vacation_backend.repository.HolidayRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayService {
    private final HolidayRepository holidayRepository;
    private final HolidayRequestRepository holidayRequestRepository;
    private final HolidayRequestApprovalRepository holidayRequestApprovalRepository;
    private final HolidayRequestDisplayRepository holidayRequestDisplayRepository;
    private final MemberService memberService;
    private final ApproverService approverService;
    private final EventService eventService;


    // Update Method
    @Transactional
    public HolidayRequestDisplay updateHolidayRequestApprovalAccepted(HolidayApprovalPatchRequestDto requestDto){
        HolidayRequestDisplay holidayRequestDisplay = findHolidayRequestDisplayById(requestDto.getDisplayId());
        HolidayRequest holidayRequest = holidayRequestDisplay.getHolidayRequest();
        HolidayRequestApproval holidayApproval = findHolidayApprovalByRequest(holidayRequest);

        // TODO: 승인처리
        Member approver = holidayRequestDisplay.getReceivedMember();
        holidayApproval.approve(approver);

        // TODO: 승인 시 휴가 차감 (공가일 경우 차감하지 않음)
        if(HolidayTypes.ANNUAL_LEAVE.isEqual(holidayRequest.getType())){
            Member requestedMember = holidayRequest.getMember();
            Holiday holiday = findHolidayByMember(requestedMember);
            int usingHoliday = holidayRequest.getRequestLeave();

            holiday.decrease(usingHoliday);
        }

        // TODO: 휴가 신청 - 디스플레이 삭제 (승인 현황이 처리 되었으므로)
        deleteHolidayRequestDisplay(holidayRequestDisplay);

        return holidayRequestDisplay;
    }

    @Transactional
    public HolidayRequestDisplay updateHolidayRequestApprovalRejected(HolidayApprovalPatchRequestDto requestDto){
        // TODO: 휴가 신청 - 디스플레이 삭제 (승인 현황이 처리 되었으므로)
        HolidayRequestDisplay holidayRequestDisplay = findHolidayRequestDisplayById(requestDto.getDisplayId());
        deleteHolidayRequestDisplay(holidayRequestDisplay);

        return holidayRequestDisplay;
    }

    // Read Method
    public List<FetchHolidayRequestResponseDto> fetchHolidayRequestAllByMemberId(int memberId, int pageNumber){
        Member member = memberService.findById(memberId);
        PageRequest pageRequest = PageRequest.of(pageNumber, 10);

        Page<HolidayRequestApproval> pages =
                findHolidayRequestApprovalByMember(member, pageRequest);

        if(!pages.isEmpty()){
            return pages.getContent().stream().map(HolidayRequestApproval::toDto).toList();
        }

        return List.of();
    }

    public List<FetchHolidayResponseDto> fetchHolidayAll(int pageNumber){
        PageRequest pageRequest = PageRequest.of(pageNumber, 10);
        Page<Holiday> pages = findHolidayAll(pageRequest);

        if(!pages.isEmpty()){
            return pages.getContent().stream().map(Holiday::toDto).toList();
        }

        return List.of();
    }

    public FetchHolidayResponseDto fetchHolidayByMemberId(int memberId){
        Member member = memberService.findById(memberId);
        return findHolidayByMember(member).toDto();
    }

    private Page<Holiday> findHolidayAll(PageRequest pageRequest) {
        return holidayRepository.findAllPage(pageRequest);
    }

    public Page<HolidayRequestApproval> findHolidayRequestAll(PageRequest pageRequest){
        return holidayRequestApprovalRepository.findAll(pageRequest);
    }

    public Page<HolidayRequestApproval> findHolidayRequestAllByMemberId(int memberId, PageRequest pageRequest){
        return holidayRequestApprovalRepository.findAllByMemberId(memberId, pageRequest);
    }

    public Holiday findByMember(Member member){
        return holidayRepository.findByMember(member)
                .orElseThrow(() -> new HolidayNotFoundByMemberException(member));
    }

    private Page<HolidayRequestApproval> findHolidayRequestApprovalByMember(Member member, PageRequest pageRequest) {
        if(member.isAdmin()){
            return findHolidayRequestAll(pageRequest);
        }else{
            return findHolidayRequestAllByMemberId(member.getId(), pageRequest);
        }
    }

    private Holiday findHolidayByMember(Member member) {
        return holidayRepository.findByMember(member).orElseThrow(()-> new MemberNotFoundByException(member.getId()));
    }

    private HolidayRequestApproval findHolidayApprovalByRequest(HolidayRequest holidayRequest){
        return holidayRequestApprovalRepository.findByHolidayRequest(holidayRequest)
                .orElseThrow(()->new HolidayApprovalNotFoundException(holidayRequest));
    }


    // CREATE METHOD

    @Transactional
    public HolidayRequest createHolidayRequest(HolidayApplyRequestDto requestDto){
        // 유저의 Holiday 검색
        String email = requestDto.getEmail();
        Member member = memberService.findByEmail(email);
        Member receiver  = memberService.findById(requestDto.getReceiverId());
        Holiday holiday = findByMember(member);
        Approver approver = approverService.findByMember(member);

        // 휴가 부족하면 exception
        if(holiday.getRemainedLeave() - requestDto.getCalculatedUsingLeaveCounts() < 0){
            throw new NoRemainedHolidayException(requestDto.getCalculatedUsingLeaveCounts());
        }

        // HolidayRequest 생성
        HolidayRequest newHolidayRequest = createHolidayRequest(requestDto, receiver, holiday, approver);

        // HolidayRequestApproval 생성
        createHolidayRequestApproval(newHolidayRequest);

        // event 알림 생성
        eventService.publishApprovalNotification("휴가 신청",newHolidayRequest);

        // 승인 현황 알림 생성
        createHolidayRequestDisplayFromApprover(approver,newHolidayRequest);

        return newHolidayRequest;
    }

    public List<HolidayRequestDisplay> findAllHolidayRequestDisplayByMember(Member member){
        return holidayRequestDisplayRepository.findAllByReceivedMember(member);
    }

    public HolidayRequestDisplay findHolidayRequestDisplayById(Integer displayId){
        return holidayRequestDisplayRepository.findById(displayId)
                .orElseThrow(()-> new DisplayNotFoundException(displayId));
    }


    private HolidayRequestApproval createHolidayRequestApproval(HolidayRequest newHolidayRequest) {
        HolidayRequestApproval newApproval = newHolidayRequest.toApprovalEntity();
        return holidayRequestApprovalRepository.save(newApproval);
    }

    private HolidayRequest createHolidayRequest(HolidayApplyRequestDto requestDto, Member receiver, Holiday holiday, Approver approver) {
        HolidayRequest newHolidayRequest = requestDto.toEntity(approver, receiver, holiday);
        return holidayRequestRepository.save(newHolidayRequest);
    }

    private void createHolidayRequestDisplayFromApprover(Approver approver, HolidayRequest holidayRequest){
        Member firstApprover = approver.getFirstApprover();
        Member secondApprover = approver.getSecondApprover();

        createHolidayRequestDisplay(firstApprover,holidayRequest);
        createHolidayRequestDisplay(secondApprover,holidayRequest);
    }

    private HolidayRequestDisplay createHolidayRequestDisplay(Member member, HolidayRequest holidayRequest){
        HolidayRequestDisplay newHolidayRequestDisplay = HolidayRequestDisplay.builder()
                .holidayRequest(holidayRequest)
                .receivedMember(member)
                .build();

        return holidayRequestDisplayRepository.save(newHolidayRequestDisplay);
    }

    // Delete Method
    private void deleteHolidayRequestDisplay(HolidayRequestDisplay holidayRequestDisplay){
        holidayRequestDisplay.softDelete();
    }
}
