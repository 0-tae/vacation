package lineword.vacation_backend.service;


import lineword.vacation_backend.domain.Approver;
import lineword.vacation_backend.domain.Member;
import lineword.vacation_backend.exception.MemberNotFoundByEmailException;
import lineword.vacation_backend.exception.NoApproverForMemberException;
import lineword.vacation_backend.repository.ApproverRepository;
import lineword.vacation_backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findByEmail(String email){
        return memberRepository.findByEmail(email)
                .orElseThrow(()->new MemberNotFoundByEmailException(email));
    }

    public Member findById(int id){
        return memberRepository.findById(id).orElseThrow(()->new MemberNotFoundByEmailException());
    }
}
