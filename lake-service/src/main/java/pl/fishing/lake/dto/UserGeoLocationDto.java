package pl.fishing.lake.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class UserGeoLocationDto {

    private BigDecimal width;
    private BigDecimal height;

    @NotNull
    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    @NotNull
    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }
}
