package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Exceptions.AgeBelowAgeException;
import Exceptions.DateBelowCurrentDateException;
import Exceptions.NoPendingTasksException;
import Exceptions.NotFoundTaskException;
import Exceptions.UserTaskListEmptyException;

public class TaskManager {
	private List<Task> tasksHistory;
    
	public TaskManager() {
		tasksHistory=new ArrayList<Task>();
	}

	public Task createTask(User user, LocalDate todayDate,LocalDate dueDate,String taskContent,boolean stateOfTask) {
		Task task=new Task(user,todayDate,dueDate,taskContent,false);
		return task;
	}

	public void addTaskToTaskHistory(Task task){
		tasksHistory.add(task);
	}

	public List<Task>findIncompletesTasks(User user){
		List<Task>incompletesTasks=user.getTasksList().stream()
				.filter(task->!task.getStateOfTask())
				.collect(Collectors.toList());
                
		return incompletesTasks;
	}

	public void setTaskId(User user){
		int id=0;
		for(Task task:user.getTasksList()){
			id++;
			task.setId(id);
		}
	}

	public void verifyDueDate(LocalDate digitedDueDate){
		if(digitedDueDate.isBefore(LocalDate.now())){
			throw new DateBelowCurrentDateException();
		}
	}
	
    public void verifyIsTaskListIsEmpty(User user){
    	if(user.getTasksList().isEmpty()){
    		throw new UserTaskListEmptyException();
    	}
    }
    
    public void verifyIncompletesTasks(List<Task>incompleteTasks){
    	if(incompleteTasks.isEmpty()){
    		throw new NoPendingTasksException();
    	}
    }
    
    public Task getChoosedTaskCompleted(User user,int digitiedTask) {	
		for(Task task:user.getTasksList()){
			if(task.getId()==digitiedTask){
				return task;
			}  	
		}
		throw new NotFoundTaskException();	
	}
    
    public String 
  


}