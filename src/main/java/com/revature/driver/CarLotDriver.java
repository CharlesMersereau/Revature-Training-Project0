package com.revature.driver;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.dao.LotDAO;
import com.revature.dao.LotDAOSerialization;
import com.revature.dao.UserServiceDAO;
import com.revature.dao.UserServiceDAOSerialization;
import com.revature.lot.Lot;
import com.revature.pojo.Car;
import com.revature.users.Customer;
import com.revature.users.Employee;
import com.revature.users.User;
import com.revature.users.UserService;
import com.revature.util.LoggerUtil;

public class CarLotDriver {
	
	private static LoggerUtil logger = new LoggerUtil();
	private static Scanner scan = new Scanner(System.in);
	private static UserServiceDAO usDAO = new UserServiceDAOSerialization();
	private static UserService users = new UserService();
	private static LotDAO lotDAO = new LotDAOSerialization();
	private static Lot lot = new Lot();
	

	public static void main(String[] args) {
		
		String option = "";
		
		users = usDAO.readUserService();
		lot = lotDAO.readLot();
//		lot.addCar(new Car("Ford", "Expedition","2000","209,000"));
//		lot.addCar(new Car("Nissan", "Rogue", "2018","18,000"));
		
		do {
			
			displayOptions();
			option = scan.nextLine();
			handleOption(option);
			
		} while(!"exit".equals(option));
		
	}
	
	private static void displayOptions() {
		
		System.out.println("Select an option...");
		
		if (users.getCurrentUser() == null) {
			System.out.println("'register' to register as a new user.");
			System.out.println("'login' to log into your account.");
		} else {
			users.displayOptions();
			System.out.println("'logout' to log out of this account.");
		}
		
		System.out.println("'exit' to exit the car lot tool.");
		
	}
	
	private static void handleOption(String option) {
		option = option.toLowerCase();
		
		if ("login".equals(option)) {
			
			System.out.println("Enter your credentials to log in");
			User user = getUserInfo();
			user = users.authenticateUser(user);
			
			while(user == null) {
				
				System.out.println("Enter 'exit' to exit login, or anything else to retry.");
				option = scan.nextLine();
				
				if("exit".equals(option)) {
					break;
				} else {
					user = getUserInfo();
				}
			};
			
		} else if ("register".equals(option)) {
			
			System.out.println("Enter a username and password to register.");
			User user = getUserInfo();
			
			while(!users.registerUser(user)) {
				System.out.println("Enter 'exit' to exit the register page, or anything else to retry.");
				option = scan.nextLine();
				if("exit".equals(option)) {
					break;
				} else {
					user = getUserInfo();
				}
			};
			
		} else if ("logout".equals(option)) {
			
			users.logoutUser();
			
		} else if ("exit".equals(option)) {
			
			usDAO.persistUserService(users);
			lotDAO.persistLot(lot);
			System.out.println("Thank you for using the car lot tool. Goodbye!");
			
		} else if ("lot".equals(option)) {
			
			ArrayList<Car> cars = lot.getCars();
			System.out.println("\nWe currently have the following vehicles available:\n");
			for (Car car : cars) {
				System.out.println(car + "\n");
			}
			
		} else if ("make".equals(option)) {
						
		} else if ("cancel".equals(option)) {
						
		} else if ("cars".equals(option)) {
			
		} else if ("my payments".equals(option)) {
			
		} else if ("add".equals(option)) {
			
			System.out.println("Enter the information for the car you want to add");
			System.out.println("Make: ");
			String make = scan.nextLine();
			
			System.out.println("Model: ");
			String model = scan.nextLine();
			
			System.out.println("Year: ");
			String year = scan.nextLine();
			
			System.out.println("Mileage: ");
			String mileage = scan.nextLine();
			
			lot.addCar(new Car(make, model, year, mileage));
			
		} else if ("remove".equals(option)) {
			
			System.out.println("Please enter the ID of the car you'd like to remove");
			System.out.println("ID: ");
			String id = scan.nextLine();
			
			lot.removeCar(id);
			
		} else if ("accept".equals(option)) {
			
		} else if ("reject".equals(option)) {
			
		} else if ("payments".equals(option)) {
			
		} else {
			
			System.out.println("Sorry, you have not entered a valid option. Please try again.");
			
		}
	}
	
	private static User getUserInfo() {
		
		User user = new User();
		
		System.out.println("Username: ");
		user.setUsername(scan.nextLine());
		
		System.out.println("Password: ");
		user.setPassword(scan.nextLine());
		
		return user;
	}
}
