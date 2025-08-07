package com.railse.hiring.workforcemgmt.common.model.response; 
 
import com.fasterxml.jackson.databind.PropertyNamingStrategies; 
import com.fasterxml.jackson.databind.annotation.JsonNaming; 
import com.railse.hiring.workforcemgmt.common.exception.StatusCode; 
import lombok.Data; 
import lombok.NoArgsConstructor; 
 
@Data 
@NoArgsConstructor 
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) 
public class Response<T> { 
   private T data; 
   private Pagination pagination; 
   private ResponseStatus status; 
 
   public T getData() {
	return data;
}

   public void setData(T data) {
	this.data = data;
   }

   public Pagination getPagination() {
	return pagination;
   }

   public void setPagination(Pagination pagination) {
	this.pagination = pagination;
   }

   public ResponseStatus getStatus() {
	return status;
   }

   public void setStatus(ResponseStatus status) {
	this.status = status;
   }

   public Response(T data, Pagination pagination, ResponseStatus status) { 
       this.data = data; 
       this.pagination = pagination; 
       this.status = status; 
   } 
 
   public Response(T data) { 
       this(data, null, new ResponseStatus(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage())); 
   } 
} 