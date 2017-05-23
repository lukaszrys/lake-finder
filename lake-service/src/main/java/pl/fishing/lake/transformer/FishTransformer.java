package pl.fishing.lake.transformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.fishing.commons.exception.NotFoundException;
import pl.fishing.commons.transformer.Transformer;
import pl.fishing.lake.dto.FishDto;
import pl.fishing.lake.model.Fish;
import pl.fishing.lake.model.FishType;
import pl.fishing.lake.model.Lake;
import pl.fishing.lake.repository.FishTypeRepository;
import pl.fishing.lake.repository.LakeRepository;
import pl.fishing.lake.service.FishTypeService;
import pl.fishing.lake.transformer.criteria.FishCriteria;

@Component
public class FishTransformer implements Transformer<Fish, FishDto, FishCriteria> {

    @Autowired
    private LakeRepository lakeRepository;

    @Autowired
    private FishTypeRepository fishTypeRepository;

    @Autowired
    private FishTypeService fishTypeService;

    @Override
    public void transformFromDto(Fish entity, FishDto dto, FishCriteria criteria) {
        if (criteria.isWithLakeCriteria()) {
            Lake lake = lakeRepository.findOne(dto.getLake().getId());
            if (lake == null) {
                throw new NotFoundException("Didn't find lake with id=" + dto.getLake().getId());
            }
            entity.setLake(lake);
        }
        FishType type = fishTypeRepository.findOne(dto.getType().getName());
        if (type == null){//TODO: create if doesn't exist - confirmed false
            type = fishTypeService.addNewFishType(dto.getType());
        }
        entity.setType(type);
    }

    @Override
    public FishDto transformToDto(Fish entity, FishCriteria criteria) {
        return null;
    }
}
