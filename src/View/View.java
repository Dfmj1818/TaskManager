package View;

import java.util.Scanner;

public class View {
	private Scanner scannerForInt;
	private Scanner scannerForString;
	
	public View(){
		scannerForInt=new Scanner(System.in);
		scannerForString=new Scanner(System.in);
	}
	
	public void showMessage(String message){
		System.out.println(message);
	}
	
	public int readInt() {
		return scannerForInt.nextInt();
	}
	
	public String readString() {
		return scannerForString.nextLine();
	}
	
	
}
