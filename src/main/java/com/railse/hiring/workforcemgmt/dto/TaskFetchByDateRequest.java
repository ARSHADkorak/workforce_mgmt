package com.railse.hiring.workforcemgmt.dto; 
 
import com.fasterxml.jackson.databind.PropertyNamingStrategies; 
import com.fasterxml.jackson.databind.annotation.JsonNaming; 
import lombok.Data; 
 
import java.util.List; 
 
@Data 
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) 
public class TaskFetchByDateRequest { 
   private Long startDate; 
   private Long endDate; 
   private List<Long> assigneeIds;
   public Long getStartDate() {
	return startDate;
   }
   public void setStartDate(Long startDate) {
	this.startDate = startDate;
   }
   public Long getEndDate() {
	return endDate;
   }
   public void setEndDate(Long endDate) {
	this.endDate = endDate;
   }
   public List<Long> getAssigneeIds() {
	return assigneeIds;
   }
   public void setAssigneeIds(List<Long> assigneeIds) {
	this.assigneeIds = assigneeIds;
   } 
} 