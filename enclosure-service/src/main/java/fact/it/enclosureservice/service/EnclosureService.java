package fact.it.enclosureservice.service;

import fact.it.enclosureservice.dto.AnimalResponse;
import fact.it.enclosureservice.dto.EnclosureRequest;
import fact.it.enclosureservice.dto.EnclosureResponse;
import fact.it.enclosureservice.model.Enclosure;
import fact.it.enclosureservice.repository.EnclosureRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnclosureService {

    private final EnclosureRepository enclosureRepository;
    private final WebClient webClient;

    @Value("${animalservice.baseurl}")
    private String animalServiceBaseUrl;

    public void createEnclosure(EnclosureRequest enclosureRequest) {
        Enclosure enclosure = Enclosure.builder()
                .enclosureCode(enclosureRequest.getEnclosureCode())
                .name(enclosureRequest.getName())
                .type(enclosureRequest.getType())
                .size(enclosureRequest.getSize())
                .animals(enclosureRequest.getAnimals())
                .build();

        enclosureRepository.save(enclosure);
    }

    public List<EnclosureResponse> getAllEnclosures() {
        List<Enclosure> enclosures = enclosureRepository.findAll();

        return enclosures.stream().map(this::mapToEnclosureResponse).toList();
    }

    public List<EnclosureResponse> getEnclosureById(List<String> id) {
        List<Enclosure> enclosures = enclosureRepository.findAllById(id);

        return enclosures.stream().map(this::mapToEnclosureResponse).toList();
    }

    public boolean deleteEnclosure(String enclosureId) {
        Optional<Enclosure> enclosureOptional = enclosureRepository.findById(enclosureId);
        if(enclosureOptional.isPresent()) {
            Enclosure enclosure = enclosureOptional.get();
            enclosureRepository.delete(enclosure);
            return true;
        } else {
            return false;
        }
    }

    private EnclosureResponse mapToEnclosureResponse(Enclosure enclosure) {
        String animalCodesQueryParam = String.join(",", enclosure.getAnimals());

//        AnimalResponse[] animalResponseArray = webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("http://localhost:8082/api/animal")
//                        .queryParam("animalId", animalIdsQueryParam)
//                        .build())
//                .retrieve()
//                .bodyToMono(AnimalResponse[].class)
//                .block();

        AnimalResponse[] animalResponseArray = webClient.get()
                .uri("http://" + animalServiceBaseUrl + "/api/animal/" + animalCodesQueryParam)
                .retrieve()
                .bodyToMono(AnimalResponse[].class)
                .block();

        return EnclosureResponse.builder()
                .id(enclosure.getId())
                .enclosureCode(enclosure.getEnclosureCode())
                .name(enclosure.getName())
                .type(enclosure.getType())
                .size(enclosure.getSize())
                .animals(Arrays.asList(animalResponseArray))
                .build();
    }
}
