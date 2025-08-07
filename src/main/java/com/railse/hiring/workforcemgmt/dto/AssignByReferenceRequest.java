package com.railse.hiring.workforcemgmt.dto; 
 
import com.fasterxml.jackson.databind.PropertyNamingStrategies; 
import com.fasterxml.jackson.databind.annotation.JsonNaming; 
import com.railse.hiring.workforcemgmt.common.model.enums.ReferenceType; 
import lombok.Data; 
 
@Data 
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) 
public class AssignByReferenceRequest { 
   private Long referenceId; 
   private ReferenceType referenceType; 
   private Long assigneeId;
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
   public Long getAssigneeId() {
	return assigneeId;
   }
   public void setAssigneeId(Long assigneeId) {
	this.assigneeId = assigneeId;
   } 
}