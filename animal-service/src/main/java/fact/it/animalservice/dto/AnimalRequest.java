package fact.it.animalservice.dto;

import fact.it.animalservice.model.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalRequest {

    private String name;
    private String animalCode;
    private AnimalType type;
    private LocalDate birthDate;
    private String codePreferredFood;
}
