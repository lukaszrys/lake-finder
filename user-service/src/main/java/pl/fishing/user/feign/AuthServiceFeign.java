package pl.fishing.user.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.fishing.user.dto.UserDto;

@FeignClient(name = "auth-service")
public interface AuthServiceFeign {
    @RequestMapping(method = RequestMethod.POST, value = "/uaa/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void registerAccount(@RequestBody UserDto user);
}