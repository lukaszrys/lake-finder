package pl.fishing.lake.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleMapsResponse {

    private List<GoogleApiLake> results;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GoogleApiLake> getResults() {
        return results;
    }

    public void setResults(List<GoogleApiLake> results) {
        this.results = results;
    }
}
