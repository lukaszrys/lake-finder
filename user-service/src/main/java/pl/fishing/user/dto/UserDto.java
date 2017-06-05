package pl.fishing.user.dto;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private String username;
    private BigDecimal radius;
    private String email;

    private List<UserFriendDto> friends = new ArrayList<>();

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

    public List<UserFriendDto> getFriends() {
        return friends;
    }

    public void setFriends(List<UserFriendDto> friends) {
        this.friends = friends;
    }
}
