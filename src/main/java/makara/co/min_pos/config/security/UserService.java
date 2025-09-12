package makara.co.min_pos.config.security;

import java.util.Optional;

public interface UserService {
    Optional<AuthUser> findUserByUsername(String username);
}
