package pl.fishing.statistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.fishing.statistics.model.Statistics;

import java.util.Date;
import java.util.List;

public interface StatisticsRepository extends MongoRepository<Statistics, String> {
    Statistics findTopByOrderByCreatedDesc();

    @Query(value = "{'lakeStats': { $elemMatch: { '_id': ?2 } }, 'created': {$gte: ?0, $lte: ?1}, 'status': ?2 } }")
    List<Statistics> findByDatesBeetwen(Date dateFrom, Date dateTo, String lakeId);

    @Query(value = "{'lakeStats': { $elemMatch: { '_id': ?2 } }, 'created': {$gte: ?0, $lte: ?1}, 'status': ?2 } }", count = true)
    Long countByDatesBeetwen(Date dateFrom, Date dateTo, String lakeId);
}
