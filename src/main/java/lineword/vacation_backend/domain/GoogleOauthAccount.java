package lineword.vacation_backend.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "GoogleOauthAccount")
public class GoogleOauthAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_id", nullable = false, length = 32)
    private String accountId;

    @Column(name = "access_token", nullable = false, length = 64)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false, length = 64)
    private String refreshToken;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @OneToOne
    @JoinColumn(name = "oauth_account_id", nullable = false)
    private MemberOauthAccount memberOauthAccount;

    // Getters and Setters
}

