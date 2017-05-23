package pl.fishing.lake.event;

import pl.fishing.lake.model.Fish;

public interface FishStatisticEventPublisher {

    public void publishEvent(final Fish fish);

}
