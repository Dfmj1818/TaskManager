package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Exceptions.NotFoundTaskException;

public class User {
	private String nickName;
	private String mail;
	private String password;
	private List<Task>tasksList;
	private LocalDate birthDate;

	public User(String nickName,String mail,String password){
		this.nickName=nickName;
		this.mail=mail;
		this.password=password;
		tasksList=new ArrayList<Task>();
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Task> getTasksList() {
		return tasksList;
	}

	public void setTasksList(List<Task> tasksList) {
		this.tasksList = tasksList;
	}

	public void setBirthDate(LocalDate birthDate){
		this.birthDate=birthDate;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void addTaskToList(Task task) {
		tasksList.add(task);
	}

	public void viewTasksList(List<Task>tasksList){
		tasksList.forEach(task->System.out.println(task));
	}

	public void eraseTask(int choosedOption){
		tasksList.removeIf(task->task.getId()==choosedOption);
	}

	public void verifyStateOfTask(){
		for(Task task:tasksList){
			if(task.getStateOfTask()){
				task.setCheckedOrUncheckedTask("[✓]");
			}
			else {
				task.setCheckedOrUncheckedTask("[X]");
			}
		}

	}

	

}
