package rootcode.roaddamagedetectionserver.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import rootcode.roaddamagedetectionserver.common.BaseEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "email", length = 30, nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", length = 15, nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth", nullable = false)
    private AuthGrade auth;

    public boolean isPasswordMatch(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }

    public boolean isAdmin() {
        return this.auth == AuthGrade.ROLE_ADMIN;
    }
}
