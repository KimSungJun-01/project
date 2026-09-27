package com.project.shared.task.dto;

import com.project.boundedContext.task.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskResponse {
    private int id;
    private String title;
    private String description;
    private boolean complete;
    private String createDate;
    private String modifyDate;

    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isComplete(),
                task.getCreateDate().toString(),
                task.getModifyDate().toString()
        );
    }
}
