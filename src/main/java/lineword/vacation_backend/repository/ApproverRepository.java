package lineword.vacation_backend.repository;

import lineword.vacation_backend.domain.Approver;
import lineword.vacation_backend.domain.Holiday;
import lineword.vacation_backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApproverRepository extends JpaRepository<Approver,Integer> {
    Optional<Approver> findByMember(Member member);
}
