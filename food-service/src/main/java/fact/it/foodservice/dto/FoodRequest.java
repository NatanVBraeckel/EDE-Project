package fact.it.foodservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequest {

    private String name;
    private String foodCode;
    private String category;
    private String servingSize;
    private String origin;
    private int stock;

}
