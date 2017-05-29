package pl.fishing.lake.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.fishing.lake.LakeServiceApplication;
import pl.fishing.lake.dto.UserGeoLocationDto;
import pl.fishing.lake.model.Lake;

import java.math.BigDecimal;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LakeServiceApplication.class)
@ComponentScan(basePackages = {"pl.fishing.lake.service.*"})
public class LakeServiceTest {

    @Autowired
    private LakeService lakeService;

    @Test
    public void findLakeWithoutLakesTest() throws Exception {
        UserGeoLocationDto dto = new UserGeoLocationDto();
        dto.setLongitude(new BigDecimal("19.899566"));
        dto.setLatitude(new BigDecimal("50.051691"));
        Assert.assertNull(lakeService.getLakeNearMe(dto, 1000));
    }

    @Test
    public void findLakeTest() throws Exception {
        UserGeoLocationDto dto = new UserGeoLocationDto();
        dto.setLongitude(new BigDecimal("20.871212"));
        dto.setLatitude(new BigDecimal("53.948004"));
        Lake lake = lakeService.getLakeNearMe(dto, 1000);
        Assert.assertEquals(lake.getName(), "Jezioro Tejstymy");
    }
}
