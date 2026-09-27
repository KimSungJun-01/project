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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    @Transactional
    public TaskResponse createTask(TaskCreateRequest request) {
        return TaskResponse.from(taskRepository.save(new Task(request.title(), request.description())));
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(int id) {
        return TaskResponse.from(findTask(id));
    }

    @Transactional
    public TaskResponse updateTask(int id, TaskUpdateRequest request) {
        Task task = findTask(id);
        task.update(request.title(), request.description(), request.complete());
        return TaskResponse.from(task);
    }

    @Transactional
    public TaskResponse changeComplete(int id, boolean complete) {
        Task task = findTask(id);
        task.changeComplete(complete);
        return TaskResponse.from(task);
    }

    @Transactional
    public void deleteTask(int id) {
        taskRepository.delete(findTask(id));
    }

    private Task findTask(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new DomainException(
                        "TASK_NOT_FOUND",
                        "존재하지 않는 할 일입니다."
                ));
    }
}
