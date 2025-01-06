package lineword.vacation_backend.repository;

import lineword.vacation_backend.domain.Holiday;
import lineword.vacation_backend.domain.HolidayRequestDisplay;
import lineword.vacation_backend.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HolidayRequestDisplayRepository extends JpaRepository<HolidayRequestDisplay,Integer> {

    List<HolidayRequestDisplay> findAllByReceivedMember(Member member);

}
