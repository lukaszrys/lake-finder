package pl.fishing.lake.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.fishing.lake.dto.UserDto;

@FeignClient(name = "user-service")
public interface UserServiceFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/users/find/{name}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    UserDto getByUsername(@PathVariable("name") String name);
}


