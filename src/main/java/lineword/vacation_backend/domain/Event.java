package lineword.vacation_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Event")
@Getter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "event_type", nullable = false, length = 16)
    private String eventType;

    @Column(name = "entity_type", length = 16)
    private String entityType;
}

