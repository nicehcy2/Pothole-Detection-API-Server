package rootcode.roaddamagedetectionserver.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rootcode.roaddamagedetectionserver.jwt.TokenProvider;
import rootcode.roaddamagedetectionserver.user.AuthService;
import rootcode.roaddamagedetectionserver.user.UserService;

@RequiredArgsConstructor
@Configuration
public class SpringSecurityConfig {
    private final UserService userService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(it -> it.disable())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(it -> {
                    it.requestMatchers(
                            "/h2-console/**"
                    ).authenticated();

                    it.requestMatchers("/**").permitAll();
                })
                .exceptionHandling(it -> {
                    it.accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    });

                    it.authenticationEntryPoint((request, response, authException) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    });
                })
                .addFilterBefore(new JwtFilter(userService, authService, tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
