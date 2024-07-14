package com.swyp.glint.keyword.api;

import com.swyp.glint.keyword.application.UniversityService;
import com.swyp.glint.keyword.domain.University;
import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.common.exception.NotFoundEntityException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/universities")
@Tag(name = "university_controller", description = "Manage university related operations")
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    @Operation(summary = "List all universities", description = "모든 대학 조회")
    public ResponseEntity<List<University>> getAllUniversity() {
        return ResponseEntity.ok(universityService.getAllUniversity());
    }

    @GetMapping("/{universityId}")
    @Operation(summary = "Get a university by its ID", description = "University Id를 통학 대학 조회")
    public ResponseEntity<University> getUniversityById(@PathVariable Long universityId) {
        University university = universityService.findById(universityId);
        return ResponseEntity.ok(university);
    }

    @GetMapping("/by-name")
    @Operation(summary = "Get a university by its Name", description = "대학명을 통학 대학 조회")
    public ResponseEntity<University> getUniversityByName(@RequestParam String universityName) {
        University university = universityService.findByName(universityName);
        return ResponseEntity.ok(university);
    }

    @GetMapping("/{universityId}/category")
    @Operation(summary = "Get a university category by its university Id", description = "University Id를 통학 대학 카테고리 조회")
    public ResponseEntity<UniversityCategory> getUniversityCategoryById(@PathVariable Long universityId) {
        UniversityCategory category = universityService.getUniversityCategoryByUniversityId(universityId);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/category")
    @Operation(summary = "List all university categories", description = "모든 대학 카테고리 조회")
    public ResponseEntity<List<UniversityCategory>> getAllUniversityCategories() {
        return ResponseEntity.ok(universityService.getAllUniversityCategory());
    }

}