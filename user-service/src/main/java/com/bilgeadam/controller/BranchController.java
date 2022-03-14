package com.bilgeadam.controller;

import com.bilgeadam.dto.request.BranchRequestDto;
import com.bilgeadam.dto.request.CourseRequestDto;
import com.bilgeadam.dto.response.BranchResponseDto;
import com.bilgeadam.dto.response.CourseDetailResponseDto;
import com.bilgeadam.dto.response.CourseResponseDto;
import com.bilgeadam.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/branch")
public class BranchController {
    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveBranch(@RequestBody BranchRequestDto dto) {

        branchService.save(dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/listall")
    public ResponseEntity<List<BranchResponseDto>> listAllStudent() {
        return ResponseEntity.ok(branchService.listAllCourse());
    }
    @GetMapping("/findbranchbyid")
    public ResponseEntity<BranchResponseDto> findBranchById(@RequestParam long id) {
        return ResponseEntity.ok(branchService.getBranchDetailById(id));
    }
    @PostMapping("/update")
    public ResponseEntity<Boolean> updateBranch(@RequestBody BranchResponseDto dto) {
        return ResponseEntity.ok(branchService.updateBranch(dto));
    }
}
