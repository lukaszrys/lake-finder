package pl.fishing.lake.google.response;

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
