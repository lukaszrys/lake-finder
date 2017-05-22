package pl.fishing.user.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "appuser")
public class User {
    private String username;

    @NotNull
    @Id
    @Column(unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
