package lineword.vacation_backend.service;


import lineword.vacation_backend.domain.Approver;
import lineword.vacation_backend.domain.Member;
import lineword.vacation_backend.exception.ApproverNotFoundException;
import lineword.vacation_backend.repository.ApproverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ApproverService {
    private final ApproverRepository approverRepository;

    public Approver findByMember(Member member){
        return approverRepository.findByMember(member)
                .orElseThrow(()-> new ApproverNotFoundException(member));
    }
}
