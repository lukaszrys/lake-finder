package pl.fishing.statistics.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LakeStats {

    private String id;
    private List<FishTypeValue> fishesInLake = new ArrayList<>();
    private Date created;
    private Long total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FishTypeValue> getFishesInLake() {
        return fishesInLake;
    }

    public void setFishesInLake(List<FishTypeValue> fishesInLake) {
        this.fishesInLake = fishesInLake;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
