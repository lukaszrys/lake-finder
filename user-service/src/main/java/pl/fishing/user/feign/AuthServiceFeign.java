package pl.fishing.user.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.fishing.user.dto.UserAuthDto;

@FeignClient(name = "auth-service")
public interface AuthServiceFeign {
    @RequestMapping(method = RequestMethod.POST, value = "/uaa/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void registerAccount(@RequestBody UserAuthDto user);

    @RequestMapping(method = RequestMethod.PATCH, value = "/uaa/user/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void editAccount(@PathVariable("id") String id, @RequestBody UserAuthDto user);
}