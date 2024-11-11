package com.swyp.glint.keyword.presentation;

import com.swyp.glint.keyword.application.WorkCategoryService;
import com.swyp.glint.keyword.application.WorkService;
import com.swyp.glint.keyword.application.dto.WorkCategoryListResponse;
import com.swyp.glint.keyword.application.dto.WorkListResponse;
import com.swyp.glint.user.application.dto.WorkResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/works")
@Tag(name = "work_controller", description = "Manage works related operations")
public class WorkController {

    private final WorkService workService;
    private final WorkCategoryService workCategoryService;

    public WorkController(WorkService workService, WorkCategoryService workCategoryService) {
        this.workService = workService;
        this.workCategoryService = workCategoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all works", description = "모든 직업 조회")
    public ResponseEntity<WorkListResponse> getAllWorks() {
        return ResponseEntity.ok(workService.getAllWork());
    }

    @GetMapping(path = "/work", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a Work by workName", description = "직업명을 통한 직업 조회")
    public ResponseEntity<WorkResponse> getWorkByName(
            @RequestParam String workName) {
        return ResponseEntity.ok(workService.findByName(workName));
    }

    @GetMapping(path = "/{workId}/work", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a work by its ID", description = "work id를 통한 통한 직업 조회")
    public ResponseEntity<WorkResponse> getWorkById(@PathVariable Long workId) {
        return ResponseEntity.ok(workService.findById(workId));
    }

    @GetMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all work categories", description = "모든 직업 카테고리 조회")
    public ResponseEntity<WorkCategoryListResponse> getAllWorkCategories() {
        return ResponseEntity.ok(workCategoryService.getAllWorkCategory());
    }

    @PostMapping("/map-categories")
    @Operation(summary = "Map all existing works to categories", description = "초기 매핑 작업 : 모든 기존 Work 엔티티를 workCategoryId로 매핑하는 초기 작업을 수행")
    public ResponseEntity<Void> mapAllWorksToCategories() {
        workService.mapAllWorksToCategories();
        return ResponseEntity.ok().build();
    }


    @PostMapping(path = "/{workId}/work", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new work", description = "새로운 직업 생성")
    public ResponseEntity<WorkResponse> createWork(@RequestParam String workName) {
        return new ResponseEntity<>(workService.createNewWorkReturnDTO(workName), HttpStatus.CREATED);
    }

  /* // 생성, 수정, 조회 주석 처리

    @PutMapping(path = "/{workId}/work", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an existing work by its work ID", description = "work id를 통한 직업 수정")
    public ResponseEntity<Work> updateWork(@PathVariable Long workId, @RequestParam String workName) {
        Work work = workService.updateWorkById(workId, workName);
        return ResponseEntity.ok(work);
    }

    @DeleteMapping(path = "/{workId}/work")
    @Operation(summary = "Delete a work by its work ID", description = "work id를 통한 직업 삭제")
    public ResponseEntity<Void> deleteWork(@PathVariable Long workId) {
        workService.deleteWork(workId);
        return ResponseEntity.noContent().build();
    }

     */

}

