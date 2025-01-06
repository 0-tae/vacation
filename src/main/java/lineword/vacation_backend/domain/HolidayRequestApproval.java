package lineword.vacation_backend.domain;

import jakarta.persistence.*;
import lineword.vacation_backend.model.FetchHolidayRequestResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "HolidayRequestApproval")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HolidayRequestApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_approved", nullable = false)
    private Boolean firstApproved;

    @Column(name = "sec_approved", nullable = false)
    private Boolean secApproved;

    @Column(name = "first_approved_at", nullable = false)
    private LocalDateTime firstApprovedAt;

    @Column(name = "sec_approved_at", nullable = false)
    private LocalDateTime secApprovedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private HolidayRequest holidayRequest;

    public void approve(Member givenMember){
        Approver approver = holidayRequest.getApprover();

        // 1차 결재자일 경우
        if(approver.getFirstApprover().getId().equals(givenMember.getId())){
            setFirstApproved(true);
        }

        // 2차 결재자일 경우
        if(approver.getSecondApprover().getId().equals(givenMember.getId())){
            setSecApproved(true);
        }

        // 모두 승인 되었을 경우
        if(firstApproved && secApproved){
            holidayRequest.setStatusAccepted();
        }
    }

    private void setFirstApproved(boolean isApproved){
        this.firstApproved = isApproved;

        if(this.firstApproved){
            this.firstApprovedAt = LocalDateTime.now();
        }
    }

    private void setSecApproved(boolean isApproved){
        this.secApproved = isApproved;

        if(this.secApproved){
            this.secApprovedAt = LocalDateTime.now();
        }
    }

    public FetchHolidayRequestResponseDto toDto()
    {
        return FetchHolidayRequestResponseDto.builder()
                .requestId(holidayRequest.getId())
                .previousRemainedLeave(holidayRequest.getPreviousRemainedLeave())
                .afterRemainedLeave(holidayRequest.getAfterRemainedLeave())
                .requestLeave(holidayRequest.getRequestLeave())
                .type(holidayRequest.getType())
                .unit(holidayRequest.getUnit())
                .startDate(holidayRequest.getStartDate())
                .endDate(holidayRequest.getEndDate())
                .startTime(holidayRequest.getStartTime())
                .endTime(holidayRequest.getEndTime())
                .note(holidayRequest.getNote())
                .receiverName(holidayRequest.getReceiver().getName())
                .memberName(holidayRequest.getMember().getName())
                .firstApproverName(holidayRequest.getApprover().getFirstApprover().getName())
                .firstApproved(firstApproved)
                .firstApprovedAt(firstApprovedAt)
                .secApproverName(holidayRequest.getApprover().getSecondApprover().getName())
                .secApproved(secApproved)
                .secApprovedAt(secApprovedAt)
                .status(holidayRequest.getStatus())
                .createdAt(createdAt)
                .build();
    }
}
