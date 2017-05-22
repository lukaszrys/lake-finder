package pl.fishing.user.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.fishing.user.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String>{
}
