package romanav.analizermoc.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import romanav.analizermoc.Entities.DetectedDataEntry;
import java.util.List;

public interface DetectedDataRepository extends MongoRepository<DetectedDataEntry,String> {
    DetectedDataEntry findByUuid(String uuid);
    List<DetectedDataEntry> findByPublisher(String publisher);
}
