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

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnclosureService {

    private final EnclosureRepository enclosureRepository;
    private final WebClient webClient;

    @Value("${animalservice.baseurl}")
    private String animalServiceBaseUrl;

    public EnclosureResponse createEnclosure(EnclosureRequest enclosureRequest) {
        Enclosure enclosure = Enclosure.builder()
                .enclosureCode(enclosureRequest.getEnclosureCode())
                .name(enclosureRequest.getName())
                .type(enclosureRequest.getType())
                .size(enclosureRequest.getSize())
                .animalCodes(enclosureRequest.getAnimalCodes())
                .build();

        enclosureRepository.save(enclosure);
        return mapToEnclosureResponse(enclosure);
    }

    public List<EnclosureResponse> getAllEnclosures() {
        List<Enclosure> enclosures = enclosureRepository.findAll();

        return enclosures.stream().map(this::mapToEnclosureResponse).toList();
    }

    public List<EnclosureResponse> getEnclosureById(List<String> id) {
        List<Enclosure> enclosures = enclosureRepository.findAllById(id);

        return enclosures.stream().map(this::mapToEnclosureResponse).toList();
    }

    public List<EnclosureResponse> getEnclosureByEnclosureCode(List<String> enclosureCode) {
        List<Enclosure> enclosures = enclosureRepository.findAllByEnclosureCodeIn(enclosureCode);

        return enclosures.stream().map(this::mapToEnclosureResponse).toList();
    }

    public EnclosureResponse updateEnclosure(String enclosureId, EnclosureRequest updateEnclosure) {
        Optional<Enclosure> enclosureOptional = enclosureRepository.findById(enclosureId);
        if(enclosureOptional.isPresent()) {
            Enclosure enclosure = enclosureOptional.get();
            enclosure.setEnclosureCode(updateEnclosure.getEnclosureCode());
            enclosure.setName(updateEnclosure.getName());
            enclosure.setType(updateEnclosure.getType());
            enclosure.setAnimalCodes(updateEnclosure.getAnimalCodes());
            enclosure.setSize(updateEnclosure.getSize());
            enclosureRepository.save(enclosure);
            return mapToEnclosureResponse(enclosure);
        }
        return null;
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
        List<String> animalCodes = enclosure.getAnimalCodes();

        //only request from the animal service if there are animal codes
        //also don't request if there is only one value in the array, being an empty string (would call all animal endpoint)
        if(animalCodes != null && !animalCodes.isEmpty() && !(animalCodes.size() == 1 && (animalCodes.get(0).equals("")))) {
            String animalCodesQueryParam = String.join(",", animalCodes);

            AnimalResponse[] animalResponseArray = webClient.get()
                    .uri("http://" + animalServiceBaseUrl + "/api/animal/" + animalCodesQueryParam)
                    .retrieve()
                    .bodyToMono(AnimalResponse[].class)
                    .block();

            // assign only when at least 1 animal found
            if (animalResponseArray != null && animalResponseArray.length > 0) {
                return EnclosureResponse.builder()
                        .id(enclosure.getId())
                        .enclosureCode(enclosure.getEnclosureCode())
                        .name(enclosure.getName())
                        .type(enclosure.getType())
                        .size(enclosure.getSize())
                        .animalCodes(enclosure.getAnimalCodes())
                        .animals(Arrays.asList(animalResponseArray))
                        .build();
            }
        }

        // no animalCodes or no animalReponses found
        return EnclosureResponse.builder()
                .id(enclosure.getId())
                .enclosureCode(enclosure.getEnclosureCode())
                .name(enclosure.getName())
                .type(enclosure.getType())
                .size(enclosure.getSize())
                .animalCodes(enclosure.getAnimalCodes())
                .animals(Collections.emptyList())
                .build();

    }
}
