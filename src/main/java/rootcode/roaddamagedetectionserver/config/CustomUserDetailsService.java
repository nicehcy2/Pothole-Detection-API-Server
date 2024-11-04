package rootcode.roaddamagedetectionserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rootcode.roaddamagedetectionserver.user.User;
import rootcode.roaddamagedetectionserver.user.UserJpaRepository;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserJpaRepository userJpaRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJpaRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such user: " + username));

        return createUserDetails(user);
    }

    private UserDetails createUserDetails(User user) {
        var grantedAuthority = new SimpleGrantedAuthority(user.getAuth().toString());

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getId()),
                user.getPassword(),
                Set.of(grantedAuthority)
        );
    }

}
