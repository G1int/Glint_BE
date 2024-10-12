package com.swyp.glint.keyword.api;

import com.swyp.glint.keyword.application.SmokingService;
import com.swyp.glint.keyword.domain.Smoking;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/smokings")
@Tag(name = "smoking_controller", description = "Manage smoking related operations")
public class SmokingController {

    private final SmokingService smokingService;

    public SmokingController(SmokingService smokingService) {
        this.smokingService = smokingService;
    }

    @GetMapping
    @Operation(summary = "List all smokings", description = "모든 흡연 조회")
    public ResponseEntity<List<Smoking>> getAllSmoking() {
        return ResponseEntity.ok(smokingService.getAllSmoking());
    }

    @GetMapping("/smoking") @Operation(summary = "Get a smoking by its name", description = "흡연명을 통한 흡연 조회")
    public ResponseEntity<Smoking> getSmokingByName(@RequestParam String smokingName) {
        Smoking smoking = smokingService.findByName(smokingName);
        return ResponseEntity.ok(smoking);
    }

    @GetMapping("/{smokingId}/smoking")
    @Operation(summary = "Get a smoking by its ID", description = "Smoking Id를 통한 흡연 조회")
    public ResponseEntity<Smoking> getSmokingById(@PathVariable Long smokingId) {
        Smoking smoking = smokingService.getSmokingById(smokingId);
        return ResponseEntity.ok(smoking);
    }

    /* // 생성, 수정, 조회 주석 처리

    @PostMapping(path = "/{smokingId}/smoking", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new smoking", description = "새로운 흡연 생성")
    public ResponseEntity<Smoking> createSmoking(@RequestParam String smokingName) {
        Smoking smoking = smokingService.createNewSmoking(smokingName);
        return new ResponseEntity<>(smoking, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{smokingId}/smoking", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an existing smoking by its smoking ID", description = "smoking id를 통한 흡연 수정")
    public ResponseEntity<Smoking> updateSmoking(@PathVariable Long smokingId, @RequestParam String smokingName) {
        Smoking smoking = smokingService.updateSmokingById(smokingId, smokingName);
        return ResponseEntity.ok(smoking);
    }

    @DeleteMapping(path = "/{smokingId}/smoking")
    @Operation(summary = "Delete a smoking by its smoking ID", description = "smoking id를 통한 흡연 삭제")
    public ResponseEntity<Void> deleteSmoking(@PathVariable Long smokingId) {
        smokingService.deleteSmoking(smokingId);
        return ResponseEntity.noContent().build();
    }

     */

}
