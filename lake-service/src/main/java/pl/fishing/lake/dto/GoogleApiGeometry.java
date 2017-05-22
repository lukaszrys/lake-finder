package pl.fishing.lake.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleApiGeometry {

    private GoogleApiLocation location;

    public GoogleApiLocation getLocation() {
        return location;
    }

    public void setLocation(GoogleApiLocation location) {
        this.location = location;
    }
}
