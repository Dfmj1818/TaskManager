package Exceptions;

public class UserTaskListEmptyException extends RuntimeException {

	public UserTaskListEmptyException() {
		super("No tienes Tareas Creadas Hasta El momento ");
	}
}
