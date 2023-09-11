package Exceptions;

public class AttemptsExceededException extends RuntimeException {

	public AttemptsExceededException() {
		super("Numero de intentos Excedido,Vuelve a intentarlo mas tarde");
	}
}
