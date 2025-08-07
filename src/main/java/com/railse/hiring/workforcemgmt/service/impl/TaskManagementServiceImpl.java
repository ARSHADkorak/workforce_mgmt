package com.railse.hiring.workforcemgmt.service.impl; 
 
import com.railse.hiring.workforcemgmt.common.exception.ResourceNotFoundException; 
import com.railse.hiring.workforcemgmt.dto.*; 
import com.railse.hiring.workforcemgmt.mapper.ITaskManagementMapper;
import com.railse.hiring.workforcemgmt.model.TaskActivity;
import com.railse.hiring.workforcemgmt.model.TaskComment;
import com.railse.hiring.workforcemgmt.model.TaskManagement;
import com.railse.hiring.workforcemgmt.model.enums.Priority;
import com.railse.hiring.workforcemgmt.model.enums.Task;
import com.railse.hiring.workforcemgmt.model.enums.TaskStatus; 
import com.railse.hiring.workforcemgmt.repository.TaskRepository; 
import com.railse.hiring.workforcemgmt.service.TaskManagementService; 
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors; 
 
@Service 
public class TaskManagementServiceImpl implements TaskManagementService { 
 
   private final TaskRepository taskRepository; 
   private final ITaskManagementMapper taskMapper;
   private final List<TaskComment> comments = new ArrayList<>();
   private final List<TaskActivity> activities = new ArrayList<>();

    @Override
    public void addComment(Long taskId, String comment, String author) {
        TaskManagement task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        TaskComment newComment = new TaskComment();
        newComment.setTaskId(taskId);
        newComment.setComment(comment);
        newComment.setAuthor(author);
        comments.add(newComment);

        TaskActivity activity = new TaskActivity();
        activity.setTaskId(taskId);
        activity.setDescription(author + " added a comment.");
        activities.add(activity);
    }


    public TaskManagementServiceImpl(TaskRepository taskRepository, ITaskManagementMapper taskMapper) {
       this.taskRepository = taskRepository; 
       this.taskMapper = taskMapper; 
   } 
 
   @Override 
   public TaskManagementDto findTaskById(Long id) { 
       TaskManagement task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id)); 
       return taskMapper.modelToDto(task); 
   }

    @Override
    public TaskDetailsDto findTaskDetails(Long taskId) {
        TaskManagementDto task = findTaskById(taskId);

        List<TaskComment> taskComments = comments.stream()
                .filter(c -> c.getTaskId().equals(taskId))
                .sorted(Comparator.comparing(TaskComment::getTimestamp))
                .collect(Collectors.toList());

        List<TaskActivity> taskActivities = activities.stream()
                .filter(a -> a.getTaskId().equals(taskId))
                .sorted(Comparator.comparing(TaskActivity::getTimestamp))
                .collect(Collectors.toList());

        TaskDetailsDto details = new TaskDetailsDto();
        details.setTask(task);
        details.setComments(taskComments);
        details.setActivityHistory(taskActivities);
        return details;
    }


    @Override
   public List<TaskManagementDto> createTasks(TaskCreateRequest createRequest) { 
       List<TaskManagement> createdTasks = new ArrayList<>(); 
       for (TaskCreateRequest.RequestItem item : createRequest.getRequests()) { 
           TaskManagement newTask = new TaskManagement(); 
           newTask.setReferenceId(item.getReferenceId()); 
           newTask.setReferenceType(item.getReferenceType()); 
           newTask.setTask(item.getTask()); 
           newTask.setAssigneeId(item.getAssigneeId()); 
           newTask.setPriority(item.getPriority()); 
           newTask.setTaskDeadlineTime(item.getTaskDeadlineTime()); 
           newTask.setStatus(TaskStatus.ASSIGNED); 
           newTask.setDescription("New task created."); 
           createdTasks.add(taskRepository.save(newTask)); 
       } 
       return taskMapper.modelListToDtoList(createdTasks); 
   } 
 
   @Override 
   public List<TaskManagementDto> updateTasks(UpdateTaskRequest updateRequest) { 
       List<TaskManagement> updatedTasks = new ArrayList<>(); 
       for (UpdateTaskRequest.RequestItem item : updateRequest.getRequests()) { 
           TaskManagement task = taskRepository.findById(item.getTaskId()).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + item.getTaskId())); 
 
           if (item.getTaskStatus() != null) { 
               task.setStatus(item.getTaskStatus()); 
           } 
           if (item.getDescription() != null) { 
               task.setDescription(item.getDescription()); 
           } 
           updatedTasks.add(taskRepository.save(task)); 
       } 
       return taskMapper.modelListToDtoList(updatedTasks); 
   }

    @Override
    public String assignByReference(AssignByReferenceRequest request) {
        List<Task> applicableTasks = Task.getTasksByReferenceType(request.getReferenceType());

        List<TaskManagement> existingTasks = taskRepository
                .findByReferenceIdAndReferenceType(request.getReferenceId(), request.getReferenceType());

        for (Task taskType : applicableTasks) {
            List<TaskManagement> tasksOfType = existingTasks.stream().filter(t -> t.getTask() == taskType && t.getStatus() != TaskStatus.COMPLETED).collect(Collectors.toList());

            if (!tasksOfType.isEmpty()) {
                TaskManagement taskToAssign = tasksOfType.get(0);
                taskToAssign.setAssigneeId(request.getAssigneeId());
                taskToAssign.setStatus(TaskStatus.ASSIGNED);
                taskRepository.save(taskToAssign);

                for (int i = 1; i < tasksOfType.size(); i++) {
                    TaskManagement taskToCancel = tasksOfType.get(i);
                    taskToCancel.setStatus(TaskStatus.CANCELLED);
                    taskRepository.save(taskToCancel);
                }
            } else {
                TaskManagement newTask = new TaskManagement();
                newTask.setReferenceId(request.getReferenceId());
                newTask.setReferenceType(request.getReferenceType());
                newTask.setTask(taskType);
                newTask.setAssigneeId(request.getAssigneeId());
                newTask.setStatus(TaskStatus.ASSIGNED);
                taskRepository.save(newTask);
            }
        }

        return "Tasks assigned successfully for reference " + request.getReferenceId();
    }


    @Override
    public List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request) {
        List<TaskManagement> tasks = taskRepository.findByAssigneeIdIn(request.getAssigneeIds());

        List<TaskManagement> filteredTasks = tasks.stream()
                .filter(task -> {
                    TaskStatus status = task.getStatus();
                    boolean isActive = status != TaskStatus.COMPLETED && status != TaskStatus.CANCELLED;

                    long deadline = task.getTaskDeadlineTime();
                    long startDate = request.getStartDate();
                    long endDate = request.getEndDate();

                    boolean withinRange = deadline >= startDate && deadline <= endDate;
                    boolean beforeRangeButStillOpen = deadline < startDate && isActive;

                    return (withinRange && isActive) || beforeRangeButStillOpen;
                })
                .collect(Collectors.toList());

        return taskMapper.modelListToDtoList(filteredTasks);
    }

    @Override
    public TaskManagementDto updateTaskPriority(Long taskId, Priority priority) {
        TaskManagement task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        task.setPriority(priority);  // <-- Update the field
        TaskManagement updated = taskRepository.save(task);

        return taskMapper.modelToDto(updated);
    }

    @Override
    public List<TaskManagementDto> getTasksByPriority(Priority priority) {
        List<TaskManagement> tasks = taskRepository.findByPriority(priority);
        return taskMapper.modelListToDtoList(tasks);
    }






} 
