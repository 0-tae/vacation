package lineword.vacation_backend.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "MemberOauthAccount")
public class MemberOauthAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 16)
    private String provider;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;
    // Getters and Setters
}

