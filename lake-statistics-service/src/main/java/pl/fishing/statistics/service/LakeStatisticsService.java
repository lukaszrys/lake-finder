package pl.fishing.statistics.service;


import pl.fishing.statistics.model.FishTypeValue;

import java.util.Date;
import java.util.List;

public interface LakeStatisticsService {
    void saveLakeStatistics();

    List<FishTypeValue> getStatsForLake(String lakeId, Date from, Date to);
}
