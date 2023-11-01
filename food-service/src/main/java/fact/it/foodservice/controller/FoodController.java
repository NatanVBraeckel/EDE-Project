package fact.it.foodservice.controller;

import fact.it.foodservice.dto.FoodRequest;
import fact.it.foodservice.dto.FoodResponse;
import fact.it.foodservice.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createAnimal(@RequestBody FoodRequest foodRequest) {
        foodService.createFood(foodRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FoodResponse> getAllFood() {
        return foodService.getAllFood();
    }

    @GetMapping("/byId/{foodId}")
    @ResponseStatus(HttpStatus.OK)
    public List<FoodResponse> getFoodById(@PathVariable List<Long> foodId) {
        return foodService.getFoodById(foodId);
    }

    @GetMapping("/{foodCode}")
    @ResponseStatus(HttpStatus.OK)
    public List<FoodResponse> getFoodByFoodCode(@PathVariable List<String> foodCode) {
        return foodService.getFoodByFoodCode(foodCode);
    }

    @PutMapping("/{foodId}/updateStock")
    public ResponseEntity<FoodResponse> updateStock(@PathVariable Long foodId, @RequestBody int amount) {
        FoodResponse foodResponse = foodService.updateStock(foodId, amount);
        if(foodResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foodResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<?> deleteFood(@PathVariable Long foodId) {
        if(foodService.deleteFood(foodId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Food item not found with id: " + foodId);
        }
    }

}