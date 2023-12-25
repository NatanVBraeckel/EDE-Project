package fact.it.animalservice;

import fact.it.animalservice.dto.AnimalRequest;
import fact.it.animalservice.dto.AnimalResponse;
import fact.it.animalservice.dto.FoodResponse;
import fact.it.animalservice.model.Animal;
import fact.it.animalservice.model.AnimalType;
import fact.it.animalservice.repository.AnimalRepository;
import fact.it.animalservice.service.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceUnitTests {

    @InjectMocks
    private AnimalService animalService;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(animalService, "foodServiceBaseUrl", "localhost:8081");
    }

    @Test
    public void testGetAllAnimals() {
        // Arrange
        Animal animal1 = new Animal(1L, "TonyTiger1957", "Tony", AnimalType.Tiger, LocalDate.parse("1957-02-01"), "MeatPigFoot");
        Animal animal2 = new Animal(2L, "NalaLion1994", "Nala", AnimalType.Lion, LocalDate.parse("1994-06-15"), "MeatPigFoot");

        when(animalRepository.findAll()).thenReturn(Arrays.asList(animal1, animal2));

        FoodResponse foodResponse = new FoodResponse(1L, "MeatPigFoot", "Pig foot", "Meat", "1 unit", "Germany",26);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(FoodResponse[].class)).thenReturn(Mono.just(new FoodResponse[]{foodResponse}));

        // Act
        List<AnimalResponse> animalList = animalService.getAllAnimals();

        // Assert
        assertEquals(2, animalList.size());

        // animal properties
        assertEquals("TonyTiger1957", animalList.get(0).getAnimalCode());
        assertEquals("Tony", animalList.get(0).getName());
        assertEquals(AnimalType.Tiger, animalList.get(0).getType());
        assertEquals(LocalDate.parse("1957-02-01"), animalList.get(0).getBirthDate());

        assertEquals("NalaLion1994", animalList.get(1).getAnimalCode());
        assertEquals("Nala", animalList.get(1).getName());
        assertEquals(AnimalType.Lion, animalList.get(1).getType());
        assertEquals(LocalDate.parse("1994-06-15"), animalList.get(1).getBirthDate());
        // food properties
        assertEquals("MeatPigFoot", animalList.get(0).getPreferredFood().getFoodCode());
        assertEquals("Pig foot", animalList.get(0).getPreferredFood().getName());
        assertEquals("Meat", animalList.get(0).getPreferredFood().getCategory());
        assertEquals("1 unit", animalList.get(0).getPreferredFood().getServingSize());
        assertEquals("Germany", animalList.get(0).getPreferredFood().getOrigin());
        assertEquals(26, animalList.get(0).getPreferredFood().getStock());

        verify(animalRepository, times(1)).findAll();
    }

    @Test
    public void testGetAnimalById() {
        // Arrange
        Animal animal1 = new Animal(1L, "TonyTiger1957", "Tony", AnimalType.Tiger, LocalDate.parse("1957-02-01"), "MeatPigFoot");
        Animal animal2 = new Animal(2L, "NalaLion1994", "Nala", AnimalType.Lion, LocalDate.parse("1994-06-15"), "MeatPigFoot");

        when(animalRepository.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(animal1, animal2));

        FoodResponse foodResponse = new FoodResponse(1L, "MeatPigFoot", "Pig foot", "Meat", "1 unit", "Germany",26);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(FoodResponse[].class)).thenReturn(Mono.just(new FoodResponse[]{foodResponse}));

        // Act
        List<AnimalResponse> animalList = animalService.getAnimalById(Arrays.asList(1L, 2L));

        // Assert
        assertEquals(2, animalList.size());

        // animal properties
        assertEquals("TonyTiger1957", animalList.get(0).getAnimalCode());
        assertEquals("Tony", animalList.get(0).getName());
        assertEquals(AnimalType.Tiger, animalList.get(0).getType());
        assertEquals(LocalDate.parse("1957-02-01"), animalList.get(0).getBirthDate());

        assertEquals("NalaLion1994", animalList.get(1).getAnimalCode());
        assertEquals("Nala", animalList.get(1).getName());
        assertEquals(AnimalType.Lion, animalList.get(1).getType());
        assertEquals(LocalDate.parse("1994-06-15"), animalList.get(1).getBirthDate());
        // food properties
        assertEquals("MeatPigFoot", animalList.get(0).getPreferredFood().getFoodCode());
        assertEquals("Pig foot", animalList.get(0).getPreferredFood().getName());
        assertEquals("Meat", animalList.get(0).getPreferredFood().getCategory());
        assertEquals("1 unit", animalList.get(0).getPreferredFood().getServingSize());
        assertEquals("Germany", animalList.get(0).getPreferredFood().getOrigin());
        assertEquals(26, animalList.get(0).getPreferredFood().getStock());

        verify(animalRepository, times(1)).findAllById(Arrays.asList(animalList.get(0).getId(), animalList.get(1).getId()));
    }
    @Test
    public void testGetAnimalByAnimalCode() {
        // Arrange
        Animal animal1 = new Animal(1L, "TonyTiger1957", "Tony", AnimalType.Tiger, LocalDate.parse("1957-02-01"), "MeatPigFoot");
        Animal animal2 = new Animal(2L, "NalaLion1994", "Nala", AnimalType.Lion, LocalDate.parse("1994-06-15"), "MeatPigFoot");

        when(animalRepository.findAllByAnimalCodeIn(Arrays.asList("TonyTiger1957", "NalaLion1994"))).thenReturn(Arrays.asList(animal1, animal2));

        FoodResponse foodResponse = new FoodResponse(1L, "MeatPigFoot", "Pig foot", "Meat", "1 unit", "Germany",26);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(FoodResponse[].class)).thenReturn(Mono.just(new FoodResponse[]{foodResponse}));

        // Act
        List<AnimalResponse> animalList = animalService.getAnimalByAnimalCode(Arrays.asList("TonyTiger1957", "NalaLion1994"));

        // Assert
        assertEquals(2, animalList.size());

        // animal properties
        assertEquals("TonyTiger1957", animalList.get(0).getAnimalCode());
        assertEquals("Tony", animalList.get(0).getName());
        assertEquals(AnimalType.Tiger, animalList.get(0).getType());
        assertEquals(LocalDate.parse("1957-02-01"), animalList.get(0).getBirthDate());

        assertEquals("NalaLion1994", animalList.get(1).getAnimalCode());
        assertEquals("Nala", animalList.get(1).getName());
        assertEquals(AnimalType.Lion, animalList.get(1).getType());
        assertEquals(LocalDate.parse("1994-06-15"), animalList.get(1).getBirthDate());
        // food properties
        assertEquals("MeatPigFoot", animalList.get(0).getPreferredFood().getFoodCode());
        assertEquals("Pig foot", animalList.get(0).getPreferredFood().getName());
        assertEquals("Meat", animalList.get(0).getPreferredFood().getCategory());
        assertEquals("1 unit", animalList.get(0).getPreferredFood().getServingSize());
        assertEquals("Germany", animalList.get(0).getPreferredFood().getOrigin());
        assertEquals(26, animalList.get(0).getPreferredFood().getStock());

        verify(animalRepository, times(1)).findAllByAnimalCodeIn(Arrays.asList(animalList.get(0).getAnimalCode(), animalList.get(1).getAnimalCode()));
    }
    @Test
    public void testCreateAnimal() {
        // Arrange
        AnimalRequest animalRequest = new AnimalRequest("Po", "PoPanda2008", AnimalType.Panda, LocalDate.parse("2008-07-09"), "VegetableBamboo");

        FoodResponse foodResponse = new FoodResponse(1L, "VegetableBamboo", "Bamboo", "Vegetable", "15 units", "China",165);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(FoodResponse[].class)).thenReturn(Mono.just(new FoodResponse[]{foodResponse}));

        // Act
        AnimalResponse createdAnimal = animalService.createAnimal(animalRequest);

        // Assert
        assertEquals("PoPanda2008", createdAnimal.getAnimalCode());
        assertEquals("Po", createdAnimal.getName());
        assertEquals(AnimalType.Panda, createdAnimal.getType());
        assertEquals(LocalDate.parse("2008-07-09"), createdAnimal.getBirthDate());

        assertEquals("VegetableBamboo", createdAnimal.getPreferredFood().getFoodCode());
        assertEquals("Bamboo", createdAnimal.getPreferredFood().getName());
        assertEquals("Vegetable", createdAnimal.getPreferredFood().getCategory());
        assertEquals("15 units", createdAnimal.getPreferredFood().getServingSize());
        assertEquals("China", createdAnimal.getPreferredFood().getOrigin());
        assertEquals(165, createdAnimal.getPreferredFood().getStock());

        verify(animalRepository, times(1)).save(any(Animal.class));
    }
    @Test
    public void testUpdateAnimal_Success() {
        // Arrange
        Animal animal1 = new Animal(1L, "TonyTiger1957", "Tony", AnimalType.Tiger, LocalDate.parse("1957-02-01"), "MeatPigFoot");
        AnimalRequest animalToUpdate = new AnimalRequest("Timmy", "TimmyTiger1957", AnimalType.Tiger, LocalDate.parse("1957-02-01"), "MeatPigFoot");

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal1));

        FoodResponse foodResponse = new FoodResponse(1L, "VegetableBamboo", "Bamboo", "Vegetable", "15 units", "China",165);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(FoodResponse[].class)).thenReturn(Mono.just(new FoodResponse[]{foodResponse}));

        // Act
        AnimalResponse updatedAnimal = animalService.updateAnimal(1L, animalToUpdate);

        // Assert
        assertEquals("TimmyTiger1957", updatedAnimal.getAnimalCode());
        assertEquals("Timmy", updatedAnimal.getName());
        assertEquals(AnimalType.Tiger, updatedAnimal.getType());
        assertEquals(LocalDate.parse("1957-02-01"), updatedAnimal.getBirthDate());

        verify(animalRepository, times(1)).findById(1L);
        verify(animalRepository, times(1)).save(any(Animal.class));
    }

    @Test
    public void testUpdateAnimal_Failure() {
        // Arrange
        AnimalRequest animalToUpdate = new AnimalRequest("Timmy", "TimmyTiger1957", AnimalType.Tiger, LocalDate.parse("1957-02-01"), "MeatPigFoot");

        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        AnimalResponse updatedAnimal = animalService.updateAnimal(1L, animalToUpdate);

        // Assert
        assertNull(updatedAnimal);

        verify(animalRepository, times(1)).findById(1L);
        // the save was not called due to no animal being found
        verify(animalRepository, never()).save(any(Animal.class));
    }
    @Test
    public void testDeleteAnimal_Success() {
        // Arrange
        Animal animal1 = new Animal(1L, "TonyTiger1957", "Tony", AnimalType.Tiger, LocalDate.parse("1957-02-01"), "MeatPigFoot");

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal1));

        // Act
        boolean wasDeleted = animalService.deleteAnimal(1L);

        // Assert
        assertTrue(wasDeleted);

        verify(animalRepository, times(1)).delete(any(Animal.class));
    }
    @Test
    public void testDeleteAnimal_Failure() {
        // Arrange
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        boolean wasDeleted = animalService.deleteAnimal(1L);

        // Assert
        assertFalse(wasDeleted);

        verify(animalRepository, never()).delete(any(Animal.class));
    }
}
