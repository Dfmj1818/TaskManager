package Exceptions;


public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException() {
		super("Usuario o contraseña Incorrectos,Vuelve a intentarlo");
	}

}
