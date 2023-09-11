package Model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {
	
	public TaskManager() {
		
	}
	
	public Task createTask(User user, LocalDate todayDate,LocalDate dueDate,String taskContent,boolean stateOfTask) {
		Task task=new Task(user,todayDate,dueDate,taskContent,false);
		return task;
	}
	
	public List<Task>findIncompletesTasks(User user){
		List<Task>incompletesTasks=user.getTasksList().stream()
	             	       .filter(task->!task.getStateOfTask())
	             	       .collect(Collectors.toList());
		
		return incompletesTasks;
	}
	
	public void setTaskId(List<Task>tasksList){
		int id=0;
       	for(Task task:tasksList){
       		task.setId(id++);
       	}
	}
	

}