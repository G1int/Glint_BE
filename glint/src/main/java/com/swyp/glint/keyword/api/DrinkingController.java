package com.swyp.glint.keyword.api;

import com.swyp.glint.keyword.application.DrinkingService;
import com.swyp.glint.keyword.domain.Drinking;
import com.swyp.glint.keyword.domain.Work;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all drinking", description = "모든 음주 조회")
    public ResponseEntity<List<Drinking>> getAllDrinking() {
        List<Drinking> drinkings = drinkingService.getAllDrinking();
        return ResponseEntity.ok(drinkings);
    }

    @GetMapping("/drinking")
    @Operation(summary = "Get a drinking by its name", description = "음주 상태명을 통한 음주 조회")
    public ResponseEntity<Drinking> getDrinkingByName(@RequestParam String drinkingName) {
        Drinking drinking = drinkingService.findByName(drinkingName);
        return ResponseEntity.ok(drinking);
    }

    @GetMapping("/{drinkingId}/drinking")
    @Operation(summary = "Get a drinking by its ID", description = "Drinking Id를 통한 음주 조회")
    public ResponseEntity<Drinking> getDrinkingById(@PathVariable Long drinkingId) {
        Drinking drinking = drinkingService.findById(drinkingId);
        return ResponseEntity.ok(drinking);
    }

    @PostMapping(path = "/{drinkingId}/drinking", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a Drinking", description = "새로운 음주 생성")
    public ResponseEntity<Drinking> createDrinking(@RequestParam String drinkingName) {
        Drinking drinking = drinkingService.createNewDrinking(drinkingName);
        return new ResponseEntity<>(drinking, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{drinkingId}/drinking", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an existing Drinking by its drinking ID", description = "drinking id를 통한 음주 수정")
    public ResponseEntity<Drinking> updateDrinking(@PathVariable Long drinkingId, @RequestParam String drinkingName) {
        Drinking drinking = drinkingService.updateDrinkingById(drinkingId, drinkingName);
        return ResponseEntity.ok(drinking);
    }

    @DeleteMapping(path = "/{drinkingId}/drinking")
    @Operation(summary = "Delete a Drinking by its drinking ID", description = "drinking id를 통한 음주 삭제")
    public ResponseEntity<Void> deleteDrinking(@PathVariable Long drinkingId) {
        drinkingService.deleteDrinking(drinkingId);
        return ResponseEntity.noContent().build();
    }

}
