package rootcode.roaddamagedetectionserver.pothole.controller;

public record PotholeMonthlyStatisticsResponseDTO(
        long january,
        long february,
        long march,
        long april,
        long may,
        long june,
        long july,
        long august,
        long september,
        long october,
        long november,
        long december
) {
}
