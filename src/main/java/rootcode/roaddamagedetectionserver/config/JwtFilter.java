package rootcode.roaddamagedetectionserver.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import rootcode.roaddamagedetectionserver.jwt.TokenProvider;
import rootcode.roaddamagedetectionserver.user.AuthService;
import rootcode.roaddamagedetectionserver.user.User;
import rootcode.roaddamagedetectionserver.user.UserService;

import java.io.IOException;

import static rootcode.roaddamagedetectionserver.common.util.AuthUtil.getBearerToken;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getBearerToken(request);

        if (tokenProvider.isValidToken(token)) {
            long userId = tokenProvider.getSubject(token);
            User user = userService.findById(userId);

            var authentication = authService.getAuthentication(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
