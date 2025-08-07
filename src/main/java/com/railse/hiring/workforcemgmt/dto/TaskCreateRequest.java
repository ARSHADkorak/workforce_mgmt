package com.railse.hiring.workforcemgmt.dto; 
 
import com.fasterxml.jackson.databind.PropertyNamingStrategies; 
import com.fasterxml.jackson.databind.annotation.JsonNaming; 
import com.railse.hiring.workforcemgmt.common.model.enums.ReferenceType; 
import com.railse.hiring.workforcemgmt.model.enums.Priority; 
import com.railse.hiring.workforcemgmt.model.enums.Task; 
import lombok.Data; 
 
import java.util.List; 
 
@Data 
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) 
public class TaskCreateRequest { 
   private List<RequestItem> requests; 
 
   public List<RequestItem> getRequests() {
	return requests;
}

   public void setRequests(List<RequestItem> requests) {
	this.requests = requests;
   }

   @Data 
   @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) 
   public static class RequestItem { 
       private Long referenceId; 
       private ReferenceType referenceType; 
       private Task task; 
       private Long assigneeId; 
       private Priority priority; 
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
	   public Long getAssigneeId() {
		   return assigneeId;
	   }
	   public void setAssigneeId(Long assigneeId) {
		   this.assigneeId = assigneeId;
	   }
	   public Priority getPriority() {
		   return priority;
	   }
	   public void setPriority(Priority priority) {
		   this.priority = priority;
	   }
	   public Long getTaskDeadlineTime() {
		   return taskDeadlineTime;
	   }
	   public void setTaskDeadlineTime(Long taskDeadlineTime) {
		   this.taskDeadlineTime = taskDeadlineTime;
	   }
	   private Long taskDeadlineTime; 
   } 
}