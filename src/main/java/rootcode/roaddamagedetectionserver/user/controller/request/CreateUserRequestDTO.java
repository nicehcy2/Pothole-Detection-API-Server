package rootcode.roaddamagedetectionserver.user.controller.request;

public record CreateUserRequestDTO(
        String email,
        String password,
        String name,
        String auth,
        String phoneNumber
) {
}
