package fact.it.animalservice.service;

import fact.it.animalservice.dto.AnimalRequest;
import fact.it.animalservice.dto.AnimalResponse;
import fact.it.animalservice.dto.FoodResponse;
import fact.it.animalservice.model.Animal;
import fact.it.animalservice.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final WebClient webClient;
    @Value("${foodservice.baseurl}")
    private String foodServiceBaseUrl;

    public List<AnimalResponse> getAllAnimals() {
        List<Animal> animals = animalRepository.findAll();

        return animals.stream().map(this::mapToAnimalResponse).toList();
    }

    public List<AnimalResponse> getAnimalById(List<Long> id) {
        List<Animal> animals = animalRepository.findAllById(id);

        return animals.stream().map(this::mapToAnimalResponse).toList();
    }

    public List<AnimalResponse> getAnimalByAnimalCode(List<String> animalCode) {
        List<Animal> animals = animalRepository.findAllByAnimalCodeIn(animalCode);

        return animals.stream().map(this::mapToAnimalResponse).toList();
    }

    public AnimalResponse createAnimal(AnimalRequest animalRequest) {
        Animal animal = Animal.builder()
                .name(animalRequest.getName())
                .animalCode(animalRequest.getAnimalCode())
                .type(animalRequest.getType())
                .birthDate(animalRequest.getBirthDate())
                .codePreferredFood(animalRequest.getCodePreferredFood())
                .build();

        animalRepository.save(animal);
        return mapToAnimalResponse(animal);
    }

    public AnimalResponse updateAnimal(long animalId, AnimalRequest updateAnimal) {
        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        if(animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            animal.setAnimalCode(updateAnimal.getAnimalCode());
            animal.setName(updateAnimal.getName());
            animal.setType(updateAnimal.getType());
            animal.setBirthDate(updateAnimal.getBirthDate());
            animal.setCodePreferredFood(updateAnimal.getCodePreferredFood());
            animalRepository.save(animal);
            return mapToAnimalResponse(animal);
        }
        return null;
    }

    public boolean deleteAnimal(Long animalId) {
        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        if(animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            animalRepository.delete(animal);
            return true;
        } else {
            return false;
        }
    }

    private AnimalResponse mapToAnimalResponse(Animal animal) {
        FoodResponse foodResponse = null;

        String foodCodeQueryParameter = animal.getCodePreferredFood();

        //only request from the food service if food code is not an empty string (would call all food endpoint)
        if(foodCodeQueryParameter != null && !foodCodeQueryParameter.equals("")) {
            FoodResponse[] foodResponseArray = webClient.get()
                    .uri("http://" + foodServiceBaseUrl + "/api/food/" + foodCodeQueryParameter)
                    .retrieve()
                    .bodyToMono(FoodResponse[].class)
                    .block();
            // assign only when food is found
            if (foodResponseArray != null && foodResponseArray.length > 0) {
                foodResponse = foodResponseArray[0];
            }
        }

        return AnimalResponse.builder()
                .id(animal.getId())
                .name(animal.getName())
                .animalCode(animal.getAnimalCode())
                .type(animal.getType())
                .birthDate(animal.getBirthDate())
                .codePreferredFood(animal.getCodePreferredFood())
                .preferredFood(foodResponse)
                .build();
    }
}
