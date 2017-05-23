package pl.fishing.lake.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.fishing.lake.model.FishType;

public interface FishTypeRepository extends PagingAndSortingRepository<FishType, String> {
}
