package pl.fishing.user.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appuser")
public class User {

    private String username;
    private BigDecimal radius;
    private List<User> friends = new ArrayList<>();

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

    @ManyToMany
    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
