package lineword.vacation_backend.domain;

import jakarta.persistence.*;
import lineword.vacation_backend.exception.NoRemainedHolidayException;
import lineword.vacation_backend.model.FetchHolidayResponseDto;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Holiday")
@Getter
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_leave", nullable = false)
    private Integer totalLeave;

    @Column(name = "remained_leave", nullable = false)
    private Integer remainedLeave;

    @Column(name = "used_leave", nullable = false)
    private Integer usedLeave;

    @Column(name = "used_official_leave", nullable = false)
    private Integer usedOfficialLeave;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    public FetchHolidayResponseDto toDto() {
        return FetchHolidayResponseDto.builder()
                .totalLeave(totalLeave)
                .usedOfficialLeave(usedOfficialLeave)
                .remainedLeave(remainedLeave)
                .usedLeave(usedLeave)
                .build();
    }

    public void decrease(int decreaseNum) throws NoRemainedHolidayException {
        int tmpRemainedLeave = this.remainedLeave;
        tmpRemainedLeave -= decreaseNum;

        // 만약 남은 휴가가 없을 경우, 휴가를 신청할 수 없다.
        if (tmpRemainedLeave < 0) {
            throw new NoRemainedHolidayException(tmpRemainedLeave);
        }

        this.usedLeave += decreaseNum;
        this.remainedLeave = tmpRemainedLeave;
    }
}
