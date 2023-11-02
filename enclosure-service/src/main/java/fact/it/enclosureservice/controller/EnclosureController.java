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
    public ResponseEntity<EnclosureResponse> createEnclosure(@RequestBody EnclosureRequest enclosureRequest) {
        EnclosureResponse enclosureResponse = enclosureService.createEnclosure(enclosureRequest);
        return new ResponseEntity<>(enclosureResponse, HttpStatus.OK);
    }

    @GetMapping("/byId/{enclosureId}")
    @ResponseStatus(HttpStatus.OK)
    public List<EnclosureResponse> getEnclosureById(@PathVariable List<String> enclosureId) {
        return enclosureService.getEnclosureById(enclosureId);
    }

    @GetMapping("/{enclosureCode}")
    @ResponseStatus(HttpStatus.OK)
    public List<EnclosureResponse> getEnclosureByEnclosureCode(@PathVariable List<String> enclosureCode) {
        return enclosureService.getEnclosureByEnclosureCode(enclosureCode);
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
