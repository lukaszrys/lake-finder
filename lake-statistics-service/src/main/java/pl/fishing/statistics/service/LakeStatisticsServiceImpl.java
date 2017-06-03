package pl.fishing.statistics.service;

import com.mongodb.DBObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.fishing.statistics.model.FishTypeValue;
import pl.fishing.statistics.model.Statistics;
import pl.fishing.statistics.repository.StatisticsRepository;
import pl.fishing.statistics.thread.LakeStatisticsTask;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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
            for (int i = 1; i <= missingStatistics; i++){
                createNewStatistics(created, i);
            }
        }
    }

    @Override
    public List<FishTypeValue> getStatsForLake(String lakeId, Date from, Date to) {
        if (from == null){
            from = getBeginingOfMonthDate(new Date());
        }
        if (to == null) {
            to = getEndOfMonthDate(new Date());
        }

        Aggregation aggregation = newAggregation(
                match(Criteria.where("created").gte(from).lte(to).and("lakeStats._id").is(lakeId)),
                unwind("lakeStats"),
                match(Criteria.where("lakeStats._id").is(lakeId)),
                unwind("lakeStats.fishesInLake"),
                group("lakeStats.fishesInLake.type") .sum("lakeStats.fishesInLake.value").as("count")
        );

        AggregationResults<DBObject> aggregateStats = aggregateStats(aggregation);
        List<DBObject> allResult = new ArrayList<>(aggregateStats.getMappedResults());
        List<FishTypeValue> fishTypes = new ArrayList<>();
        allResult.stream().forEach(db -> {
            FishTypeValue typeValue = new FishTypeValue();
            typeValue.setType(db.get("_id").toString());
            typeValue.setValue(Long.valueOf(db.get("count").toString()));
            fishTypes.add(typeValue);
        });
        return fishTypes;

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




    private Date getBeginingOfMonthDate(Date from) {
        Calendar calendar = getCalendarForNow();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        setTimeToBeginningOfDay(calendar);
        return calendar.getTime();
    }

    private Date getEndOfMonthDate(Date to){
        Calendar calendar = getCalendarForNow();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setTimeToEndofDay(calendar);
        return calendar.getTime();
    }

    private static Calendar getCalendarForNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    private static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setTimeToEndofDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    private AggregationResults<DBObject> aggregateStats(Aggregation aggregation){
        return mongoTemplate.aggregate(aggregation, "statistics", DBObject.class);
    }
}
