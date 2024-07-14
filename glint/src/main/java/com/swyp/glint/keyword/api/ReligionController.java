package com.swyp.glint.keyword.api;

import com.swyp.glint.keyword.application.ReligionService;
import com.swyp.glint.keyword.domain.Religion;
import com.swyp.glint.common.exception.NotFoundEntityException;
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

    @GetMapping("/{religionId}")
    @Operation(summary = "Get a religion by its ID", description = "Religion Id를 통한 종교 조회")
    public ResponseEntity<Religion> getReligionById(@PathVariable Long religionId) {
        Religion religion = religionService.findById(religionId);
        return ResponseEntity.ok(religion);
    }

    @GetMapping("/by-name")
    @Operation(summary = "Get a religion by its name", description = "종교명을 통한 종교 조회")
    public ResponseEntity<Religion> getReligionByName(@RequestParam String religionName) {
        Religion religion = religionService.findByName(religionName);
        return ResponseEntity.ok(religion);
    }

}
