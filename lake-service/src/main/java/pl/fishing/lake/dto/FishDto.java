package pl.fishing.lake.dto;

import pl.fishing.lake.model.FishType;

import java.math.BigDecimal;
import java.util.Date;

public class FishDto {

    private String id;
    private FishType type;
    private LakeDto lake;
    private Date uploadDate;
    private String username;
    private BigDecimal weight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LakeDto getLake() {
        return lake;
    }

    public void setLake(LakeDto lake) {
        this.lake = lake;
    }

    public FishType getType() {
        return type;
    }

    public void setType(FishType type) {
        this.type = type;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
