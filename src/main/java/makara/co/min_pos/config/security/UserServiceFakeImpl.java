package makara.co.min_pos.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceFakeImpl implements UserService{
    private final PasswordEncoder passwordEncoder;
    @Override
    public Optional<AuthUser> findUserByUsername(String username) {
        List<AuthUser> users = List.of(
                new AuthUser("chi",passwordEncoder.encode("chi369"),
                        RoleEnum.SALE.getAuthorities(),true, true,true,true),
                new AuthUser("nita",passwordEncoder.encode("nita789"),
                        RoleEnum.ADMIN.getAuthorities(),true,true,true,true)
        );
        return users.stream()
                .filter(user->user.getUsername().equals(username))
                .findFirst();
    }
}
