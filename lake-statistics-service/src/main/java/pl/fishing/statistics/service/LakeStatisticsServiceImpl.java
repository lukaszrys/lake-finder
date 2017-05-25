package pl.fishing.statistics.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.fishing.statistics.model.Statistics;
import pl.fishing.statistics.repository.StatisticsRepository;
import pl.fishing.statistics.thread.LakeStatisticsTask;

import java.util.Date;

@Service
public class LakeStatisticsServiceImpl implements LakeStatisticsService {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveLakeStatistics() {
        Statistics statistics = statisticsRepository.findTopByOrderByCreatedDesc();
        if (statistics == null){
            createNewStatistics(new Date(), -1);
        } else {
            Date created = statistics.getCreated();
            int missingStatistics = countMissingStatistics(created);
            for (int i = 0; i < missingStatistics; i++){
                createNewStatistics(created, i);
            }
        }
    }

    @Async
    public void createNewStatistics(Date created, int i) {
        LakeStatisticsTask lakeStatisticsTask = new LakeStatisticsTask(created, i, statisticsRepository, mongoTemplate);
        logger.info("Finished creating statistics for date: " + lakeStatisticsTask.call().toString());
    }

    private int countMissingStatistics(Date created) {
        Date today = new Date();
        return Days.daysBetween(new DateTime(created), new DateTime(today)).getDays();
    }
}
