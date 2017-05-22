package pl.fishing.auth.model;


import javax.persistence.*;

@Entity
@Table(name="appuser")
public class User {


    private String username;

    private String password;

    public String getPassword() {
        return password;
    }

    @Id
    @Column(unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
