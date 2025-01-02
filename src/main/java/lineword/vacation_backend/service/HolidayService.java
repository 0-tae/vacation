package lineword.vacation_backend.service;


import lineword.vacation_backend.domain.*;
import lineword.vacation_backend.exception.HolidayNotFoundByMemberException;
import lineword.vacation_backend.model.FetchHolidayRequestResponseDto;
import lineword.vacation_backend.model.HolidayApplyRequestDto;
import lineword.vacation_backend.repository.HolidayRepository;
import lineword.vacation_backend.repository.HolidayRequestApprovalRepository;
import lineword.vacation_backend.repository.HolidayRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class HolidayService {
    private final HolidayRepository holidayRepository;
    private final HolidayRequestRepository holidayRequestRepository;
    private final HolidayRequestApprovalRepository holidayRequestApprovalRepository;
    private final MemberService memberService;
    private final ApproverService approverService;
    private final EventService eventService;

    public HolidayRequest createHolidayRequest(HolidayApplyRequestDto requestDto){
        String email = requestDto.getEmail();

        // 유저의 Holiday 검색
        Member member = memberService.findByEmail(email);
        Member receiver  = memberService.findById(requestDto.getReceiverId());
        Holiday holiday = findByMember(member);
        Approver approver = approverService.findByMember(member);

        // HolidayRequest 생성
        HolidayRequest newHolidayRequest = requestDto.toEntity(approver, receiver, holiday);
        holidayRequestRepository.save(newHolidayRequest);

        // HolidayRequestApproval 생성
        HolidayRequestApproval newApproval = newHolidayRequest.toApprovalEntity();
        holidayRequestApprovalRepository.save(newApproval);

        // ApprovalNotification 생성
        eventService.publishApprovalNotification("휴가 신청",newHolidayRequest);

        return newHolidayRequest;
    }


    public List<FetchHolidayRequestResponseDto> fetchHolidayRequestAllByMemberId(int memberId, int pageNumber){
        Member member = memberService.findById(memberId);
        PageRequest pageRequest = PageRequest.of(pageNumber, 10);


        // page 가져오기
        Page<HolidayRequestApproval> pages =
                findHolidayRequestApprovalByMember(member, pageRequest);

        if(!pages.isEmpty()){
            return pages.getContent().stream().map(HolidayRequestApproval::toDto).toList();
        }

        return null;
    }

    private Page<HolidayRequestApproval> findHolidayRequestApprovalByMember(Member member, PageRequest pageRequest) {
        if(member.isAdmin()){
            return findHolidayRequestAll(pageRequest);
        }else{
            return findHolidayRequestAllByMemberId(member.getId(), pageRequest);
        }
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
}
