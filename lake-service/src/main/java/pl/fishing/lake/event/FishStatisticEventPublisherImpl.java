package pl.fishing.lake.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.fishing.lake.dto.FishStatisticDto;
import pl.fishing.lake.model.Fish;
import pl.fishing.lake.transformer.FishTransformer;

@Component
public class FishStatisticEventPublisherImpl implements  FishStatisticEventPublisher{

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private FishTransformer fishTransformer;

    public void publishEvent(final Fish fish) {
        FishStatisticDto fishDto = new FishStatisticDto(fish);
        FishStatisticEvent fishStatisticEvent = new FishStatisticEvent(this, fishDto);
        applicationEventPublisher.publishEvent(fishStatisticEvent);
    }
}
