package pl.fishing.lake.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.fishing.commons.exception.ValidationException;
import pl.fishing.lake.dto.FishDto;
import pl.fishing.commons.dto.ListResult;
import pl.fishing.lake.event.FishStatisticEventPublisher;
import pl.fishing.lake.feign.UserServiceFeign;
import pl.fishing.lake.model.Fish;
import pl.fishing.lake.repository.FishRepository;
import pl.fishing.lake.transformer.FishTransformer;
import pl.fishing.lake.transformer.criteria.FishCriteria;
import pl.fishing.lake.transformer.criteria.LakeCriteria;

import java.security.Principal;
import java.util.Date;

@Service
public class FishServiceImpl implements FishService {

    @Autowired
    private FishRepository fishRepository;

    @Autowired
    private UserServiceFeign userService;

    @Autowired
    private FishTransformer fishTransformer;

    @Autowired
    private FishStatisticEventPublisher fishStatisticEventPublisher;

    @Override
    public void addFish(FishDto fishDto, Principal principal) {
        validate(fishDto);
        Fish fish = new Fish();
        fishTransformer.transformFromDto(fish, fishDto, new FishCriteria().withLakeCriteria(new LakeCriteria()));
        fish.setUploadDate(new Date());
        fish.setUsername(userService.getByUsername(principal.getName()).getUsername());
        fishRepository.save(fish);
        fishStatisticEventPublisher.publishEvent(fish);
    }

    @Override
    public ListResult<Fish> listFish(String username, Principal principal, Pageable pageable) {
        //TODO: check if user can download
        return new ListResult<>(fishRepository.countByUsername(username), fishRepository.findByUsername(username, pageable));
    }

    private void validate(FishDto fishDto) {
        if (fishDto.getLake() == null || fishDto.getLake().getId() == null){
            throw new ValidationException("FishStatisticDto has to swim in water.");
        }
        if (fishDto.getType() == null || fishDto.getType().getName() == null){
            throw new ValidationException("FishStatisticDto has to have type.");
        }
    }
}
