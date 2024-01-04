package fact.it.enclosureservice;

import fact.it.enclosureservice.dto.AnimalResponse;
import fact.it.enclosureservice.dto.EnclosureRequest;
import fact.it.enclosureservice.dto.EnclosureResponse;
import fact.it.enclosureservice.dto.FoodResponse;
import fact.it.enclosureservice.model.Enclosure;
import fact.it.enclosureservice.model.EnclosureSize;
import fact.it.enclosureservice.model.EnclosureType;
import fact.it.enclosureservice.repository.EnclosureRepository;
import fact.it.enclosureservice.service.EnclosureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnclosureServiceUnitTests {

    @InjectMocks
    private EnclosureService enclosureService;

    @Mock
    private EnclosureRepository enclosureRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecSavannah;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecForest;

    @Mock
    private WebClient.ResponseSpec responseSpecSavannah;
    @Mock
    private WebClient.ResponseSpec responseSpecForest;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(enclosureService, "animalServiceBaseUrl", "localhost:8082");
    }

    @Test
    public void testGetAllEnclosures() {
        // Arrange
        Enclosure enclosure1 = new Enclosure("6542c800712e1a7c6a3e77e5", "MediumSavannahLion",
                "Lion Savannah", EnclosureSize.Medium, EnclosureType.Savannah, Arrays.asList("NalaLion2002", "SimbaLion2016"));
        Enclosure enclosure2 = new Enclosure("7864b511784a5c8r6b5e84b6", "SmallForestBear",
                "Bear Forest", EnclosureSize.Small, EnclosureType.Forest, Arrays.asList("BalooBear1999"));

        when(enclosureRepository.findAll()).thenReturn(Arrays.asList(enclosure1, enclosure2));

        FoodResponse foodResponse = new FoodResponse(1L, "MeatPigFoot", "Pig foot", "Meat", "1 unit", "Germany", 26);
        AnimalResponse animalResponseNala = new AnimalResponse(1L, "NalaLion2002", "Nala", "Lion", LocalDate.parse("1994-06-15"), "MeatPigFoot", foodResponse);
        AnimalResponse animalResponseSimba = new AnimalResponse(2L, "SimbaLion2016", "Simba", "Lion", LocalDate.parse("2016-02-01"),"MeatPigFoot", foodResponse);
        AnimalResponse animalResponseBaloo = new AnimalResponse(3L, "BalooBear1999", "Baloo", "Bear", LocalDate.parse("1999-10-12"), "MeatPigFoot", foodResponse);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);

        // when the animals of the first enclosure get called
        when(requestHeadersUriSpec.uri("http://localhost:8082/api/animal/NalaLion2002,SimbaLion2016"))
                .thenReturn(requestHeadersSpecSavannah);
        when(requestHeadersSpecSavannah.retrieve())
                .thenReturn(responseSpecSavannah);
        when(responseSpecSavannah.bodyToMono(AnimalResponse[].class))
                .thenReturn(Mono.just(new AnimalResponse[]{animalResponseNala, animalResponseSimba}));

        // when the animals of the second enclosure get called
        when(requestHeadersUriSpec.uri("http://localhost:8082/api/animal/BalooBear1999"))
                .thenReturn(requestHeadersSpecForest);
        when(requestHeadersSpecForest.retrieve())
                .thenReturn(responseSpecForest);
        when(responseSpecForest.bodyToMono(AnimalResponse[].class))
                .thenReturn(Mono.just(new AnimalResponse[]{animalResponseBaloo}));

        // Act
        List<EnclosureResponse> enclosureList = enclosureService.getAllEnclosures();

        // Assert
        assertEquals(2, enclosureList.size());

        // enclosure properties
        assertEquals("MediumSavannahLion", enclosureList.get(0).getEnclosureCode());
        assertEquals("Lion Savannah", enclosureList.get(0).getName());
        assertEquals(EnclosureSize.Medium, enclosureList.get(0).getSize());
        assertEquals(EnclosureType.Savannah, enclosureList.get(0).getType());

        assertEquals("SmallForestBear", enclosureList.get(1).getEnclosureCode());
        assertEquals("Bear Forest", enclosureList.get(1).getName());
        assertEquals(EnclosureSize.Small, enclosureList.get(1).getSize());
        assertEquals(EnclosureType.Forest, enclosureList.get(1).getType());

        // animals (not checking all properties)
        assertEquals(2, enclosureList.get(0).getAnimals().size());
        assertEquals("NalaLion2002", enclosureList.get(0).getAnimals().get(0).getAnimalCode());
        assertEquals("SimbaLion2016", enclosureList.get(0).getAnimals().get(1).getAnimalCode());

        assertEquals(1, enclosureList.get(1).getAnimals().size());
        assertEquals("BalooBear1999", enclosureList.get(1).getAnimals().get(0).getAnimalCode());

        verify(enclosureRepository, times(1)).findAll();
    }
    @Test
    public void testGetEnclosureById() {
        // Arrange
        Enclosure enclosure1 = new Enclosure("6542c800712e1a7c6a3e77e5", "MediumSavannahLion",
                "Lion Savannah", EnclosureSize.Medium, EnclosureType.Savannah, Arrays.asList("NalaLion2002", "SimbaLion2016"));
        Enclosure enclosure2 = new Enclosure("7864b511784a5c8r6b5e84b6", "SmallForestBear",
                "Bear Forest", EnclosureSize.Small, EnclosureType.Forest, Arrays.asList("BalooBear1999"));

        when(enclosureRepository.findAllById(Arrays.asList("6542c800712e1a7c6a3e77e5", "7864b511784a5c8r6b5e84b6"))).thenReturn(Arrays.asList(enclosure1, enclosure2));

        FoodResponse foodResponse = new FoodResponse(1L, "MeatPigFoot", "Pig foot", "Meat", "1 unit", "Germany", 26);
        AnimalResponse animalResponseNala = new AnimalResponse(1L, "NalaLion2002", "Nala", "Lion", LocalDate.parse("1994-06-15"),"MeatPigFoot", foodResponse);
        AnimalResponse animalResponseSimba = new AnimalResponse(2L, "SimbaLion2016", "Simba", "Lion", LocalDate.parse("2016-02-01"),"MeatPigFoot", foodResponse);
        AnimalResponse animalResponseBaloo = new AnimalResponse(3L, "BalooBear1999", "Baloo", "Bear", LocalDate.parse("1999-10-12"),"MeatPigFoot", foodResponse);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);

        // when the animals of the first enclosure get called
        when(requestHeadersUriSpec.uri("http://localhost:8082/api/animal/NalaLion2002,SimbaLion2016"))
                .thenReturn(requestHeadersSpecSavannah);
        when(requestHeadersSpecSavannah.retrieve())
                .thenReturn(responseSpecSavannah);
        when(responseSpecSavannah.bodyToMono(AnimalResponse[].class))
                .thenReturn(Mono.just(new AnimalResponse[]{animalResponseNala, animalResponseSimba}));

        // when the animals of the second enclosure get called
        when(requestHeadersUriSpec.uri("http://localhost:8082/api/animal/BalooBear1999"))
                .thenReturn(requestHeadersSpecForest);
        when(requestHeadersSpecForest.retrieve())
                .thenReturn(responseSpecForest);
        when(responseSpecForest.bodyToMono(AnimalResponse[].class))
                .thenReturn(Mono.just(new AnimalResponse[]{animalResponseBaloo}));

        // Act
        List<EnclosureResponse> enclosureList = enclosureService.getEnclosureById(Arrays.asList("6542c800712e1a7c6a3e77e5", "7864b511784a5c8r6b5e84b6"));

        // Assert
        assertEquals(2, enclosureList.size());

        // enclosure properties
        assertEquals("MediumSavannahLion", enclosureList.get(0).getEnclosureCode());
        assertEquals("Lion Savannah", enclosureList.get(0).getName());
        assertEquals(EnclosureSize.Medium, enclosureList.get(0).getSize());
        assertEquals(EnclosureType.Savannah, enclosureList.get(0).getType());

        assertEquals("SmallForestBear", enclosureList.get(1).getEnclosureCode());
        assertEquals("Bear Forest", enclosureList.get(1).getName());
        assertEquals(EnclosureSize.Small, enclosureList.get(1).getSize());
        assertEquals(EnclosureType.Forest, enclosureList.get(1).getType());

        // animals (not checking all properties)
        assertEquals(2, enclosureList.get(0).getAnimals().size());
        assertEquals("NalaLion2002", enclosureList.get(0).getAnimals().get(0).getAnimalCode());
        assertEquals("SimbaLion2016", enclosureList.get(0).getAnimals().get(1).getAnimalCode());

        assertEquals(1, enclosureList.get(1).getAnimals().size());
        assertEquals("BalooBear1999", enclosureList.get(1).getAnimals().get(0).getAnimalCode());

        verify(enclosureRepository, times(1)).findAllById(Arrays.asList(enclosureList.get(0).getId(), enclosureList.get(1).getId()));
    }
    @Test
    public void testGetEnclosureByEnclosureCode() {
        // Arrange
        Enclosure enclosure1 = new Enclosure("6542c800712e1a7c6a3e77e5", "MediumSavannahLion",
                "Lion Savannah", EnclosureSize.Medium, EnclosureType.Savannah, Arrays.asList("NalaLion2002", "SimbaLion2016"));
        Enclosure enclosure2 = new Enclosure("7864b511784a5c8r6b5e84b6", "SmallForestBear",
                "Bear Forest", EnclosureSize.Small, EnclosureType.Forest, Arrays.asList("BalooBear1999"));

        when(enclosureRepository.findAllByEnclosureCodeIn(Arrays.asList("MediumSavannahLion", "SmallForestBear"))).thenReturn(Arrays.asList(enclosure1, enclosure2));

        FoodResponse foodResponse = new FoodResponse(1L, "MeatPigFoot", "Pig foot", "Meat", "1 unit", "Germany", 26);
        AnimalResponse animalResponseNala = new AnimalResponse(1L, "NalaLion2002", "Nala", "Lion", LocalDate.parse("1994-06-15"),"MeatPigFoot", foodResponse);
        AnimalResponse animalResponseSimba = new AnimalResponse(2L, "SimbaLion2016", "Simba", "Lion", LocalDate.parse("2016-02-01"),"MeatPigFoot", foodResponse);
        AnimalResponse animalResponseBaloo = new AnimalResponse(3L, "BalooBear1999", "Baloo", "Bear", LocalDate.parse("1999-10-12"),"MeatPigFoot", foodResponse);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);

        // when the animals of the first enclosure get called
        when(requestHeadersUriSpec.uri("http://localhost:8082/api/animal/NalaLion2002,SimbaLion2016"))
                .thenReturn(requestHeadersSpecSavannah);
        when(requestHeadersSpecSavannah.retrieve())
                .thenReturn(responseSpecSavannah);
        when(responseSpecSavannah.bodyToMono(AnimalResponse[].class))
                .thenReturn(Mono.just(new AnimalResponse[]{animalResponseNala, animalResponseSimba}));

        // when the animals of the second enclosure get called
        when(requestHeadersUriSpec.uri("http://localhost:8082/api/animal/BalooBear1999"))
                .thenReturn(requestHeadersSpecForest);
        when(requestHeadersSpecForest.retrieve())
                .thenReturn(responseSpecForest);
        when(responseSpecForest.bodyToMono(AnimalResponse[].class))
                .thenReturn(Mono.just(new AnimalResponse[]{animalResponseBaloo}));

        // Act
        List<EnclosureResponse> enclosureList = enclosureService.getEnclosureByEnclosureCode(Arrays.asList("MediumSavannahLion", "SmallForestBear"));

        // Assert
        assertEquals(2, enclosureList.size());

        // enclosure properties
        assertEquals("MediumSavannahLion", enclosureList.get(0).getEnclosureCode());
        assertEquals("Lion Savannah", enclosureList.get(0).getName());
        assertEquals(EnclosureSize.Medium, enclosureList.get(0).getSize());
        assertEquals(EnclosureType.Savannah, enclosureList.get(0).getType());

        assertEquals("SmallForestBear", enclosureList.get(1).getEnclosureCode());
        assertEquals("Bear Forest", enclosureList.get(1).getName());
        assertEquals(EnclosureSize.Small, enclosureList.get(1).getSize());
        assertEquals(EnclosureType.Forest, enclosureList.get(1).getType());

        // animals (not checking all properties)
        assertEquals(2, enclosureList.get(0).getAnimals().size());
        assertEquals("NalaLion2002", enclosureList.get(0).getAnimals().get(0).getAnimalCode());
        assertEquals("SimbaLion2016", enclosureList.get(0).getAnimals().get(1).getAnimalCode());

        assertEquals(1, enclosureList.get(1).getAnimals().size());
        assertEquals("BalooBear1999", enclosureList.get(1).getAnimals().get(0).getAnimalCode());

        verify(enclosureRepository, times(1)).findAllByEnclosureCodeIn(Arrays.asList(enclosureList.get(0).getEnclosureCode(), enclosureList.get(1).getEnclosureCode()));
    }
    @Test
    public void testCreateEnclosure() {
        // Arrange
        EnclosureRequest enclosureRequest = new EnclosureRequest("LargeSavannahLion", "Lion's Savannah",
                EnclosureSize.Large, EnclosureType.Savannah, Arrays.asList("NalaLion2002", "SimbaLion2016"));

        FoodResponse foodResponse = new FoodResponse(1L, "MeatPigFoot", "Pig foot", "Meat", "1 unit", "Germany", 26);
        AnimalResponse animalResponseNala = new AnimalResponse(1L, "NalaLion2002", "Nala", "Lion", LocalDate.parse("1994-06-15"),"MeatPigFoot", foodResponse);
        AnimalResponse animalResponseSimba = new AnimalResponse(2L, "SimbaLion2016", "Simba", "Lion", LocalDate.parse("2016-02-01"),"MeatPigFoot", foodResponse);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("http://localhost:8082/api/animal/NalaLion2002,SimbaLion2016"))
                .thenReturn(requestHeadersSpecSavannah);
        when(requestHeadersSpecSavannah.retrieve())
                .thenReturn(responseSpecSavannah);
        when(responseSpecSavannah.bodyToMono(AnimalResponse[].class))
                .thenReturn(Mono.just(new AnimalResponse[]{animalResponseNala, animalResponseSimba}));

        // Act
        EnclosureResponse createdEnclosure = enclosureService.createEnclosure(enclosureRequest);

        // Assert
        // enclosure properties
        assertEquals("LargeSavannahLion", createdEnclosure.getEnclosureCode());
        assertEquals("Lion's Savannah", createdEnclosure.getName());
        assertEquals(EnclosureSize.Large, createdEnclosure.getSize());
        assertEquals(EnclosureType.Savannah, createdEnclosure.getType());

        // animals (not checking all properties)
        assertEquals(2, createdEnclosure.getAnimals().size());
        assertEquals("NalaLion2002", createdEnclosure.getAnimals().get(0).getAnimalCode());
        assertEquals("SimbaLion2016", createdEnclosure.getAnimals().get(1).getAnimalCode());

        verify(enclosureRepository, times(1)).save(any(Enclosure.class));
    }

    @Test
    public void testUpdateEnclosure_Success() {
        // Arrange
        Enclosure enclosure1 = new Enclosure("6542c800712e1a7c6a3e77e5", "MediumSavannahLion",
                "Lion Savannah", EnclosureSize.Medium, EnclosureType.Savannah, Arrays.asList("NalaLion2002", "SimbaLion2016"));
        EnclosureRequest enclosureToUpdate = new EnclosureRequest("LargeForestLion",
                "Lion Forest", EnclosureSize.Large, EnclosureType.Forest, Arrays.asList("NalaLion2002", "SimbaLion2016"));

        FoodResponse foodResponse = new FoodResponse(1L, "MeatPigFoot", "Pig foot", "Meat", "1 unit", "Germany", 26);
        AnimalResponse animalResponseNala = new AnimalResponse(1L, "NalaLion2002", "Nala", "Lion", LocalDate.parse("1994-06-15"),"MeatPigFoot", foodResponse);
        AnimalResponse animalResponseSimba = new AnimalResponse(2L, "SimbaLion2016", "Simba", "Lion", LocalDate.parse("2016-02-01"),"MeatPigFoot", foodResponse);

        when(enclosureRepository.findById("6542c800712e1a7c6a3e77e5")).thenReturn(Optional.of(enclosure1));

        when(webClient.get()).thenReturn(requestHeadersUriSpec);

        // when the animals of the first enclosure get called
        when(requestHeadersUriSpec.uri("http://localhost:8082/api/animal/NalaLion2002,SimbaLion2016"))
                .thenReturn(requestHeadersSpecSavannah);
        when(requestHeadersSpecSavannah.retrieve())
                .thenReturn(responseSpecSavannah);
        when(responseSpecSavannah.bodyToMono(AnimalResponse[].class))
                .thenReturn(Mono.just(new AnimalResponse[]{animalResponseNala, animalResponseSimba}));

        // Act
        EnclosureResponse updatedEnclosure = enclosureService.updateEnclosure("6542c800712e1a7c6a3e77e5", enclosureToUpdate);

        // Assert
        assertEquals("LargeForestLion", updatedEnclosure.getEnclosureCode());
        assertEquals("Lion Forest", updatedEnclosure.getName());
        assertEquals(EnclosureSize.Large, updatedEnclosure.getSize());
        assertEquals(EnclosureType.Forest, updatedEnclosure.getType());

        assertEquals(2, updatedEnclosure.getAnimals().size());
        assertEquals("NalaLion2002", updatedEnclosure.getAnimals().get(0).getAnimalCode());
        assertEquals("SimbaLion2016", updatedEnclosure.getAnimals().get(1).getAnimalCode());

        verify(enclosureRepository, times(1)).findById("6542c800712e1a7c6a3e77e5");
        verify(enclosureRepository, times(1)).save(any(Enclosure.class));
    }

    @Test
    public void testUpdateEnclosure_Failure() {
        // Arrange
        EnclosureRequest enclosureToUpdate = new EnclosureRequest("LargeForestLion",
                "Lion Forest", EnclosureSize.Large, EnclosureType.Forest, Arrays.asList("NalaLion2002", "SimbaLion2016"));

        when(enclosureRepository.findById("6542c800712e1a7c6a3e77e5")).thenReturn(Optional.empty());

        // Act
        EnclosureResponse updatedEnclosure = enclosureService.updateEnclosure("6542c800712e1a7c6a3e77e5", enclosureToUpdate);

        // Assert
        assertNull(updatedEnclosure);

        verify(enclosureRepository, times(1)).findById("6542c800712e1a7c6a3e77e5");
        verify(enclosureRepository, never()).save(any(Enclosure.class));
    }
    @Test
    public void testDeleteEnclosure_Success() {
        // Arrange
        Enclosure enclosure1 = new Enclosure("6542c800712e1a7c6a3e77e5", "MediumSavannahLion",
                "Lion Savannah", EnclosureSize.Medium, EnclosureType.Savannah, Arrays.asList("NalaLion2002", "SimbaLion2016"));

        when(enclosureRepository.findById("6542c800712e1a7c6a3e77e5")).thenReturn(Optional.of(enclosure1));

        // Act
        boolean wasDeleted = enclosureService.deleteEnclosure("6542c800712e1a7c6a3e77e5");

        // Assert
        assertTrue(wasDeleted);

        verify(enclosureRepository, times(1)).delete(any(Enclosure.class));
    }
    @Test
    public void testDeleteEnclosure_Failure() {
        // Arrange
        when(enclosureRepository.findById("6542c800712e1a7c6a3e77e5")).thenReturn(Optional.empty());

        // Act
        boolean wasDeleted = enclosureService.deleteEnclosure("6542c800712e1a7c6a3e77e5");

        // Assert
        assertFalse(wasDeleted);

        verify(enclosureRepository, never()).delete(any(Enclosure.class));
    }
}
