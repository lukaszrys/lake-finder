package pl.fishing.lake.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.fishing.lake.model.Fish;

public interface FishRepository extends PagingAndSortingRepository<Fish, String> {
}
