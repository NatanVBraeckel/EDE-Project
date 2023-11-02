package fact.it.foodservice.service;

import fact.it.foodservice.dto.FoodRequest;
import fact.it.foodservice.dto.FoodResponse;
import fact.it.foodservice.model.Food;
import fact.it.foodservice.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public List<FoodResponse> getAllFood() {
        List<Food> food = foodRepository.findAll();

        return food.stream().map(this::mapToFoodResponse).toList();
    }

    public List<FoodResponse> getFoodById(List<Long> id) {
        List<Food> food = foodRepository.findAllById(id);

        return food.stream().map(this::mapToFoodResponse).toList();
    }

    public List<FoodResponse> getFoodByFoodCode(List<String> foodCode) {
        List<Food> food = foodRepository.findAllByFoodCodeIn(foodCode);

        return food.stream().map(this::mapToFoodResponse).toList();
    }

    public FoodResponse createFood(FoodRequest foodRequest) {
        Food food = Food.builder()
                .foodCode(foodRequest.getFoodCode())
                .name(foodRequest.getName())
                .category(foodRequest.getCategory())
                .servingSize(foodRequest.getServingSize())
                .origin(foodRequest.getOrigin())
                .stock(foodRequest.getStock())
                .build();

        foodRepository.save(food);
        return mapToFoodResponse(food);
    }

    public FoodResponse updateStock(Long foodId, int amount) {
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if(foodOptional.isPresent()) {
            Food food = foodOptional.get();
            int currentStock = food.getStock();
            // Math.max geeft grootste waarde terug, dus 0 als het negatief is
            food.setStock(Math.max(currentStock + amount, 0));
            foodRepository.save(food);
            return mapToFoodResponse(food);
        } else {
            return null;
        }
    }

    public boolean deleteFood(Long foodId) {
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if(foodOptional.isPresent()) {
            Food food = foodOptional.get();
            foodRepository.delete(food);
            return true;
        } else {
            return false;
        }
    }

    private FoodResponse mapToFoodResponse(Food food) {
        return FoodResponse.builder()
                .id(food.getId())
                .foodCode(food.getFoodCode())
                .name(food.getName())
                .category(food.getCategory())
                .servingSize(food.getServingSize())
                .origin(food.getOrigin())
                .stock(food.getStock())
                .build();
    }

}
