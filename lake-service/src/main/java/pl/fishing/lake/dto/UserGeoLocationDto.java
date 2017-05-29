package pl.fishing.lake.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class UserGeoLocationDto {

    private BigDecimal latitude;
    private BigDecimal longitude;

    @NotNull
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @NotNull
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
