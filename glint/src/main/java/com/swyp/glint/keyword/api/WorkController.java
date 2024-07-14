package com.swyp.glint.keyword.api;

import com.swyp.glint.keyword.application.WorkService;
import com.swyp.glint.keyword.domain.Work;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{workId}")
    @Operation(summary = "Get a work by its ID")
    public ResponseEntity<Work> getWorkById(@PathVariable Long workId) {
        Work work = workService.findById(workId);
        return ResponseEntity.ok(work);
    }

    @GetMapping("/by-name")
    @Operation(summary = "Get a Work by workName", description = "직업명을 통한 직업 조회")
    public ResponseEntity<Work> getWorkByName(
            @RequestParam String workName) {
        Work work = workService.findByName(workName);
        return ResponseEntity.ok(work);
    }

    @GetMapping
    @Operation(summary = "List all works", description = "모든 직업 조회")
    public ResponseEntity<List<Work>> getAllWorks() {
        List<Work> works = workService.getAllWork();
        return ResponseEntity.ok(works);
    }

    /* //정상 동작함
    @PostMapping
    @Operation(summary = "Create a new work", description = "새로운 직업 생성")
    public ResponseEntity<Work> createWork(@RequestParam String workName) {
        Work newWork = workService.createNewWork(workName);
        return new ResponseEntity<>(newWork, HttpStatus.CREATED);
    }
    */
    /*
    @PutMapping("/{workId}")
    @Operation(summary = "Update an existing work")
    public ResponseEntity<Work> updateWork(@PathVariable Long workId, @RequestParam String workName) {
        workService.updateWork(workId, workName);
        Work updatedWork = workService.findById(workId); // Fetch to confirm update
        return ResponseEntity.ok(updatedWork);
    }
    */

}

