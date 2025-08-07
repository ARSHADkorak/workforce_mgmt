package com.railse.hiring.workforcemgmt.model;

import com.railse.hiring.workforcemgmt.common.model.enums.ReferenceType;
import com.railse.hiring.workforcemgmt.model.enums.Priority; 
import com.railse.hiring.workforcemgmt.model.enums.Task; 
import com.railse.hiring.workforcemgmt.model.enums.TaskStatus; 
import lombok.Data;

@Data 
public class TaskManagement { 
   private Long id; 
   private Long referenceId; 
   private ReferenceType referenceType; 
   private Task task; 
   private String description; 
   private TaskStatus status; 
   private Long assigneeId; 
   private Long taskDeadlineTime;
   private Priority priority;
   public Long getId() {
	return id;
   }
   public void setId(Long id) {
	this.id = id;
   }
   public Long getReferenceId() {
	return referenceId;
   }
   public void setReferenceId(Long referenceId) {
	this.referenceId = referenceId;
   }
   public ReferenceType getReferenceType() {
	return referenceType;
   }
   public void setReferenceType(ReferenceType referenceType) {
	this.referenceType = referenceType;
   }
   public Task getTask() {
	return task;
   }
   public void setTask(Task task) {
	this.task = task;
   }
   public String getDescription() {
	return description;
   }
   public void setDescription(String description) {
	this.description = description;
   }
   public TaskStatus getStatus() {
	return status;
   }
   public void setStatus(TaskStatus status) {
	this.status = status;
   }
   public Long getAssigneeId() {
	return assigneeId;
   }
   public void setAssigneeId(Long assigneeId) {
	this.assigneeId = assigneeId;
   }
   public Long getTaskDeadlineTime() {
	return taskDeadlineTime;
   }
   public void setTaskDeadlineTime(Long taskDeadlineTime) {
	this.taskDeadlineTime = taskDeadlineTime;
   }
   public Priority getPriority() {
	return priority;
   }
   public void setPriority(Priority priority) {
	this.priority = priority;
   } 
   
   
} 