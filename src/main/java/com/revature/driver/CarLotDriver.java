package com.revature.driver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.revature.cars.CarService;
import com.revature.dao.CarDAO;
import com.revature.dao.CarDAOPostgres;
import com.revature.dao.OfferServiceDAO;
import com.revature.dao.OfferServiceDAOSerialization;
import com.revature.dao.PaymentServiceDAO;
import com.revature.dao.PaymentServiceDAOSerialization;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOPostgres;
import com.revature.offer.OfferService;
import com.revature.payment.PaymentService;
import com.revature.pojo.Car;
import com.revature.pojo.Offer;
import com.revature.pojo.Payment;
import com.revature.users.User;
import com.revature.users.UserService;
import com.revature.util.LoggerUtil;

public class CarLotDriver {
	
	private static LoggerUtil logger = new LoggerUtil();
	private static Scanner scan = new Scanner(System.in);
	private static UserDAO usersDAO = new UserDAOPostgres();
	private static UserService users = new UserService();
	private static CarDAO lotDAO = new CarDAOPostgres();
	private static CarService cars = new CarService();
	private static OfferServiceDAO offersDAO = new OfferServiceDAOSerialization();
	private static OfferService offers = new OfferService();
	private static PaymentServiceDAO paymentsDAO = new PaymentServiceDAOSerialization();;
	private static PaymentService payments = new PaymentService(); 
	

	public static void main(String[] args) {
		
		String option = "";
	
		logger.info("System booted up");
		try {
			cars.loadCars();
		} catch (SQLException e) {
			// set flag to reload
		}

		System.out.println(bannerCreator("Welcome to the car lot!"));
		
		do {
			
			
			do {
				displayOptions();
				option = scan.nextLine();
				try {
					Integer i = Integer.parseInt(option);
					if (users.getCurrentUser() != null) {
						option = users.interpretOption(i);
					} else {
						if (i == 1) {
							option = "login";
						} else if (i == 2) {
							option = "register";
						} else if (i ==3) {
							option = "exit";
						} else {
							option = "try again";
						}
					}
					
					if("try again".equals(option)) {
						System.out.println("\nInvalid option".toUpperCase());
					}
				} catch (NumberFormatException e) {
					System.out.println("\nInvalid option".toUpperCase());
					option = "try again";
				} catch (Exception e) {
					// hopefully this doesn't happen
				}
				
				
			} while ("try again".equals(option));
			
			handleOption(option);
			
		} while(!"exit".equals(option));
		
	}
	
	private static void displayOptions() {
		
		System.out.println(bannerCreator("Select an option from the list below"));
		
		if (users.getCurrentUser() == null) {
			System.out.println("[1] Log into your account");
			System.out.println("[2] Register new user");
			System.out.print("[3] Exit the car lot app");
		} else {
			users.displayOptions();
		}
		System.out.print("\n\nOption: ");
		
	}
	
