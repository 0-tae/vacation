package lineword.vacation_backend.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 16)
    private String name;

    // Getters and Setters
}
