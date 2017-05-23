package pl.fishing.lake.event;

import org.springframework.context.ApplicationEvent;
import pl.fishing.lake.dto.FishStatisticDto;

public class FishStatisticEvent extends ApplicationEvent{

    private FishStatisticDto fish;

    public FishStatisticEvent(Object source, FishStatisticDto fish) {
        super(source);
        this.fish = fish;
    }

    public FishStatisticDto getFish() {
        return fish;
    }
}
