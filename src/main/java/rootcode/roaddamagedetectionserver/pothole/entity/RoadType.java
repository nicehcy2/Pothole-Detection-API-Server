package rootcode.roaddamagedetectionserver.pothole.entity;

public enum RoadType {
    HIGHWAY("HIGHWAY", "고속도로"),
    NATIONAL_ROAD("NATIONAL_ROAD", "국도"),
    // TODO: 도로 종류 추가
    ETC("ETC", "기타"),
    ;

    private final String value;
    private final String displayName;

    RoadType(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public static RoadType of(String value) {
        for (RoadType roadType : RoadType.values()) {
            if (roadType.value.equals(value)) {
                return roadType;
            }
        }
        throw new IllegalArgumentException();
    }
}
