package lineword.vacation_backend.domain;

import jakarta.persistence.*;
import lineword.vacation_backend.exception.NoRemainedHolidayException;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "Holiday")
@Getter
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "remained_leave", nullable = false)
    private Integer remainedLeave = 0;

    @Column(name = "used_leave", nullable = false)
    private Integer usedLeave = 0;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    public void decrease(int decreaseNum) throws NoRemainedHolidayException {
        int tmpRemainedLeave = this.remainedLeave;
        tmpRemainedLeave -= decreaseNum;

        // 만약 남은 휴가가 없을 경우, 휴가를 신청할 수 없다.
        if(tmpRemainedLeave < 0){
            throw new NoRemainedHolidayException(tmpRemainedLeave);
        }

        this.usedLeave += decreaseNum;
        this.remainedLeave = tmpRemainedLeave;
    }
}

