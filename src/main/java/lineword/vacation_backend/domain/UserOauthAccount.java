package lineword.vacation_backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class GoogleOauthAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 16)
    private String name;

    // Getters and Setters
}