	private static void handleOption(String option) {
		option = option.toLowerCase();
		
		if ("accept offer".equals(option)) {
			
			System.out.println("\nEnter the ID of the offer you'd like to accept");
			System.out.print("ID: ");
			Integer offerId = Integer.parseInt(scan.nextLine());
			boolean accepted = offers.acceptOffer(offerId);
			
			if(accepted) {
				
//				users.transferCarToUser(car,username);
				
			}
			
		} else if ("add car".equals(option)) {
			
			Car car = new Car();
			
			System.out.println("\nEnter the information for the car you want to add");
			System.out.print("Make: ");
			car.setMake(scan.nextLine());
			
			System.out.print("Model: ");
			car.setModel(scan.nextLine());
			
			System.out.print("Year: ");
			Integer year = Integer.parseInt(scan.nextLine());
			car.setYear(year);
			
			System.out.print("Mileage: ");
			Integer mileage = Integer.parseInt(scan.nextLine());
			car.setMileage(mileage);
			
			System.out.print("Price: ");
			Integer price = Integer.parseInt(scan.nextLine());
			car.setPrice(price);
			
			try {
				cars.addCar(car);
				System.out.println("\nCar succesfully added");
			} catch (SQLException e) {
				System.out.println("\nThere was an error adding the car to the database".toUpperCase());
			}
			
		} else if ("cancel offer".equals(option)) {
			
			System.out.println("\nEnter the ID of the offer you'd like to cancel");
			System.out.print("ID: ");
			offers.cancelOffer(Integer.parseInt(scan.nextLine()));
						
		} else if ("exit".equals(option)) {
			
			System.out.println("\nThank you for using the car lot tool. Goodbye!");
			
		} else if ("login".equals(option)) {
			
			User user;
			
			do {
				user = new User();
				System.out.println("\nEnter your credentials to log in");
				
				System.out.print("Username: ");
				user.setUsername(scan.nextLine());
				
				System.out.print("Password: ");
				user.setPassword(scan.nextLine());
				
				user = users.authenticateUser(user);
				
				if (user == null) {
					
					System.out.println("\nINCORRECT USERNAME OR PASSWORD".toUpperCase());
					
					do {
						System.out.println("\nWould you like to retry loggin in?");
						System.out.println("[1] Yes");
						System.out.println("[2] No");
						System.out.print("\nOption: ");
						option = scan.nextLine();
						if (!("1".equals(option) || "2".equals(option))) {
							System.out.println("\nINVALID OPTION".toUpperCase());
						}
					} while ( !("1".equals(option) || "2".equals(option)) ); 
					
				} else {
					users.setCurrentUser(user);
					users.loadUserOptions();
					logger.info("System logged into by: " + user.getFirstName() + " " + user.getLastName() + " [" + user.getUsername() + "]");
				}
				
			} while ( user == null && "1".equals(option));
			
		} else if ("logout".equals(option)) {
			
			cars.setCurrentUserCars(new ArrayList<Car>());
			users.logoutUser();
			
		} else if ("make offer".equals(option)) {
			
			Offer offer = new Offer();
			offer.setUserId(users.getCurrentUser().getUserId());
			
			System.out.println("\nEnter the car ID and the amount of the offer");
			System.out.print("Car ID: ");
			String carId = scan.nextLine();
			
			if (cars.findCar(carId) > -1) {
				
				System.out.print("Amount: ");
				Integer amount = Integer.parseInt(scan.nextLine());
				offers.makeOffer(offer);
				
			} else {
				System.out.println("\nSorry, but we could not locate a car with that ID.");
			}
						
		} else if ("make payment".equals(option)) {
			
			System.out.println("\nLet's get some info for your payment");
			System.out.print("ID of car to make a payment on: ");
			String carId = scan.nextLine();
			System.out.print("Amount of payment: ");
			Float amount = (float)0;
					
			try {
				
				amount = Float.parseFloat(scan.nextLine());
				Payment paymentToBeMadeOn = payments.getPayment(carId);
				
				if (paymentToBeMadeOn != null) {
					paymentToBeMadeOn.makePayment(amount);
				} else {
					System.out.println("\nCannot find a payment for you matching that car ID");
				}
				
			} catch (NumberFormatException e) {
				System.out.println("\nPAYMENT AMOUNTS MUST CONTAIN NUMBERS ONLY".toUpperCase());;
			}
			
		} else if ("my cars".equals(option)) {
			
			ArrayList<Car> myCars;
			
			try {
				
				if (cars.getCurrentUserCars().size() == 0) {
					myCars = cars.loadUserCars(users.getCurrentUser().getUserId());
					cars.setCurrentUserCars(myCars);
				} else {
					myCars = cars.getCurrentUserCars();
				}
				
				System.out.println(bannerCreator("Here are your cars"));
				
				for (Car car : myCars) {
					System.out.println(car + "\n");
				}
				
			} catch (SQLException e) {
				System.out.println("\nThere was an error loading your cars".toUpperCase());
			}
			
		} else if ("my offers".equals(option)) {
			
			ArrayList<Offer> myOffers = offers.getUserOffers(users.getCurrentUser().getUsername());

			System.out.println("\nHere are your current offers:\n");
			
			for (Offer offer : myOffers) {
				System.out.println(offer + "\n");
			}
			
		} else if ("my payments".equals(option)) {
			
			ArrayList<Payment> userPayments = payments.getUserPayments(users.getCurrentUser().getUsername());
			
			System.out.println("\nThese are the current ongoing payments\n");
			for (Payment payment : userPayments) {
				System.out.println(payment.getRemainingPayments() + "\n");
			}
			
		} else if ("register".equals(option)) {
			
			User customer;
			do {
				try {
					System.out.println("\nEnter a username and password to register.");
					
					customer = new User();
					customer.setUserRoleId(0);
					
					System.out.print("Username: ");
					customer.setUsername(scan.nextLine());
					
					System.out.print("Password: ");
					customer.setPassword(scan.nextLine());
					
					System.out.print("First Name: ");
					customer.setFirstName(scan.nextLine());
					
					System.out.print("Last Name: ");
					customer.setLastName(scan.nextLine());
					users.registerCustomer(customer);
					
				} catch (SQLException e) {

					System.out.println("\nSorry, but this username has already been taken.".toUpperCase());
					
					do {
						
						System.out.println("\nWould you like to register using a different username?");
						System.out.println("[1] Yes");
						System.out.println("[2] No");
						System.out.print("\nOption: ");
						option = scan.nextLine();
						
						if (!("1".equals(option) || "2".equals(option))) {
							System.out.println("\nINVALID OPTION".toUpperCase());
						}
						
					} while (!("1".equals(option) || "2".equals(option)));
					
				}
			} while ("1".equals(option));
			
		} else if ("reject offer".equals(option)) {
			
			System.out.println("\nEnter the ID of the offer you'd like to reject");
			System.out.print("ID: ");
			offers.rejectOffer(Integer.parseInt(scan.nextLine()));
			
		} else if ("remove car".equals(option)) {
			
			System.out.println("\nPlease enter the ID of the car you'd like to remove");
			System.out.print("ID: ");
			Integer id = Integer.parseInt(scan.nextLine());
			
			try {
				Car car = new Car();
				car.setId(id);
				cars.removeCar(car);
				
				if (car != null) {
					System.out.println("\nCar succesfully removed");
//					offers.rejectOffersOfRemovedCar(id);
				}
				
			} catch (SQLException e) {
				System.out.println("There was an error removing the car from the database".toUpperCase());
			}
			
		} else if ("show lot".equals(option)) {
			
			ArrayList<Car> available = cars.getCars();
			System.out.println(bannerCreator("We currently have the following vehicles available"));
			for (Car car : available) {
				System.out.println(car.getYear() + " " + car.getMake().toUpperCase() + " " + car.getModel().toUpperCase() + " with " + String.format("%,d", car.getMileage()) + " miles for $" + String.format("%,d", car.getPrice()) +  "\n");
			}
			
		} else if ("view offers".equals(option)) {
			
			ArrayList<Offer> pendingOffers = offers.getPendingOffers();
			
			System.out.println("\nThese are the payments for all users\n");
			for (Offer offer: pendingOffers) {
				System.out.println(offer + "\n");
			}
			
		} else if ("view payments".equals(option)) {
			
			ArrayList<Payment> allPayments = payments.getPayments();
			
			System.out.println("\nThese are all the currently ongoing payments\n");
			for (Payment payment : allPayments) {
				System.out.println(payment.getUsername() + " has " + payment.getRemainingPayments() + "\n");
			}
			
		} else {
			
			System.out.println("\nSorry, you have not entered a valid option. Please try again.");
			
		}
	}
	
	private static String bannerCreator(String s) {
		String line = "";
		
		for (int i = 0; i <= s.length(); i++) {
			line += "_";
		}
		
		String string = "\n" + line + "\n\n" + s.toUpperCase() + "\n" + line + "\n";
		
		return string;
	}
	
}
