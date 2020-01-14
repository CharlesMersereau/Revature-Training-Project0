package com.revature.driver;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.dao.LotDAO;
import com.revature.dao.LotDAOSerialization;
import com.revature.dao.OfferServiceDAO;
import com.revature.dao.OfferServiceDAOSerialization;
import com.revature.dao.UserServiceDAO;
import com.revature.dao.UserServiceDAOSerialization;
import com.revature.lot.Lot;
import com.revature.offer.OfferService;
import com.revature.pojo.Car;
import com.revature.pojo.Offer;
import com.revature.users.Customer;
import com.revature.users.User;
import com.revature.users.UserService;
import com.revature.util.LoggerUtil;

public class CarLotDriver {
	
	private static LoggerUtil logger = new LoggerUtil();
	private static Scanner scan = new Scanner(System.in);
	private static UserServiceDAO usersDAO = new UserServiceDAOSerialization();
	private static UserService users;
	private static LotDAO lotDAO = new LotDAOSerialization();
	private static Lot lot;
	private static OfferServiceDAO offersDAO = new OfferServiceDAOSerialization();
	private static OfferService offers;
	

	public static void main(String[] args) {
		
		String option = "";
		
		// for Dev
//		users = new UserService();
//		users.addEmployee("chuck", "pass");
//		users.registerUser(new User("bigben","thefro"));
//		lot = new Lot();
//		lot.addCar(new Car("ford","mustang","1975","65000"));
//		lot.addCar(new Car("nissan","roge","2019","15000"));
//		offers = new OfferService();
//		offers.makeOffer(new Offer("bigben","NR1",12500));
		
		// for Prod
		users = usersDAO.readUserService();
		lot = lotDAO.readLot();
		offers = offersDAO.readOfferService();
		
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
		
		if ("accept".equals(option)) {
			
			System.out.println("Enter the ID of the offer you'd like to accept");
			System.out.println("ID: ");
			String offerId = scan.nextLine();
			boolean accepted = offers.acceptOffer(offerId);
			
			if(accepted) {
				Car car = lot.removeCar(offers.getOffer(offerId).getCarId());
				users.transferCarToUser(car,offers.getOffer(offerId).getUsername());
			}
			
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
			
		} else if ("cancel".equals(option)) {
			
			System.out.println("Enter the ID of the offer you'd like to cancel");
			System.out.println("ID: ");
			offers.cancelOffer(scan.nextLine());
						
		} else if ("exit".equals(option)) {
			
			usersDAO.persistUserService(users);
			lotDAO.persistLot(lot);
			offersDAO.persistOfferService(offers);
			System.out.println("Thank you for using the car lot tool. Goodbye!");
			
		} else if ("login".equals(option)) {
			
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
			
		} else if ("logout".equals(option)) {
			
			users.logoutUser();
			
		} else if ("lot".equals(option)) {
			
			ArrayList<Car> cars = lot.getCars();
			System.out.println("\nWe currently have the following vehicles available:\n");
			for (Car car : cars) {
				System.out.println(car + "\n");
			}
			
		} else if ("make".equals(option)) {
			
			System.out.println("Enter the car ID and the amount of the offer");
			System.out.println("Car ID: ");
			String carId = scan.nextLine();
			
			if (lot.findCar(carId) > -1) {
				
				System.out.println("Amount: ");
				Integer amount = Integer.parseInt(scan.nextLine());
				offers.makeOffer(new Offer(users.getCurrentUser().getUsername(),carId,amount));
				
			} else {
				System.out.println("Sorry, but we could not locate a car with that ID.");
			}
						
		} else if ("my cars".equals(option)) {
			
			ArrayList<Car> myCars = ((Customer)users.getCurrentUser()).getMyCars();
			
			System.out.println("Here are your cars:\n");
			
			for (Car car : myCars) {
				System.out.println(car + "\n");
			}
			
		} else if ("my offers".equals(option)) {
			
		} else if ("my payments".equals(option)) {
			
		} else if ("offers".equals(option)) {
			
		} else if ("payments".equals(option)) {
			
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
			
		} else if ("reject".equals(option)) {
			
			System.out.println("Enter the ID of the offer you'd like to reject");
			System.out.println("ID: ");
			offers.rejectOffer(scan.nextLine());
			
		} else if ("remove".equals(option)) {
			
			System.out.println("Please enter the ID of the car you'd like to remove");
			System.out.println("ID: ");
			String id = scan.nextLine();
			
			lot.removeCar(id);
			
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
