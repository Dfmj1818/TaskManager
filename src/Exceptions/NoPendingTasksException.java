package Exceptions;

public class NoPendingTasksException extends RuntimeException {

	public NoPendingTasksException() {
		super("No tienes tareas Pendientes");
	}
}
