package com.railse.hiring.workforcemgmt.model;

import lombok.Data;
import java.util.Date;

@Data
public class TaskComment {
    private Long taskId;
    private String comment;
    private String author;
    private Date timestamp = new Date();
}
