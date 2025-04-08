package payroll;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserAuthentication extends AbstractAuthenticationToken {
    private final String username;

    public UserAuthentication(String username, Object roles) {
        super(convertRolesToAuthorities(roles));
        this.username = username;
        setAuthenticated(true);
    }

    private static Collection<? extends GrantedAuthority> convertRolesToAuthorities(Object roles) {
        if (roles instanceof Set<?>) {
            return ((Set<?>) roles).stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }

    @Override
    public Object getCredentials() {
        return null; // Không cần lưu mật khẩu ở đây
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}
