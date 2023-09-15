package Presenter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;

import Exceptions.AgeBelowAgeException;
import Exceptions.AttemptsExceededException;
import Exceptions.DateBelowCurrentDateException;
import Exceptions.UserNotFoundException;
import Exceptions.UserTaskListEmptyException;
import Model.Task;
import Model.TaskManager;
import Model.User;
import Model.UserManager;
import View.View;

public class Presenter {
	private View view;
	private UserManager usermanager;
	private TaskManager taskManager;

	public Presenter() {
		view=new View();
		usermanager=new UserManager();
		taskManager=new TaskManager();
	}

	public static void main(String[]args){
		Presenter presenter=new Presenter();
		int digitedOption;
		boolean exit=false;

		presenter.view.showMessage("TASK MANAGER");
		while(!exit){
			presenter.view.showMessage("¿Que deseas hacer hoy?\n1.Registrarte\n2.Iniciar sesion\n3.Salir");
			digitedOption=presenter.view.readInt();
			switch(digitedOption){
			case 1:
				User registeredUser=presenter.registerUser();
				presenter.runServices(registeredUser);
				break;
			case 2:
				User foundUser=presenter.loginUser();
				presenter.runServices(foundUser);
				break;
			case 3:
				exit=true;
				break;

			default:
				presenter.view.showMessage("La Opcion Digitada no existe,Por favor vuelve a intentarlo");
				break;
			}
		}

	}

	public void runServices(User user){
		int digitedOption;
		boolean exit=false;
		int taskToErase=0;

		while(!exit){
			view.showMessage("Bienvenido "+user.getNickName());
			view.showMessage("¿Que Deseas Hacer Hoy?");
			view.showMessage("1.Crear Tarea\n2.Ver tareas Pendientes\n3.Ver Todas las tareas\n4.Borrar Tarea\n5.Salir");
			digitedOption=view.readInt();
			switch(digitedOption){
			case 1:
				Task currentTask=createTask(user);	
				user.addTaskToList(currentTask);
				taskManager.addTaskToTaskHistory(currentTask);
				taskManager.setTaskId(user.getTasksList());
				break;
			case 2:
				try {
					taskManager.verifyIsTaskListIsEmpty(user);
				}catch(UserTaskListEmptyException e){
					view.showMessage(e.getMessage());
				}
				List<Task>incompletesTasks=taskManager.findIncompletesTasks(user);
				user.viewTasksList(incompletesTasks);
				break;
			case 3:
				List<Task>tasksHistory=user.getTasksList();
				user.viewTasksList(tasksHistory);
				break;	
			case 4:
				try {
					user.viewTasksList(user.getTasksList());
					view.showMessage("¿Que tarea deseas Eliminar?");
					taskToErase=view.readInt();
					taskManager.verifyIsTaskListIsEmpty(user);
				}catch(UserTaskListEmptyException e){
					view.showMessage(e.getMessage());
				}
				user.eraseTask(taskToErase);
				view.showMessage("Tarea eliminada con exito");
				break;
			case 5:
				exit=true;
				break;

			default:
				view.showMessage("La opcion digitada no existe,vuelve a intentarlo");
				break;

			}
		}

	}

	public User loginUser(){
		String mail;
		String password;
		User foundUser = null;
		int attempts=0;

		while(attempts<=6) {
			view.showMessage("Ingresa tu correo Electronico");
			mail=view.readString();
			view.showMessage("Ingresa tu contraseña");
			password=view.readString();
			try {
				foundUser=usermanager.findUserInDataBase(mail, password);

			}catch(UserNotFoundException e){
				view.showMessage(e.getMessage());
				attempts++;	
			}catch(AttemptsExceededException e){
				view.showMessage(e.getMessage());
				break;
			}

		}
		return foundUser;
	}

	public User registerUser() {
		String mail;
		String password;
		String nickname;
		String birthDateAsString;
		LocalDate birthDate = null;
		DateTimeFormatter birthDateFormat;
		boolean correctFormat=false;

		while(!correctFormat){
			try {
				view.showMessage("BIENVENIDO A TASK MANAGER!!\n\nA Continuacion Sigue los siguientes Pasos");
				view.showMessage("Digita tu fecha de nacimiento en formato dd/MM/yyyy");
				birthDateAsString=view.readString();
				birthDateFormat=DateTimeFormatter.ofPattern("dd/MM/yyyy");
				birthDate=LocalDate.parse(birthDateAsString,birthDateFormat);
				usermanager.verifyUserAge(birthDate);
				correctFormat=true;

			}catch(DateTimeException e){
				view.showMessage("Formato ingresado incorrecto,vuelve a intentarlo");
			}catch(AgeBelowAgeException e){
				view.showMessage(e.getMessage());
			}

		}
		view.showMessage("Digita Tu Correo Electronico");
		mail=view.readString();
		view.showMessage("Digita tu contraseña");
		password=view.readString();
		view.showMessage("Digita tu nickname");
		nickname=view.readString();
		User user=new User(nickname,mail,password);
		user.setBirthDate(birthDate);
		usermanager.addUserToList(user);

		return user;

	}

	public Task createTask(User user){
		String taskContent;
		LocalDate startDate;
		String dueDateAsString;
		LocalDate dueDate;
		DateTimeFormatter dateFormat;
		LocalDate todayDate;
		boolean correctFormat=false;
		Task task=null;

		view.showMessage("¿Digita el Proposito de la tarea");
		taskContent=view.readString();
		todayDate=LocalDate.now();
		while(!correctFormat){
			try {
				view.showMessage("Digita La fecha limite para esta tarea");
				dueDateAsString=view.readString();
				dateFormat=DateTimeFormatter.ofPattern("dd/MM/yyyy");
				dueDate=LocalDate.parse(dueDateAsString,dateFormat);
				taskManager.verifyDueDate(dueDate);
				correctFormat=true;
				task=taskManager.createTask(user, todayDate, dueDate, taskContent, correctFormat);
			}catch(DateTimeException e){
				view.showMessage("Formato Digitado Incorrecto,Vuelve a intentarlo");
			}catch(DateBelowCurrentDateException e){
				view.showMessage(e.getMessage());
			}
		}
		view.showMessage("Tarea Creada Con exito");
		return task;
	}


}
