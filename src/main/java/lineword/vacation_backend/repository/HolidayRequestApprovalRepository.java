package lineword.vacation_backend.repository;

import lineword.vacation_backend.domain.HolidayRequest;
import lineword.vacation_backend.domain.HolidayRequestApproval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface HolidayRequestApprovalRepository extends JpaRepository<HolidayRequestApproval,Integer> {
    @Query(value = "SELECT n FROM HolidayRequestApproval n WHERE n.holidayRequest.member = :memberId")
    Page<HolidayRequestApproval> findAllByMemberId(@Param("memberId") int memberId, Pageable pageable);

    Optional<HolidayRequestApproval> findByHolidayRequest(HolidayRequest holidayRequest);
}
