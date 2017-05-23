package pl.fishing.commons.transformer;


import pl.fishing.commons.transformer.criteria.Criteria;

public interface Transformer<T,V,C extends Criteria> {
    void transformFromDto(T entity, V dto, C criteria);

    V transformToDto(T entity, C criteria);
}
