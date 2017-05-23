package pl.fishing.lake.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fishing.lake.model.FishType;
import pl.fishing.lake.repository.FishTypeRepository;

@Service
public class FishTypeServiceImpl implements FishTypeService{

    @Autowired
    private FishTypeRepository fishTypeRepository;

    @Override
    public FishType addNewFishType(FishType fishType) {
        //TODO: tdo fishtype?
        fishType.setConfirmed(false);
        fishTypeRepository.save(fishType);
        return fishType;
    }
}
