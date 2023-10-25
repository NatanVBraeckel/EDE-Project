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

    public void createAnimal(AnimalRequest animalRequest) {
        Animal animal = Animal.builder()
                .name(animalRequest.getName())
                .animalCode(animalRequest.getAnimalCode())
                .type(animalRequest.getType())
                .birthDate(animalRequest.getBirthDate())
                .preferredFood(animalRequest.getPreferredFood())
                .build();

        animalRepository.save(animal);
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

        FoodResponse[] foodResponseArray = webClient.get()
                .uri("http://" + foodServiceBaseUrl + "/api/food/" + animal.getPreferredFood())
                .retrieve()
                .bodyToMono(FoodResponse[].class)
                .block();
        FoodResponse foodResponse = foodResponseArray[0];

        return AnimalResponse.builder()
                .id(animal.getId())
                .name(animal.getName())
                .animalCode(animal.getAnimalCode())
                .type(animal.getType())
                .birthDate(animal.getBirthDate())
                .preferredFood(foodResponse)
                .build();
    }
}
