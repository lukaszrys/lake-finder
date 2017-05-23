package pl.fishing.statistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.fishing.statistics.model.Statistics;

public interface StatisticsRepository extends MongoRepository<Statistics, String> {
    Statistics findTopByOrderByCreatedDesc();
}
