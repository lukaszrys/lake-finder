package pl.fishing.statistics.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.fishing.statistics.service.LakeStatisticsService;

@Service
public class StatisticsJob {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private LakeStatisticsService lakeStatisticsService;

    @Scheduled(cron = "${statistics.job.saveLakeStatistics.cron}")
    public void saveLakeStatistics() {
        logger.info("Starting saveLakeStatistics");
        lakeStatisticsService.saveLakeStatistics();
    }
}
