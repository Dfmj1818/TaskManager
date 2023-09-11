package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task {
	private User user;
	private LocalDate startDate;
	private LocalDate dueDate;
	private boolean stateOfTask;
	private String taskContent;
	private int id;
	
	public Task(User user,LocalDate startDate,LocalDate dueDate,String taskContent,boolean stateOfTask){
		this.user=user;
		this.startDate=startDate;
		this.dueDate=dueDate;
		this.taskContent=taskContent;
		this.stateOfTask=stateOfTask;
	}

	public void setUser(User user){
		this.user=user;
	}

	public User getUser() {
		return user;
	}

	public void setStartDate(LocalDate startDate){
		this.startDate=startDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setDueDate(LocalDate dueDate){
		this.dueDate=dueDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setStateOfTask(boolean stateOfTask){
		this.stateOfTask=stateOfTask;
	}

	public boolean getStateOfTask() {
		return stateOfTask;
	}

	public void setTaskContent(String taskContent){
		this.taskContent=taskContent;
	}

	public String getTaskContent() {
		return taskContent;
	}
	
	public void setId(int id){
		this.id=id;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Tarea Hecha por "+user.getNickName()+"\n"
				+"Fecha De Inicio: "+getStartDate()+"\n"
				+"Fecha de Vencimiento: "+getDueDate()+"\n"
				+"Estado de la Tarea:"+getStateOfTask();
	}



}
