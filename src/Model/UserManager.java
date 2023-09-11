package Model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import Exceptions.AgeBelowAgeException;
import Exceptions.UserNotFoundException;

public class UserManager {
	private List<User>usersDataBase;


	public UserManager() {
		usersDataBase=new ArrayList<User>();
	}

	public void addUserToList(User user){
		usersDataBase.add(user);
	}

	public void setUsersDataBase(List<User>usersDataBase){
		this.usersDataBase=usersDataBase;
	}

	public List<User>getUsersDataBase(){
		return usersDataBase;
	}

	public User findUserInDataBase(String mail,String password){
		User foundUser=usersDataBase.stream()
				.filter(user->user.getMail().equals(mail)&&user.getPassword().equals(password))
				.findFirst()
				.orElseThrow(UserNotFoundException::new);

		return foundUser;
	}

	public int verifyUserAge(LocalDate userBirthDate){
		Period userAge=Period.between(userBirthDate, LocalDate.now());
		if(userAge.getYears()>=18){
			return userAge.getYears();
		}
		throw new AgeBelowAgeException();
	}

}
