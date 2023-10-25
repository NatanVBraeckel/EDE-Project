package fact.it.enclosureservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodResponse {
    private Long id;
    private String foodCode;
    private String name;
    private String category;
    private String servingSize;
    private String origin;
    private int stock;
}