package lineword.vacation_backend.service;


import lineword.vacation_backend.domain.Approver;
import lineword.vacation_backend.domain.Holiday;
import lineword.vacation_backend.domain.HolidayRequest;
import lineword.vacation_backend.domain.Member;
import lineword.vacation_backend.exception.NoApproverForMemberException;
import lineword.vacation_backend.model.HolidayApplyRequestDto;
import lineword.vacation_backend.repository.ApproverRepository;
import lineword.vacation_backend.repository.HolidayRepository;
import lineword.vacation_backend.repository.HolidayRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ApproverService {
    private final ApproverRepository approverRepository;

    public Approver findByMember(Member member){
        return approverRepository.findByMember(member)
                .orElseThrow(()-> new NoApproverForMemberException(member));
    }
}
