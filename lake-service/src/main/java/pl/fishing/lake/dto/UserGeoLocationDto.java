package pl.fishing.lake.dto;

import java.math.BigDecimal;

public class UserGeoLocationDto {

    private BigDecimal width;
    private BigDecimal height;

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }
}
