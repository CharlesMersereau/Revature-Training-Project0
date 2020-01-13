package com.revature.driver;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.users.Customer;
import com.revature.users.User;
import com.revature.users.UserService;
import com.revature.util.LoggerUtil;

public class CarLotDriver {
	private static LoggerUtil logger = new LoggerUtil();
	private static Scanner scan = new Scanner(System.in);
	private static UserService users = new UserService();
	private static User currentUser = null;
	

	public static void main(String[] args) {		
		String option = "";
		String username;
		String password;

		users.loadUsers();
		
		do {
			displayOptions();
			option = scan.nextLine();
			handleOption(option);
			
		} while(!"exit".equals(option));
		
		System.out.println("Thank you for using the car lot tool. Goodbye!");
		
	}
	
	private static void displayOptions() {
		System.out.println("Select an option...");
		if (currentUser == null) {
			System.out.println("'register' to register as a new user.");
			System.out.println("'login' to log into your account.");
		} else {
			currentUser.displayOptions();
			System.out.println("'logout' to log out o this account.");
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
			currentUser = user;
			
		} else if ("register".equals(option)) {
			System.out.println("Enter a username and password to register.");
			User user = (Customer) getUserInfo();
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
			currentUser = null;
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
