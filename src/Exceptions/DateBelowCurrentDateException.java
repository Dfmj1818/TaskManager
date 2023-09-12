package Exceptions;

public class DateBelowCurrentDateException extends RuntimeException{

	public DateBelowCurrentDateException() {
		super("No puedes escoger una fecha anterior a la actual,Vuelve a intentarlo\n");
	}
}
