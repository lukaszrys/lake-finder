package pl.fishing.lake.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.fishing.lake.dto.FishStatisticDto;

@FeignClient(name = "lake-statistics-service")
public interface StatisticsServiceFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/fishes/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void addFish(@RequestBody FishStatisticDto fishStatisticDto);

}