package lineword.vacation_backend.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Member",
        indexes = {
                @Index(name = "idx_email", columnList = "email"),
                @Index(name = "idx_user_type", columnList = "userType"),
                @Index(name = "idx_recent_datetime", columnList = "recentDatetime")
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Member {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 16)
    private String name;

    @Column(name = "email", nullable = false, length = 32)
    private String email;

    @Column(name = "password", length = 32)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "alias", length = 50)
    private String alias;

    @Column(name = "user_type", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean userType;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Role role;

    @Column(name = "recent_datetime")
    private LocalDateTime recentDatetime;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    public String getRoleName(){
        return this.role.getName();
    }

    public boolean isAdmin(){return getRoleName() == "ADMIN";}

}
