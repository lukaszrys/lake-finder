package pl.fishing.user.dto;

import java.time.LocalDate;

public class UserFriendDto {

    //USER DTO
    private UserDto user;
    private UserDto userFriend;

    private LocalDate createdDate;


    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public UserDto getUserFriend() {
        return userFriend;
    }

    public void setUserFriend(UserDto userFriend) {
        this.userFriend = userFriend;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
