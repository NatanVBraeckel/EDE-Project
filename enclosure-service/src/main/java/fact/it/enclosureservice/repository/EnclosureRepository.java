package fact.it.enclosureservice.repository;

import fact.it.enclosureservice.model.Enclosure;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnclosureRepository extends MongoRepository<Enclosure, String> {
}
