package pl.fishing.lake.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.fishing.lake.model.Fish;

@Document
public class FishStatisticDto {

    @Id
    private String id;
    private String type;
    private String lake;

    public FishStatisticDto(Fish fish){
        this.id = fish.getId();
        this.type = fish.getType().getName();
        this.lake = fish.getLake().getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLake() {
        return lake;
    }

    public void setLake(String lake) {
        this.lake = lake;
    }
}
