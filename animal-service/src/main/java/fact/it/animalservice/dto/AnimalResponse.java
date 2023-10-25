package fact.it.animalservice.dto;

import fact.it.animalservice.model.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date birthDate;
    private FoodResponse preferredFood;
}
