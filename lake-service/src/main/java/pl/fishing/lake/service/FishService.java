package pl.fishing.lake.service;


import org.springframework.data.domain.Pageable;
import pl.fishing.lake.dto.FishDto;
import pl.fishing.lake.model.Fish;

import java.security.Principal;
import java.util.List;

public interface FishService {
    void addFish(FishDto fish, Principal principal);

    List<Fish> listFish(String username, FishDto fish, Principal principal, Pageable pageable);
}
