package rootcode.roaddamagedetectionserver.management;

public enum RepairType {
    REPAIRED("REPAIRED"),
    UNREPAIRED("UNREPAIRED"),
    IN_PROGRESS("IN_PROGRESS"),
    ;

    private final String value;

    RepairType(String value) {
        this.value = value;
    }

    public static RepairType of(String value) {
        for (RepairType repairType : RepairType.values()) {
            if (repairType.value.equals(value)) {
                return repairType;
            }
        }
        throw new IllegalArgumentException();
    }
}
