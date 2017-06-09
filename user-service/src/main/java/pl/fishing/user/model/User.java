package pl.fishing.user.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appuser")
public class User implements Serializable{

    private String username;
    private BigDecimal radius;
    private String email;

    private List<UserFriend> friends = new ArrayList<>();

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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "user")
    public List<UserFriend> getFriends() {
        return friends;
    }

    public void setFriends(List<UserFriend> friends) {
        this.friends = friends;
    }
}
