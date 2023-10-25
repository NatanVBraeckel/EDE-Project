package fact.it.enclosureservice.dto;

import fact.it.enclosureservice.model.EnclosureSize;
import fact.it.enclosureservice.model.EnclosureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnclosureResponse {

    private String id;
    private String enclosureCode;
    private String name;
    private EnclosureSize size;
    private EnclosureType type;
    private List<AnimalResponse> animals;
}
