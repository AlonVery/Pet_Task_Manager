package infra.db.jpa_entity.user;

import domain.model.user.UserRole;
import domain.model.user.UserStatus;
import infra.security.PasswordHash;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJpaEntity {
    private UUID userId;
    private String userName;
    private String email;
    private PasswordHash passwordHash;
    private UserStatus userStatus;
    private UserRole userRole;
}
