package Exceptions;

public class NotFoundTaskException extends RuntimeException{

	public NotFoundTaskException() {
		super("El ID Digitado no coincide con ninguna tarea");
	}
}
