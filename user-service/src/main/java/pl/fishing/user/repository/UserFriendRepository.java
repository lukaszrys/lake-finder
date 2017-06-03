package pl.fishing.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.fishing.user.model.User;
import pl.fishing.user.model.UserFriend;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFriendRepository extends PagingAndSortingRepository<UserFriend, String>{

    @Query("select u from UserFriend u where u.user = ?1 and u.userFriend = ?2 OR u.user = ?2 and u.userFriend = ?1")
    Optional<User> findByUserAndUserFriend(User user, User userFriend);

    @Query("select u from UserFriend u where u.user = ?1 OR u.userFriend = ?1")
    List<UserFriend> findByUser(User user);

}
