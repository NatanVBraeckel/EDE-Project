package fact.it.foodservice.repository;

import fact.it.foodservice.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findAllByFoodCodeIn(Collection<String> foodCode);
}
