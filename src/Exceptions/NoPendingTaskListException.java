package Exceptions;

public class NoPendingTaskListException extends RuntimeException {

	public NoPendingTaskListException() {
		super("No tienes tareas Pendientes hasta el momento");
	}
}
