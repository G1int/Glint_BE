package com.swyp.glint.keyword.api;

import com.swyp.glint.keyword.application.UniversityMappingService;
import com.swyp.glint.keyword.application.UniversityService;
import com.swyp.glint.keyword.application.dto.UniversityListResponse;
import com.swyp.glint.keyword.domain.University;
import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.Work;
import com.swyp.glint.user.application.dto.UniversityResponse;
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

    public UniversityController(UniversityService universityService, UniversityMappingService universityMappingService) {
        this.universityService = universityService;
    }

    @GetMapping("/{universityId}/university")
    @Operation(summary = "Get a university by its ID", description = "University Id를 통한 대학 조회")
    public ResponseEntity<UniversityResponse> getUniversityById(@PathVariable Long universityId) {
        return ResponseEntity.ok(universityService.findById(universityId));
    }

    @GetMapping("/")
    @Operation(summary = "Get a university List by university name", description = "대학명을 통한 대학 리스트 조회")
    public ResponseEntity<UniversityListResponse> getUniversityByName(@RequestParam String universityName) {
        return ResponseEntity.ok(universityService.getUniversitiesByName(universityName));
    }

    @GetMapping("/university")
    @Operation(summary = "Get a university List by university name and university department name", description = "대학명과 학과명을 통한 대학 조회")
    public ResponseEntity<UniversityResponse> getUniversityByName(@RequestParam String universityName, @RequestParam String universityDepartment) {
        return ResponseEntity.ok(universityService.findByName(universityName, universityDepartment));
    }

    @GetMapping("/categories")
    @Operation(summary = "List all university categories", description = "모든 대학 카테고리 조회")
    public ResponseEntity<List<UniversityCategory>> getAllUniversityCategories() {
        return ResponseEntity.ok(universityService.getAllUniversityCategory());
    }

     // 생성, 수정, 조회 주석 처리
/*
    @PostMapping(path = "/{universityId}/university", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new university", description = "새로운 대학 생성")
    public ResponseEntity<UniversityResponse> createUniversity(@RequestParam String universityName, @RequestParam String universityDepartment) {
        return new ResponseEntity<>(universityService.createNewUniversity(universityName, universityDepartment), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{universityId}/university", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an existing university by its university ID", description = "university id를 통한 대학 수정")
    public ResponseEntity<UniversityResponse> updateUniversity(@PathVariable Long universityId, @RequestParam String universityName, @RequestParam String universityDepartment) {
        return ResponseEntity.ok(universityService.updateUniversityById(universityId, universityName, universityDepartment));
    }

    @DeleteMapping(path = "/{universityId}/university")
    @Operation(summary = "Delete a work by its university ID", description = "university id를 통한 대학 삭제")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long universityId) {
        universityService.deleteUniversity(universityId);
        return ResponseEntity.noContent().build();
    }
 */

}