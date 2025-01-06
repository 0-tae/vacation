package lineword.vacation_backend.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "HolidayRequestDisplay")
public class HolidayRequestDisplay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private HolidayRequest holidayRequest;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member receivedMember;

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
