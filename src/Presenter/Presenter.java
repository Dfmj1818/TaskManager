package Presenter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import Exceptions.AgeBelowAgeException;
import Exceptions.AttemptsExceededException;
import Exceptions.DateBelowCurrentDateException;
import Exceptions.NoPendingTasksException;
import Exceptions.NotFoundTaskException;
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


		while(!exit){
			view.showMessage("Bienvenido "+user.getNickName());
			view.showMessage("¿Que Deseas Hacer Hoy?");
			view.showMessage("1.Crear Tarea\n2.Ver tareas Pendientes\n3.Ver Todas las tareas\n4.Borrar Tarea\n5.Salir");
			digitedOption=view.readInt();
			switch(digitedOption){
			case 1:
				createTask(user);
				break;
			case 2:
				viewIncompletesTask(user);
				break;
			case 3:
				viewAllTasks(user);
				break;	
			case 4:
				eraseTask(user);
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
        boolean exit=false;
        
		while(!exit) {
			try {
				view.showMessage("Ingresa tu correo Electronico");
				mail=view.readString();
				view.showMessage("Ingresa tu contraseña");
				password=view.readString();
				foundUser=usermanager.findUserInDataBase(mail, password);
				exit=true;
			}catch(UserNotFoundException e){
				view.showMessage(e.getMessage());	
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

	public void createTask(User user){
		String taskContent;
		LocalDate startDate;
		String dueDateAsString;
		LocalDate dueDate;
		DateTimeFormatter dateFormat;
		LocalDate todayDate;
		boolean correctFormat=false;
		Task task=null;
		LocalTime s;
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
		user.addTaskToList(task);
		taskManager.setTaskId(user);
		taskManager.addTaskToTaskHistory(task);
	}


	public void viewAllTasks(User user){
		try {
			List<Task>tasksHistory=user.getTasksList();
			taskManager.verifyIsTaskListIsEmpty(user);
			user.viewTasksList(tasksHistory);
		}catch(UserTaskListEmptyException e){
			view.showMessage(e.getMessage());
		}

	}

	public void viewIncompletesTask(User user) {
		String yesOrNotAnswer="";

		try {
			taskManager.verifyIsTaskListIsEmpty(user);
			List<Task>incompletesTasks=taskManager.findIncompletesTasks(user);
			taskManager.verifyIncompletesTasks(incompletesTasks);
			user.viewTasksList(incompletesTasks);
			view.showMessage("¿Deseas Marcar Como Completada alguna Tarea?");
			yesOrNotAnswer=view.readString();
			if(yesOrNotAnswer.equals("si")){
				view.showMessage("¿Que tarea deseas Marcar Como completada?");
				int digitedTask=view.readInt();
				Task taskToErase=taskManager.getChoosedTaskCompleted(user,digitedTask);
				taskToErase.setStateOfTask(true);			            
			}
		}catch(UserTaskListEmptyException e){
			view.showMessage(e.getMessage());
		}catch(NotFoundTaskException e){
			view.showMessage(e.getMessage());
		}catch(NoPendingTasksException e){
			view.showMessage(e.getMessage());
		}
	}

	public void eraseTask(User user){
		int digitedTaskToErase;

		try {
			user.viewTasksList(user.getTasksList());
			view.showMessage("¿Que tarea deseas Eliminar?");
			digitedTaskToErase=view.readInt();
			taskManager.verifyIsTaskListIsEmpty(user);
			user.eraseTask(digitedTaskToErase);
			view.showMessage("Tarea eliminada con exito");
		}catch(UserTaskListEmptyException e){
			view.showMessage(e.getMessage());
		}

	}


}




