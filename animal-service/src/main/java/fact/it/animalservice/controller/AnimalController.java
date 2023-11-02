package fact.it.animalservice.controller;

import fact.it.animalservice.dto.AnimalRequest;
import fact.it.animalservice.dto.AnimalResponse;
import fact.it.animalservice.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping
    public ResponseEntity<AnimalResponse> createAnimal(@RequestBody AnimalRequest animalRequest) {
        AnimalResponse animalResponse = animalService.createAnimal(animalRequest);
        return new ResponseEntity<>(animalResponse, HttpStatus.OK);
    }

    @GetMapping("/byId/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalResponse> getAnimalById(@PathVariable List<Long> animalId) {
        return animalService.getAnimalById(animalId);
    }

    @GetMapping("/{animalCode}")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalResponse> getAnimalByAnimalCode(@PathVariable List<String> animalCode) {
        return animalService.getAnimalByAnimalCode(animalCode);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalResponse> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    @PutMapping("/{animalId}")
    public ResponseEntity<AnimalResponse> updateAnimal(@PathVariable Long animalId, @RequestBody AnimalRequest updateAnimal) {
        AnimalResponse animalResponse = animalService.updateAnimal(animalId, updateAnimal);
        if(animalResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(animalResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<?> deleteAnimal(@PathVariable Long animalId) {
        if(animalService.deleteAnimal(animalId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Animal not found with id: " + animalId);
        }
    }
}
