package com.project.shared.task.dto;

import com.project.boundedContext.task.entity.Task;

import java.time.LocalDateTime;

public record TaskResponse(
        int id,
        String title,
        String description,
        boolean complete,
        LocalDateTime createDate,
        LocalDateTime modifyDate
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isComplete(),
                task.getCreateDate(),
                task.getModifyDate()
        );
    }
}
