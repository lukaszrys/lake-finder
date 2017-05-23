package pl.fishing.lake.service;


import pl.fishing.lake.dto.FishDto;

import java.security.Principal;

public interface FishService {
    void addFish(FishDto fish, Principal principal);
}
