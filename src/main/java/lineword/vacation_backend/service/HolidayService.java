package lineword.vacation_backend.service;


import jakarta.persistence.*;
import lineword.vacation_backend.domain.*;
import lineword.vacation_backend.exception.HolidayNotFoundByMemberException;
import lineword.vacation_backend.exception.MemberNotFoundByEmailException;
import lineword.vacation_backend.model.HolidayApplyRequestDto;
import lineword.vacation_backend.repository.HolidayRepository;
import lineword.vacation_backend.repository.HolidayRequestRepository;
import lineword.vacation_backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class HolidayService {
    private final HolidayRepository holidayRepository;
    private final HolidayRequestRepository holidayRequestRepository;
    private final MemberService memberService;
    private final ApproverService approverService;
    private final ApplicationEventPublisher eventPublisher;

    public HolidayRequest createHolidayRequest(HolidayApplyRequestDto requestDto){
        String email = requestDto.getEmail();

        // 유저의 Holiday 검색
        Member member = memberService.findByEmail(email);
        Member receiver  = memberService.findById(requestDto.getReceiverId());
        Holiday holiday = findByMember(member);
        Approver approver = approverService.findByMember(member);

        // TODO: holiday 조정(코드 분리 필요)
        int usingLeave = requestDto.getCalculatedUsingLeaveCounts();
        holiday.decrease(usingLeave);

        HolidayRequest newHolidayRequest = requestDto.toEntity(approver, receiver, holiday);
        return holidayRequestRepository.save(newHolidayRequest);
    }

    public Holiday findByMember(Member member){
        return holidayRepository.findByMember(member)
                .orElseThrow(() -> new HolidayNotFoundByMemberException(member));
    }
}
