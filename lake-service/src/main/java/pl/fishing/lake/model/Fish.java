package pl.fishing.lake.model;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Fish {

    @Id
    private String id;

    @DBRef
    @NotNull
    private FishType type;

    @NotNull
    @DBRef
    private Lake lake;

    private Date uploadDate;

    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FishType getType() {
        return type;
    }

    public void setType(FishType type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Lake getLake() {
        return lake;
    }

    public void setLake(Lake lake) {
        this.lake = lake;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
