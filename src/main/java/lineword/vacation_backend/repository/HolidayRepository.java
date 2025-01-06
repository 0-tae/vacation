package lineword.vacation_backend.repository;

import lineword.vacation_backend.domain.Holiday;
import lineword.vacation_backend.domain.HolidayRequestApproval;
import lineword.vacation_backend.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday,Integer> {

    Optional<Holiday> findByMember(Member member);

    Page<Holiday> findAllPage(Pageable pageable);

}
