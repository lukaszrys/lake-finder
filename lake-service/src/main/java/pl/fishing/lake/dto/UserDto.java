package pl.fishing.lake.dto;


import java.math.BigDecimal;

public class UserDto {

    private String username;
    private BigDecimal radius;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getRadius() {
        return radius;
    }

    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }
}
