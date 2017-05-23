package pl.fishing.statistics.service;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fishing.statistics.model.Statistics;
import pl.fishing.statistics.repository.StatisticsRepository;

import java.util.Calendar;
import java.util.Date;

@Service
public class LakeStatisticsServiceImpl implements LakeStatisticsService{

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public void saveLakeStatistics() {
        Statistics statistics = statisticsRepository.findTopByOrderByCreatedDesc();
        if (statistics == null){
            createNewStatistics(new Date(), -1);
        } else {
            Date created = statistics.getCreated();
            int missingStatistics = countMissingStatistics(created);
            for (int i = 0; i < missingStatistics; i++){
                //TODO: async?
                createNewStatistics(created, i);
            }
        }
    }

    private void createNewStatistics(Date created, int i) {
        Statistics newStatistics = new Statistics();
        Date newCreatedDate = new DateTime(created).plusDays(i + 1).toDate();
        newStatistics.setCreated(newCreatedDate);
        // TODO: db.fish.aggregate({$group: {_id: {lake : "$lake"}, count: {$sum: 1}}}) + match: fish per lake
        statisticsRepository.save(newStatistics);
    }

    private int countMissingStatistics(Date created) {
        Duration duration = new Duration(new DateTime(DateUtils.truncate(created, Calendar.DAY_OF_YEAR)), new DateTime(DateUtils.truncate(new Date(), Calendar.DAY_OF_YEAR)));
        return (int) duration.getStandardDays();
    }
}
