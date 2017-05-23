package pl.fishing.statistics.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.fishing.statistics.model.Fish;

public interface FishRepository extends PagingAndSortingRepository<Fish,String>{
}
