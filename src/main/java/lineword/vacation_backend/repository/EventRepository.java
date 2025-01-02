package lineword.vacation_backend.repository;

import lineword.vacation_backend.domain.Event;
import lineword.vacation_backend.domain.Holiday;
import lineword.vacation_backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {

    Optional<Event> findByEventType(String EventType);
}
