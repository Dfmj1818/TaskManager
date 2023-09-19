package Exceptions;

public class UserEmptyTaskListException extends RuntimeException {

	public UserEmptyTaskListException() {
		super("No tienes tareas creadas hasta el momento");
	}
}
