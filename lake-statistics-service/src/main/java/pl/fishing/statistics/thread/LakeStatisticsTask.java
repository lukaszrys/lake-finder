package pl.fishing.statistics.thread;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import pl.fishing.statistics.model.FishTypeValue;
import pl.fishing.statistics.model.LakeStats;
import pl.fishing.statistics.model.Statistics;
import pl.fishing.statistics.repository.StatisticsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class LakeStatisticsTask implements Callable<Date> {

    private StatisticsRepository statisticsRepository;

    private MongoTemplate mongoTemplate;

    private Date created;

    private int daysToAdd;

    private final AggregationOperation project = aggregationOperationContext -> new BasicDBObject("$project", new BasicDBObject("array", Arrays.asList("$_id.type" , "$count"))
            .append("count", "$count"));
    private final  AggregationOperation group = aggregationOperationContext -> new BasicDBObject("$group", new BasicDBObject("_id", new BasicDBObject("lake","$_id.lake"))
            .append("count", new BasicDBObject("$sum", "$count"))
            .append("types", new BasicDBObject("$addToSet", "$array")));

    public LakeStatisticsTask(Date created, int daysToAdd, StatisticsRepository statisticsRepository, MongoTemplate mongoTemplate){
        this.created = created;
        this.daysToAdd = daysToAdd;
        this.statisticsRepository = statisticsRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Date call() {
        Statistics newStatistics = new Statistics();
        created = new DateTime(created).plusDays(this.daysToAdd).toDate();
        Date newCreatedDate = new DateTime(created).plusDays(this.daysToAdd + 1).toDate();
        newStatistics.setCreated(newCreatedDate);

        List<DBObject> allResult = getAggregationResult(newCreatedDate);
        List<LakeStats> lakeStats = new ArrayList<>();
        streamResultIntoStats(allResult, lakeStats);

        newStatistics.getLakeStats().addAll(lakeStats);
        statisticsRepository.save(newStatistics);
        return newCreatedDate;
    }

    private List<DBObject> getAggregationResult(Date newCreatedDate) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("uploadDate").gte(created).lte(newCreatedDate)),
                group("lake", "type").count().as("count"),
                project, group);
        AggregationResults<DBObject> aggregateFishes = aggregateFishes(aggregation);
        return new ArrayList<>(aggregateFishes.getMappedResults());
    }

    private AggregationResults<DBObject> aggregateFishes(Aggregation aggregation){
        return mongoTemplate.aggregate(aggregation, "fish", DBObject.class);
    }

    private void streamResultIntoStats(List<DBObject> allResult, List<LakeStats> lakeStats) {
        allResult.stream().forEach(db -> {
            LakeStats ls = new LakeStats();
            ls.setId(db.get("lake").toString());
            ls.setTotal(Long.valueOf(db.get("count").toString()));
            db.get("types");
            try {
                for (Object type : ((BasicDBList) db.get("types"))){
                    FishTypeValue fishTypeValue = new FishTypeValue();
                    BasicDBList castedType = (BasicDBList) type;
                    fishTypeValue.setType(castedType.get(0).toString());
                    fishTypeValue.setValue(Long.valueOf(castedType.get(1).toString()));
                    ls.getFishesInLake().add(fishTypeValue);
                }
            } catch (ClassCastException e){

            }
            lakeStats.add(ls);
        });
    }
}
