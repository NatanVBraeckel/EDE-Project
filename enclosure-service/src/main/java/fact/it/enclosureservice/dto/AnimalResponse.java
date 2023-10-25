package fact.it.enclosureservice.dto;

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
    private String type;
    private Date birthDate;
    private FoodResponse preferredFood;
}
