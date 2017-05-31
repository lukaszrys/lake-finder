package pl.fishing.statistics.service;


import java.util.Date;

public interface LakeStatisticsService {
    void saveLakeStatistics();

    void getStatsForLake(String lakeId, Date from, Date to);
}
