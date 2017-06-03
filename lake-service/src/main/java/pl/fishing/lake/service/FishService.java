package pl.fishing.lake.service;


import org.springframework.data.domain.Pageable;
import pl.fishing.lake.dto.FishDto;
import pl.fishing.lake.dto.ListResult;
import pl.fishing.lake.model.Fish;

import java.security.Principal;

public interface FishService {
    void addFish(FishDto fish, Principal principal);

    ListResult<Fish> listFish(String username, Principal principal, Pageable pageable);
}
