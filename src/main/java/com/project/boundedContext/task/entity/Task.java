package com.project.boundedContext.task.entity;

import com.project.boundedContext.task.jpa.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TASK")
@Getter
@NoArgsConstructor
public class Task extends BaseIdAndTime {

    private static final int TITLE_MAX_LENGTH = 100;

    @Column(nullable = false, length = TITLE_MAX_LENGTH)
    private String title;

    private String description;

    @Column(nullable = false)
    private boolean complete;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.complete = false;
    }

    public void update(String title, String description, boolean complete) {
        this.title = title;
        this.description = description;
        this.complete = complete;
    }

    public void changeComplete(boolean complete) {
        this.complete = complete;
    }
}
