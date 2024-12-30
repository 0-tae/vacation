package lineword.vacation_backend.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Member")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 16)
    private String name;

    @Column(nullable = false, length = 32)
    private String email;

    @Column(nullable = false, length = 32)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    // Getters and Setters
}
