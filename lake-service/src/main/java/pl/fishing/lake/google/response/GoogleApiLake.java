package pl.fishing.lake.google.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleApiLake {
    private String id;
    private String name;
    private GoogleApiGeometry geometry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GoogleApiGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(GoogleApiGeometry geometry) {
        this.geometry = geometry;
    }
}
