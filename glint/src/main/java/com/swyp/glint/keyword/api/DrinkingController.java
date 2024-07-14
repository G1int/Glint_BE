package com.swyp.glint.keyword.api;

import com.swyp.glint.keyword.application.DrinkingService;
import com.swyp.glint.keyword.domain.Drinking;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drinkings")
@Tag(name = "drinking_controller", description = "Manage drinking related operations")
public class DrinkingController {

    private final DrinkingService drinkingService;

    public DrinkingController(DrinkingService drinkingService) {
        this.drinkingService = drinkingService;
    }

    @GetMapping("/{drinkingId}")
    @Operation(summary = "Get a drinking by its ID", description = "Drinking Id를 통한 음주 조회")
    public ResponseEntity<Drinking> getDrinkingById(@PathVariable Long drinkingId) {
        Drinking drinking = drinkingService.findById(drinkingId);
        return ResponseEntity.ok(drinking);
    }

    @GetMapping
    @Operation(summary = "List all drinking", description = "모든 음주 조회")
    public ResponseEntity<List<Drinking>> getAllDrinking() {
        List<Drinking> drinkings = drinkingService.getAllDrinking();
        return ResponseEntity.ok(drinkings);
    }

    @GetMapping("/by-name")
    @Operation(summary = "Get a drinking by its name", description = "음주 상태명을 통한 음주 조회")
    public ResponseEntity<Drinking> getDrinkingByName(@RequestParam String drinkingName) {
        Drinking drinking = drinkingService.findByName(drinkingName);
        return ResponseEntity.ok(drinking);
    }
}
