package pl.fishing.lake.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.fishing.lake.feign.StatisticsServiceFeign;

@Component
public class FishStatisticEventListener implements ApplicationListener<FishStatisticEvent>{

    @Autowired
    private StatisticsServiceFeign statisticsServiceFeign;
    @Override
    public void onApplicationEvent(FishStatisticEvent fishStatisticEvent) {
        statisticsServiceFeign.addFish(fishStatisticEvent.getFish());
    }
}
