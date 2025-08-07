package com.railse.hiring.workforcemgmt.model;

import lombok.Data;
import java.util.Date;

@Data
public class TaskActivity {
    private Long taskId;
    private String description;
    private Date timestamp = new Date();
}
