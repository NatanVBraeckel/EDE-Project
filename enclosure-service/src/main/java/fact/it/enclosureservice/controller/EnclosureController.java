package fact.it.enclosureservice.controller;

import fact.it.enclosureservice.dto.EnclosureRequest;
import fact.it.enclosureservice.dto.EnclosureResponse;
import fact.it.enclosureservice.model.Enclosure;
import fact.it.enclosureservice.service.EnclosureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enclosure")
@RequiredArgsConstructor
public class EnclosureController {

    private final EnclosureService enclosureService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createEnclosure(@RequestBody EnclosureRequest enclosureRequest) {
        enclosureService.createEnclosure(enclosureRequest);
    }

    @GetMapping("/{enclosureId}")
    @ResponseStatus(HttpStatus.OK)
    public List<EnclosureResponse> getEnclosureById(@PathVariable List<String> enclosureId) {
        return enclosureService.getEnclosureById(enclosureId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EnclosureResponse> getAllEnclosures() {
        return enclosureService.getAllEnclosures();
    }

    @DeleteMapping("/{enclosureId}")
    public ResponseEntity<?> deleteEnclosure(@PathVariable String enclosureId) {
        if(enclosureService.deleteEnclosure(enclosureId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Enclosure not found with id: " + enclosureId);
        }
    }
}
