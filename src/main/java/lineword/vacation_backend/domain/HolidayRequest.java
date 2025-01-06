package lineword.vacation_backend.domain;

import jakarta.persistence.*;
import lineword.vacation_backend.enums.HolidayRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "HolidayRequest")
public class HolidayRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "previous_remained_leave", nullable = false)
    private Integer previousRemainedLeave;

    @Column(name = "after_remained_leave", nullable = false)
    private Integer afterRemainedLeave;

    @Column(nullable = false, length = 16)
    private String type;

    @Column(nullable = false, length = 16)
    private String unit;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "request_leave", nullable = false)
    private Integer requestLeave;

    @Column(length = 64)
    private String note;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "approver_id", nullable = false)
    private Approver approver;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    public void setStatusAccepted() {
        this.status = HolidayRequestStatus.ACCEPTED.getStatus();
    }

    public List<Member> getApprovers() {
        return List.of(approver.getFirstApprover(),
                approver.getSecondApprover());
    }

    public HolidayRequestApproval toApprovalEntity() {
        return HolidayRequestApproval.builder()
                .holidayRequest(this).build();
    }
}
