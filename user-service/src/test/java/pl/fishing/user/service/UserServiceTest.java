package pl.fishing.user.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.fishing.user.UserServiceApplication;

import java.security.Principal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UserServiceApplication.class)
@ComponentScan(basePackages = {"pl.fishing.user.service.*"})
@Sql({"/clear-db.sql","/user-service.sql"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void friendsForeverTest() throws Exception {
        Principal principal = createFakePrincipal("firstUser");
        userService.addFriend(principal, "secondUser");
        Principal principalSecond = createFakePrincipal("secondUser");

        Assert.assertEquals(1L, userService.listMyFriends(principal, null).getList().size());
        Assert.assertEquals(1L, userService.listMyFriends(principalSecond, null).getList().size());
    }

    private Principal createFakePrincipal(final String name) {
        return new Principal() {
            @Override
            public boolean equals(Object another) {
                return false;
            }

            @Override
            public String toString() {
                return "";
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }
}
