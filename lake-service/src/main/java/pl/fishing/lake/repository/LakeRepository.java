package pl.fishing.lake.repository;

import org.springframework.data.geo.Circle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.fishing.lake.model.Lake;

import java.util.List;

@Repository
public interface LakeRepository extends PagingAndSortingRepository<Lake, String>{
    List<Lake> findByPositionWithin(Circle c);
}
