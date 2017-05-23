package pl.fishing.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "appuser")
public class User {

    private String username;
    private BigDecimal radius;

    @NotNull
    @Id
    @Column(unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getRadius() {
        return radius == null ? BigDecimal.TEN : radius;
    }

    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }
}
