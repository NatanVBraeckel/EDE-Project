package fact.it.enclosureservice.repository;

import fact.it.enclosureservice.dto.EnclosureResponse;
import fact.it.enclosureservice.model.Enclosure;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface EnclosureRepository extends MongoRepository<Enclosure, String> {

    List<Enclosure> findAllByEnclosureCodeIn(Collection<String> enclosures);
}
