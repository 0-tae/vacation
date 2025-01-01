package lineword.vacation_backend.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "HolidayRequestApproval")
public class HolidayRequestApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_approved", nullable = false)
    private Boolean firstApproved = false;

    @Column(name = "sec_approved", nullable = false)
    private Boolean secApproved = false;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private HolidayRequest holidayRequest;

    // Getters and Setters
}
