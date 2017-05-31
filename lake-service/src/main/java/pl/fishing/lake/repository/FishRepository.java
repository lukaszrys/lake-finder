package pl.fishing.lake.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.fishing.lake.model.Fish;

import java.util.List;

public interface FishRepository extends PagingAndSortingRepository<Fish, String> {

    List<Fish> findByUsername(String username, Pageable pageable);

}
