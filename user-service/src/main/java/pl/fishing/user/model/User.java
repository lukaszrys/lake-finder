package pl.fishing.user.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "appuser")
public class User {
    private Long id;
    private String username;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    @NotNull
    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
