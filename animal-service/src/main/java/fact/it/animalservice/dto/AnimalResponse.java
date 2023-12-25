package fact.it.animalservice.dto;

import fact.it.animalservice.model.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalResponse {
    private Long id;
    private String animalCode;
    private String name;
    private AnimalType type;
    private LocalDate birthDate;
    private FoodResponse preferredFood;
}
