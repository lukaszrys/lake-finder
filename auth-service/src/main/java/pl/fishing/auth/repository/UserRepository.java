package pl.fishing.auth.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.fishing.auth.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
}
