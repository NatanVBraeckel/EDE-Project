package fact.it.animalservice.repository;

import fact.it.animalservice.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findAllByAnimalCodeIn(Collection<String> animalCode);
}
