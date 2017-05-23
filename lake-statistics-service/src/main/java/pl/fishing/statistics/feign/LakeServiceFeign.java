package pl.fishing.statistics.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "lake-service")
public interface LakeServiceFeign {
}
