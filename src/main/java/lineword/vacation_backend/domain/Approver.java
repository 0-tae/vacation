package lineword.vacation_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Approver")
@Getter
public class Approver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "first_approver_id", nullable = false)
    private Member firstApprover;

    @ManyToOne
    @JoinColumn(name = "sec_approver_id", nullable = false)
    private Member secondApprover;

    // Getters and Setters
}

