package com.swyp.glint.keyword.api;

import com.swyp.glint.keyword.application.ReligionService;
import com.swyp.glint.keyword.application.SmokingService;
import com.swyp.glint.keyword.domain.Religion;
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

    @GetMapping("/{smokingId}")
    @Operation(summary = "Get a smoking by its ID", description = "Smoking Id를 통한 흡연 조회")
    public ResponseEntity<Smoking> getSmokingById(@PathVariable Long smokingId) {
        Smoking smoking = smokingService.findById(smokingId);
        return ResponseEntity.ok(smoking);
    }

    @GetMapping("/by-name") @Operation(summary = "Get a smoking by its name", description = "흡연명을 통한 흡연 조회")
    public ResponseEntity<Smoking> getSmokingByName(@RequestParam String smokingName) {
        Smoking smoking = smokingService.findByName(smokingName);
        return ResponseEntity.ok(smoking);
    }
}
