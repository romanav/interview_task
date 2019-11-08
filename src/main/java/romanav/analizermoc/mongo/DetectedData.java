package romanav.analizermoc.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DetectedData extends MongoRepository<String,String> {

    String findByUuid(String uuid);
}
