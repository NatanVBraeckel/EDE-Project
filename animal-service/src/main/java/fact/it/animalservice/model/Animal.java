package fact.it.animalservice.model;

import jakarta.persistence.*;

import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String animalCode;
    private String name;
    private AnimalType type;
    private Date birthDate;
    private String preferredFood;
}