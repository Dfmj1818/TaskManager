package Exceptions;

public class AgeBelowAgeException extends RuntimeException{

	public AgeBelowAgeException() {
		super("Debes tener al menos 18 Años para Usar nuestros Servicios");
	}
}
