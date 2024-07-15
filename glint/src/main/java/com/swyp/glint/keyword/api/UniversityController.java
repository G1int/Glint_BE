package com.swyp.glint.keyword.api;

import com.swyp.glint.keyword.application.UniversityService;
import com.swyp.glint.keyword.domain.University;
import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.Work;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/university")
    @Operation(summary = "Get a university by its Name", description = "대학명을 통한 대학 조회")
    public ResponseEntity<List<University>> getUniversityByName(@RequestParam String universityName) {
        List<University> universities = universityService.getUniversitiesByName(universityName);
        return ResponseEntity.ok(universities);
    }

    @GetMapping("/{universityId}/university")
    @Operation(summary = "Get a university by its ID", description = "University Id를 통한 대학 조회")
    public ResponseEntity<University> getUniversityById(@PathVariable Long universityId) {
        University university = universityService.findById(universityId);
        return ResponseEntity.ok(university);
    }

    @GetMapping("/categories")
    @Operation(summary = "List all university categories", description = "모든 대학 카테고리 조회")
    public ResponseEntity<List<UniversityCategory>> getAllUniversityCategories() {
        return ResponseEntity.ok(universityService.getAllUniversityCategory());
    }

    @GetMapping("/category")
    @Operation(summary = "Get a university category by its university name", description = "대학명을 통한 카테고리 조회")
    public ResponseEntity<Map<String, Object>> getUniversityCategoryByUniversityName(@RequestParam String universityName) {
        List<University> universities = universityService.getUniversitiesByName(universityName);
        University university = universities.stream().findFirst().orElseThrow(() -> new NotFoundEntityException("University not found with university name: " + universityName));
        UniversityCategory category = universityService.getUniversityCategoryByUniversityName(universityName);
        Map<String, Object> response = new HashMap<>();
        response.put("University", university);
        response.put("Category", category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{universityId}/category")
    @Operation(summary = "Get a university category by its university Id", description = "University Id를 통학 대학 카테고리 조회")
    public ResponseEntity<Map<String, Object>> getUniversityCategoryById(@PathVariable Long universityId) {
        University university = universityService.findById(universityId);
        UniversityCategory category = universityService.getUniversityCategoryByUniversityId(universityId);
        Map<String, Object> response = new HashMap<>();
        response.put("University", university);
        response.put("Category", category);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/{universityId}/university", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new university", description = "새로운 대학 생성")
    public ResponseEntity<University> createUniversity(@RequestParam String universityName, @RequestParam String universityDepartment) {
        University university = universityService.createNewUniversity(universityName, universityDepartment);
        return new ResponseEntity<>(university, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{universityId}/university", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an existing university by its university ID", description = "university id를 통한 대학 수정")
    public ResponseEntity<University> updateUniversity(@PathVariable Long universityId, @RequestParam String universityName, @RequestParam String universityDepartment) {
        University university = universityService.updateUniversityById(universityId, universityName, universityDepartment);
        return ResponseEntity.ok(university);
    }

    @DeleteMapping(path = "/{universityId}/university")
    @Operation(summary = "Delete a work by its university ID", description = "university id를 통한 대학 삭제")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long universityId) {
        universityService.deleteUniversity(universityId);
        return ResponseEntity.noContent().build();
    }



}