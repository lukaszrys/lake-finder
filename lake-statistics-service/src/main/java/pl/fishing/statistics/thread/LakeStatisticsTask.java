package pl.fishing.statistics.thread;

import com.mongodb.DBObject;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import pl.fishing.statistics.model.FishTypeValue;
import pl.fishing.statistics.model.LakeStats;
import pl.fishing.statistics.model.Statistics;
import pl.fishing.statistics.repository.StatisticsRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class LakeStatisticsTask implements Callable<Date> {

    private StatisticsRepository statisticsRepository;

    private MongoTemplate mongoTemplate;

    private Date created;

    private int daysToAdd;

    public LakeStatisticsTask(Date created, int daysToAdd, StatisticsRepository statisticsRepositorym, MongoTemplate mongoTemplate){
        this.created = created;
        this.daysToAdd = daysToAdd;
        this.statisticsRepository = statisticsRepositorym;
        this.mongoTemplate = mongoTemplate;
    }

    //TODO: implement to optimize
    //db.getCollection('fish').aggregate(
    // {$match : {"uploadDate" : { $gte : new ISODate("2017-05-22T00:00:00Z"), $lte : new ISODate("2017-05-25T00:00:00Z") }}},
    //{$group: {_id: {lake : "$lake", type : "$type"}, count: {$sum: 1}}} ,
    //{$project: {array : ["$_id.type" , "$count"], count : "$count"}},
    //{$group: {_id: {lake : "$_id.lake"}, count: {$sum: 1}, types : {$addToSet : "$array"}}})
    @Override
    public Date call() {
        Statistics newStatistics = new Statistics();
        created = new DateTime(created).plusDays(this.daysToAdd).toDate();
        Date newCreatedDate = new DateTime(created).plusDays(this.daysToAdd + 1).toDate();
        newStatistics.setCreated(newCreatedDate);
        Aggregation aggregation = newAggregation(match(Criteria.where("uploadDate").gte(created).lte(newCreatedDate)),
                group("lake").count().as("values"));
        AggregationResults<DBObject> aggregateFishes = aggregateFishes(aggregation);
        List<DBObject> allResult = new ArrayList<>(aggregateFishes.getMappedResults());
        List<LakeStats> lakeStats = new ArrayList<>();
        streamResultIntoStats(created, newCreatedDate, allResult, lakeStats);

        newStatistics.getLakeStats().addAll(lakeStats);
        statisticsRepository.save(newStatistics);
        return newCreatedDate;
    }

    private AggregationResults<DBObject> aggregateFishes(Aggregation aggregation){
        return mongoTemplate.aggregate(aggregation, "fish", DBObject.class);
    }

    private void streamResultIntoStats(Date created, Date newCreatedDate, List<DBObject> allResult, List<LakeStats> lakeStats) {
        allResult.stream().forEach(db -> {
            LakeStats ls = new LakeStats();
            ls.setId(db.get("_id").toString());
            ls.setTotal(Long.valueOf(db.get("values").toString()));
            Aggregation aggregationDetailed = newAggregation(match(Criteria.where("uploadDate").gte(created).lte(newCreatedDate).and("lake").is(db.get("_id").toString())),
                    group("type").count().as("values"));
            AggregationResults<DBObject> aggregateFishTypes = aggregateFishes(aggregationDetailed);
            for (DBObject fish : aggregateFishTypes.getMappedResults()){
                FishTypeValue fishTypeValue = new FishTypeValue();
                fishTypeValue.setType(fish.get("_id").toString());
                fishTypeValue.setValue(Long.valueOf(fish.get("values").toString()));
                ls.getFishesInLake().add(fishTypeValue);
            }
            lakeStats.add(ls);
        });
    }
}
