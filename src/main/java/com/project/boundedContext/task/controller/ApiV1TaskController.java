package com.project.boundedContext.task.controller;

import com.project.boundedContext.task.service.TaskService;
import com.project.shared.task.dto.TaskCreateRequest;
import com.project.shared.task.dto.TaskResponse;
import com.project.shared.task.dto.TaskUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class ApiV1TaskController {
    private final TaskService taskService;

    // 할 일 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(
            @Valid @RequestBody TaskCreateRequest request) {
        return taskService.createTask(request);
    }

    // 할 일 전체 조회
    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    // 할 일 단건 조회
    @GetMapping("/{id}")
    public TaskResponse getTaskById(
            @PathVariable int id) {
        return taskService.getTaskById(id);
    }

    // 할 일 수정
    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable int id,
            @Valid @RequestBody TaskUpdateRequest request) {
        return taskService.updateTask(id, request);
    }

    // 완료 여부 변경
    @PatchMapping("/{id}/complete")
    public TaskResponse changeComplete(
            @PathVariable int id,
            @RequestParam boolean complete) {
        return taskService.changeComplete(id, complete);
    }

    // 할 일 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable int id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
