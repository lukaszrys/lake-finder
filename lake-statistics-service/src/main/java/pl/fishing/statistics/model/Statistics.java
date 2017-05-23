package pl.fishing.statistics.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class Statistics {

    @Id
    private String id;

    private Date created;

    private List<LakeStats> lakeStats = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<LakeStats> getLakeStats() {
        return lakeStats;
    }

    public void setLakeStats(List<LakeStats> lakeStats) {
        this.lakeStats = lakeStats;
    }
}
