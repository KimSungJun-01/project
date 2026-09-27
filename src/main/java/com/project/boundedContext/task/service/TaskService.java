package com.project.boundedContext.task.service;

import com.project.boundedContext.task.entity.Task;
import com.project.boundedContext.task.exception.DomainException;
import com.project.boundedContext.task.repository.TaskRepository;
import com.project.shared.task.dto.TaskCreateRequest;
import com.project.shared.task.dto.TaskResponse;
import com.project.shared.task.dto.TaskUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    @Transactional
    public TaskResponse createTask(TaskCreateRequest request) {
        Task task = new Task(request.getTitle(), request.getDescription());
        Task savedTask = taskRepository.save(task);
        return TaskResponse.from(savedTask);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(int id) {
        Task task = taskRepository.findTaskById(id)
                .orElseThrow(() -> new DomainException("404-1", "존재하지 않는 할 일입니다."));
        return TaskResponse.from(task);
    }

    @Transactional
    public TaskResponse updateTask(int id, TaskUpdateRequest request) {
        Task task = taskRepository.findTaskById(id)
                .orElseThrow(() -> new DomainException("404-1", "존재하지 않는 할 일입니다."));
        task.update(request.getTitle(), request.getDescription(), request.getComplete());
        return TaskResponse.from(task);
    }

    @Transactional
    public TaskResponse changeComplete(int id, boolean complete) {
        Task task = taskRepository.findTaskById(id)
                .orElseThrow(() -> new DomainException("404-1", "존재하지 않는 할 일입니다."));
        task.changeComplete(complete);
        return TaskResponse.from(task);
    }

    @Transactional
    public TaskResponse deleteTask(int id) {
        Task task = taskRepository.findTaskById(id)
                .orElseThrow(() -> new DomainException("404-1", "존재하지 않는 할 일입니다."));
        taskRepository.delete(task);
        return TaskResponse.from(task);
    }
}
