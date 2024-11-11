package com.swyp.glint.keyword.presentation;

import com.swyp.glint.keyword.application.ReligionService;
import com.swyp.glint.keyword.domain.Religion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/religions")
@Tag(name = "religion_controller", description = "Manage religion related operations")
public class ReligionController {

    private final ReligionService religionService;

    public ReligionController(ReligionService religionService) {
        this.religionService = religionService;
    }

    @GetMapping
    @Operation(summary = "List all religions", description = "모든 종교 조회")
    public ResponseEntity<List<Religion>> getAllReligions() {
        return ResponseEntity.ok(religionService.getAllReligion());
    }

    @GetMapping("/religion")
    @Operation(summary = "Get a religion by its name", description = "종교명을 통한 종교 조회")
    public ResponseEntity<Religion> getReligionByName(@RequestParam String religionName) {
        Religion religion = religionService.findByName(religionName);
        return ResponseEntity.ok(religion);
    }

    @GetMapping("/{religionId}/religion")
    @Operation(summary = "Get a religion by its ID", description = "Religion Id를 통한 종교 조회")
    public ResponseEntity<Religion> getReligionById(@PathVariable Long religionId) {
        Religion religion = religionService.getById(religionId);
        return ResponseEntity.ok(religion);
    }

    /* // 생성, 수정, 조회 주석 처리

    @PostMapping(path = "/{religionId}/religion", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new Religion", description = "새로운 종교 생성")
    public ResponseEntity<Religion> createReligion(@RequestParam String religionName) {
        Religion religion = religionService.createNewReligion(religionName);
        return new ResponseEntity<>(religion, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{religionId}/religion", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an existing religion by its religion ID", description = "religion id를 통한 종교 수정")
    public ResponseEntity<Religion> updateReligion(@PathVariable Long religionId, @RequestParam String religionName) {
        Religion religion = religionService.updateReligionById(religionId, religionName);
        return ResponseEntity.ok(religion);
    }

    @DeleteMapping(path = "/{religionId}/religion")
    @Operation(summary = "Delete a religion by its religion ID", description = "religion id를 통한 종교 삭제")
    public ResponseEntity<Void> deleteReligion(@PathVariable Long religionId) {
        religionService.deleteReligion(religionId);
        return ResponseEntity.noContent().build();
    }

     */

}
