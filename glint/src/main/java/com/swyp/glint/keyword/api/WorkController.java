package com.swyp.glint.keyword.api;

import com.swyp.glint.keyword.application.WorkService;
import com.swyp.glint.keyword.domain.Work;
import com.swyp.glint.keyword.domain.WorkCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/works")
@Tag(name = "work_controller", description = "Manage works related operations")
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all works", description = "모든 직업 조회")
    public ResponseEntity<List<Work>> getAllWorks() {
        List<Work> works = workService.getAllWork();
        return ResponseEntity.ok(works);
    }

    @GetMapping(path = "/work", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a Work by workName", description = "직업명을 통한 직업 조회")
    public ResponseEntity<Work> getWorkByName(
            @RequestParam String workName) {
        Work work = workService.findByName(workName);
        return ResponseEntity.ok(work);
    }

    @GetMapping(path = "/{workId}/work", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a work by its ID", description = "work id를 통한 통한 직업 조회")
    public ResponseEntity<Work> getWorkById(@PathVariable Long workId) {
        Work work = workService.findById(workId);
        return ResponseEntity.ok(work);
    }

    @PostMapping(path = "/{workId}/work", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new work", description = "새로운 직업 생성")
    public ResponseEntity<Work> createWork(@RequestParam String workName) {
        Work work = workService.createNewWork(workName);
        return new ResponseEntity<>(work, HttpStatus.CREATED);
    }

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

    @GetMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all work categories", description = "모든 직업 카테고리 조회")
    public ResponseEntity<List<WorkCategory>> getAllWorkCategories() {
        return ResponseEntity.ok(workService.getAllWorkCategory());
    }

}

