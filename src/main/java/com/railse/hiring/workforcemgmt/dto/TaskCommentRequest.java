package com.railse.hiring.workforcemgmt.dto;

import lombok.Data;

@Data
public class TaskCommentRequest {
    private Long taskId;
    private String comment;
    private String author;
}
