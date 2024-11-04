package rootcode.roaddamagedetectionserver.pothole.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Weather {
    @Column(name = "temperature")
    private double temperature;
    @Column(name = "precipitation")
    private double precipitation;
    @Column(name = "humidity")
    private double humidity;
}
