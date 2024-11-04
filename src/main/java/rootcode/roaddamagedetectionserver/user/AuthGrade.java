package rootcode.roaddamagedetectionserver.user;

public enum AuthGrade {
    ROLE_USER("USER"),
    ROLE_MANAGER("MANAGER"),
    ROLE_ADMIN("ADMIN"),
    ;

    private final String value;

    AuthGrade(String value) {
        this.value = value;
    }

    public static AuthGrade of(String value) {
        for (AuthGrade grade : AuthGrade.values()) {
            if (grade.value.equals(value)) {
                return grade;
            }
        }

        throw new IllegalArgumentException("No such grade: " + value);
    }
}
