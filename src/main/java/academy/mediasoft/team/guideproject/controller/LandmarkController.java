package academy.mediasoft.team.guideproject.controller;

import academy.mediasoft.team.guideproject.dto.LandmarkDto;
import academy.mediasoft.team.guideproject.service.LandmarkService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/landmarks")
@AllArgsConstructor
public class LandmarkController {

    private final LandmarkService landmarkService;

    @GetMapping
    public List<LandmarkDto> getAllLandmarks() {
        return landmarkService.getAllLandmarks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LandmarkDto> getLandmarkById(@PathVariable Long id) {

        LandmarkDto landmarkDto = landmarkService.getLandmarkById(id);

        return ResponseEntity.status(HttpStatus.OK).body(landmarkDto);
    }

    @PostMapping
    public ResponseEntity<LandmarkDto> addLandmark(@RequestBody LandmarkDto landmarkDto) {

        LandmarkDto createdLandmark = landmarkService.addLandmark(landmarkDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdLandmark);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LandmarkDto> updateLandmark(@PathVariable Long id,
                               @RequestBody LandmarkDto landmarkDto) {

        LandmarkDto updatedLandmark = landmarkService.updateLandmark(id, landmarkDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedLandmark);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLandmark(@PathVariable Long id) {

        landmarkService.deleteLandmark(id);

        return ResponseEntity.noContent().build();
    }
}

