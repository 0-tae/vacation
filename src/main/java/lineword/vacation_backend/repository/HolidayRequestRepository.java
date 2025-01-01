package lineword.vacation_backend.repository;

import lineword.vacation_backend.domain.Holiday;
import lineword.vacation_backend.domain.HolidayRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HolidayRequestRepository extends JpaRepository<HolidayRequest,Integer> {
}
