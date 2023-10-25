package fact.it.enclosureservice.model;

import fact.it.enclosureservice.dto.AnimalResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value= "enclosure")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Enclosure {

    private String id;
    private String enclosureCode;
    private String name;
    private EnclosureSize size;
    private EnclosureType type;
    private List<String> animals;

}
