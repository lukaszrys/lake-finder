package pl.fishing.lake.transformer.criteria;

import pl.fishing.commons.transformer.criteria.Criteria;

public class FishCriteria implements Criteria{

    private LakeCriteria lakeCriteria;

    public LakeCriteria getLakeCriteria() {
        return lakeCriteria;
    }

    public boolean isWithLakeCriteria() {
        return lakeCriteria != null;
    }

    public FishCriteria withLakeCriteria(LakeCriteria lakeCriteria){
        this.lakeCriteria = lakeCriteria;
        return this;
    }
}
