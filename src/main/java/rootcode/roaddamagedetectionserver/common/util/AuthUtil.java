package rootcode.roaddamagedetectionserver.common.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import rootcode.roaddamagedetectionserver.common.ResponseCode;
import rootcode.roaddamagedetectionserver.user.controller.exception.handler.UserHandler;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtil {
    public static String getBearerToken(HttpServletRequest request) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearer == null) {
            return null;
        }

        return bearer.split("Bearer ")[1];
    }

    public static long getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            throw new UserHandler(ResponseCode._UNAUTHORIZED);
        }

        if (authentication.getName() == null) {
            throw new UserHandler(ResponseCode._UNAUTHORIZED);
        }

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UserHandler(ResponseCode._UNAUTHORIZED);
        }

        return Long.parseLong(authentication.getName());
    }
}
