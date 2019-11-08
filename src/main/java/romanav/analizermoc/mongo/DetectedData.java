package romanav.analizermoc.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import romanav.analizermoc.Entities.DetectorEntry;

public interface DetectedData extends MongoRepository<DetectorEntry,String> {

    DetectorEntry findByUuid(String uuid);
}
