package fact.it.foodservice;

import fact.it.foodservice.dto.FoodRequest;
import fact.it.foodservice.dto.FoodResponse;
import fact.it.foodservice.model.Food;
import fact.it.foodservice.repository.FoodRepository;
import fact.it.foodservice.service.FoodService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoodServiceUnitTest {

    @InjectMocks
    private FoodService foodService;

    @Mock
    private FoodRepository foodRepository;

    @Test
    public void testGetAllFood() {
        // Arrange
        Food food1 = new Food(1L, "VegetableCorn", "Corn", "Vegetable", "20 units", "Belgium", 405);
        Food food2 = new Food(2L, "MeatPigFoot", "Pig foot", "Meat", "1 unit", "Germany", 23);

        when(foodRepository.findAll()).thenReturn(Arrays.asList(food1, food2));

        // Act
        List<FoodResponse> foodList = foodService.getAllFood();

        // Assert
        assertEquals(2, foodList.size());
        assertEquals("VegetableCorn", foodList.get(0).getFoodCode());
        assertEquals("Corn", foodList.get(0).getName());
        assertEquals("Vegetable", foodList.get(0).getCategory());
        assertEquals("20 units", foodList.get(0).getServingSize());
        assertEquals("Belgium", foodList.get(0).getOrigin());
        assertEquals(405, foodList.get(0).getStock());

        verify(foodRepository, times(1)).findAll();
    }

    //retrieval with muliple id's, could also do with 1 id
    @Test
    public void testGetFoodById() {
        // Arrange
        Food food1 = new Food(1L, "VegetableCorn", "Corn", "Vegetable", "20 units", "Belgium", 405);
        Food food2 = new Food(2L, "MeatPigFoot", "Pig foot", "Meat", "1 unit", "Germany", 23);

        when(foodRepository.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(food1, food2));

        // Act
        List<FoodResponse> foodList = foodService.getFoodById(Arrays.asList(1L, 2L));

        // Assert
        assertEquals(2, foodList.size());

        assertEquals("VegetableCorn", foodList.get(0).getFoodCode());
        assertEquals("Corn", foodList.get(0).getName());
        assertEquals("Vegetable", foodList.get(0).getCategory());
        assertEquals("20 units", foodList.get(0).getServingSize());
        assertEquals("Belgium", foodList.get(0).getOrigin());
        assertEquals(405, foodList.get(0).getStock());

        assertEquals("MeatPigFoot", foodList.get(1).getFoodCode());
        assertEquals("Pig foot", foodList.get(1).getName());
        assertEquals("Meat", foodList.get(1).getCategory());
        assertEquals("1 unit", foodList.get(1).getServingSize());
        assertEquals("Germany", foodList.get(1).getOrigin());
        assertEquals(23, foodList.get(1).getStock());

        verify(foodRepository, times(1)).findAllById(Arrays.asList(foodList.get(0).getId(), foodList.get(1).getId()));
    }

    //retrieval with muliple codes, could also do with 1 code
    @Test
    public void testGetFoodByFoodCode() {
        // Arrange
        Food food1 = new Food(1L, "VegetableCorn", "Corn", "Vegetable", "20 units", "Belgium", 405);
        Food food2 = new Food(2L, "MeatPigFoot", "Pig foot", "Meat", "1 unit", "Germany", 23);

        when(foodRepository.findAllByFoodCodeIn(Arrays.asList("VegetableCorn", "MeatPigFoot"))).thenReturn(Arrays.asList(food1, food2));

        // Act
        List<FoodResponse> foodList = foodService.getFoodByFoodCode(Arrays.asList("VegetableCorn", "MeatPigFoot"));

        // Assert
        assertEquals(2, foodList.size());

        assertEquals("VegetableCorn", foodList.get(0).getFoodCode());
        assertEquals("Corn", foodList.get(0).getName());
        assertEquals("Vegetable", foodList.get(0).getCategory());
        assertEquals("20 units", foodList.get(0).getServingSize());
        assertEquals("Belgium", foodList.get(0).getOrigin());
        assertEquals(405, foodList.get(0).getStock());

        assertEquals("MeatPigFoot", foodList.get(1).getFoodCode());
        assertEquals("Pig foot", foodList.get(1).getName());
        assertEquals("Meat", foodList.get(1).getCategory());
        assertEquals("1 unit", foodList.get(1).getServingSize());
        assertEquals("Germany", foodList.get(1).getOrigin());
        assertEquals(23, foodList.get(1).getStock());

        verify(foodRepository, times(1)).findAllByFoodCodeIn(Arrays.asList(foodList.get(0).getFoodCode(), foodList.get(1).getFoodCode()));
    }
    @Test
    public void testCreateFood() {
        // Arrange
        FoodRequest foodRequest = new FoodRequest("Bamboo", "VegetableBamboo", "Vegetable", "5 units", "China", 150);

        // Act
        FoodResponse food = foodService.createFood(foodRequest);

        // Assert
        assertEquals("VegetableBamboo", food.getFoodCode());
        assertEquals("Bamboo", food.getName());
        assertEquals("Vegetable", food.getCategory());
        assertEquals("5 units", food.getServingSize());
        assertEquals("China", food.getOrigin());
        assertEquals(150, food.getStock());

        verify(foodRepository, times(1)).save(any(Food.class));
    }
    @Test
    public void testUpdateStock_PostiveUpdate() {
        // Arrange
        Food food1 = new Food(1L, "VegetableCorn", "Corn", "Vegetable", "20 units", "Belgium", 405);

        when(foodRepository.findById(1L)).thenReturn(Optional.of(food1));

        // Act
        FoodResponse updatedFood = foodService.updateStock(1L, 50);

        // Assert
        assertEquals(455, updatedFood.getStock());

        verify(foodRepository, times(1)).save(any(Food.class));
    }
    @Test
    public void testUpdateStock_NegativeUpdate() {
        // Arrange
        Food food1 = new Food(1L, "VegetableCorn", "Corn", "Vegetable", "20 units", "Belgium", 405);

        when(foodRepository.findById(1L)).thenReturn(Optional.of(food1));

        // Act
        FoodResponse updatedFood = foodService.updateStock(1L, -55);

        // Assert
        assertEquals(350, updatedFood.getStock());

        verify(foodRepository, times(1)).save(any(Food.class));
    }
    @Test
    public void testUpdateStock_WhenNegativeStock_ChangeToZero() {
        // Arrange
        Food food1 = new Food(1L, "VegetableCorn", "Corn", "Vegetable", "20 units", "Belgium", 20);

        when(foodRepository.findById(1L)).thenReturn(Optional.of(food1));

        // Act
        FoodResponse updatedFood = foodService.updateStock(1L, -40);

        // Assert
        assertEquals(0, updatedFood.getStock());

        verify(foodRepository, times(1)).save(any(Food.class));
    }
    @Test
    public void testDeleteFood_Success() {
        // Arrange
        Food food1 = new Food(1L, "VegetableCorn", "Corn", "Vegetable", "20 units", "Belgium", 405);

        when(foodRepository.findById(1L)).thenReturn(Optional.of(food1));

        // Act
        boolean wasDeleted = foodService.deleteFood(1L);

        // Assert
        assertTrue(wasDeleted);

        verify(foodRepository, times(1)).delete(any(Food.class));
    }
    @Test
    public void testDeleteFood_Failure() {
        // Arrange

        // Food food1 = new Food(1L, "VegetableCorn", "Corn", "Vegetable", "20 units", "Belgium", 405);
        // when(foodRepository.findById(1L)).thenReturn(Optional.of(food1));

        when(foodRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        boolean wasDeleted = foodService.deleteFood(2L);

        // Assert
        assertFalse(wasDeleted);

        verify(foodRepository, never()).delete(any(Food.class));
    }
}
