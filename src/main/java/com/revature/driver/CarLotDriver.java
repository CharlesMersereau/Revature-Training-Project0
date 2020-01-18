package com.revature.driver;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.dao.ConnectionFactory;
import com.revature.dao.LotDAO;
import com.revature.dao.LotDAOSerialization;
import com.revature.dao.OfferServiceDAO;
import com.revature.dao.OfferServiceDAOSerialization;
import com.revature.dao.PaymentServiceDAO;
import com.revature.dao.PaymentServiceDAOSerialization;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOPostgres;
import com.revature.lot.Lot;
import com.revature.offer.OfferService;
import com.revature.payment.PaymentService;
import com.revature.pojo.Car;
import com.revature.pojo.Offer;
import com.revature.pojo.Payment;
import com.revature.users.Customer;
import com.revature.users.User;
import com.revature.users.UserService;
import com.revature.util.LoggerUtil;

public class CarLotDriver {
	
	private static LoggerUtil logger = new LoggerUtil();
	private static Scanner scan = new Scanner(System.in);
	private static UserDAO usersDAO = new UserDAOPostgres();
	private static UserService users = new UserService();
	private static LotDAO lotDAO = new LotDAOSerialization();
	private static Lot lot;
	private static OfferServiceDAO offersDAO = new OfferServiceDAOSerialization();
	private static OfferService offers;
	private static PaymentServiceDAO paymentsDAO = new PaymentServiceDAOSerialization();;
	private static PaymentService payments; 
	

	public static void main(String[] args) {
		
		String option = "";
		
		// for Dev
//		users = new UserService();
//		users.addEmployee("chuck", "pass");
//		users.registerUser(new User("bigben","thefro"));
//		users.registerUser(new User("sheed","thefro"));
//		lot = new Lot();
//		lot.addCar(new Car("ford","mustang","1975","65000"));
//		lot.addCar(new Car("nissan","rogue","2019","15000"));
//		lot.addCar(new Car("chevy","camaro","2015","28000"));
//		offers = new OfferService();
//		offers.makeOffer(new Offer("bigben","NR1",12500));
//		offers.makeOffer(new Offer("sheed","NR1",15000));
//		offers.makeOffer(new Offer("sheed","FM0",9500));
//		payments = new PaymentService();
		
		
		// for Prod
		lot = lotDAO.readLot();
		offers = offersDAO.readOfferService();
		payments = paymentsDAO.readPaymentService();
		logger.info("System booted up");
		
		do {
			
			displayOptions();
			option = scan.nextLine();
			handleOption(option);
			
		} while(!"exit".equals(option));
		
	}
	
	private static void displayOptions() {
		
		System.out.println("\nSelect an option from the list below\n");
		
		if (users.getCurrentUser() == null) {
			System.out.println("'register' to register as a new user.");
			System.out.println("'login' to log into your account.");
		} else {
			users.displayOptions();
			System.out.println("'logout' to log out of this account.");
		}
		
		System.out.print("'exit' to exit the car lot tool.\n\nOption: ");
		
	}
	
	private static void handleOption(String option) {
		option = option.toLowerCase();
		
		if ("accept".equals(option)) {
			
			System.out.println("\nEnter the ID of the offer you'd like to accept");
			System.out.print("ID: ");
			String offerId = scan.nextLine();
			boolean accepted = offers.acceptOffer(offerId);
			
			if(accepted) {
				Offer offer = offers.getOffer(offerId);
				String username = offer.getUsername();
				Car car = lot.removeCar(offer.getCarId());
				payments.addPayment(new Payment(username,car.getId(),offer.getAmount()));
				users.transferCarToUser(car,username);
			}
			
		} else if ("add".equals(option)) {
			
			System.out.println("\nEnter the information for the car you want to add");
			System.out.print("Make: ");
			String make = scan.nextLine();
			
			System.out.print("Model: ");
			String model = scan.nextLine();
			
			System.out.print("Year: ");
			String year = scan.nextLine();
			
			System.out.print("Mileage: ");
			String mileage = scan.nextLine();
			
			lot.addCar(new Car(make, model, year, mileage));
			
		} else if ("cancel offer".equals(option)) {
			
			System.out.println("\nEnter the ID of the offer you'd like to cancel");
			System.out.print("ID: ");
			offers.cancelOffer(scan.nextLine());
						
		} else if ("exit".equals(option)) {
			
//			usersDAO.persistUserService(users);
			lotDAO.persistLot(lot);
			offersDAO.persistOfferService(offers);
			paymentsDAO.persistPaymentService(payments);
			System.out.println("\nThank you for using the car lot tool. Goodbye!");
			
		} else if ("login".equals(option)) {
			
			System.out.println("\nEnter your credentials to log in");
			User user = getUserInfo();
			user = users.authenticateUser(user);
			
			while(user == null) {
				
				System.out.println("\nEnter 'exit' to exit login, or anything else to retry.");
				System.out.print("Option: ");
				option = scan.nextLine();
				
				if("exit".equals(option)) {
					break;
				} else {
					user = getUserInfo();
					user = users.authenticateUser(user);
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
			
		} else if ("make offer".equals(option)) {
			
			System.out.println("\nEnter the car ID and the amount of the offer");
			System.out.print("Car ID: ");
			String carId = scan.nextLine();
			
			if (lot.findCar(carId) > -1) {
				
				System.out.print("Amount: ");
				Integer amount = Integer.parseInt(scan.nextLine());
				offers.makeOffer(new Offer(users.getCurrentUser().getUsername(),carId,amount));
				
			} else {
				System.out.println("\nSorry, but we could not locate a car with that ID.");
			}
						
		} else if ("my cars".equals(option)) {
			
			ArrayList<Car> myCars = ((Customer)users.getCurrentUser()).getMyCars();
			
			System.out.println("\nHere are your cars:\n");
			
			for (Car car : myCars) {
				System.out.println(car + "\n");
			}
			
		} else if ("my offers".equals(option)) {
			
			ArrayList<Offer> myOffers = offers.getUserOffers(users.getCurrentUser().getUsername());

			System.out.println("\nHere are your current offers:\n");
			
			for (Offer offer : myOffers) {
				System.out.println(offer + "\n");
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
				System.out.println("\nPAYMENT AMOUNTS MUST CONTAIN NUMBERS ONLY");;
			}
			
			
			
			
			
			
		} else if ("my payments".equals(option)) {
			
			ArrayList<Payment> userPayments = payments.getUserPayments(users.getCurrentUser().getUsername());
			
			System.out.println("\nThese are the current ongoing payments\n");
			for (Payment payment : userPayments) {
				System.out.println(payment.getRemainingPayments() + "\n");
			}
			
		} else if ("offers".equals(option)) {
			
			ArrayList<Offer> pendingOffers = offers.getPendingOffers();
			
			System.out.println("\nThese are the payments for all users\n");
			for (Offer offer: pendingOffers) {
				System.out.println(offer + "\n");
			}
			
		} else if ("payments".equals(option)) {
			
			ArrayList<Payment> allPayments = payments.getPayments();
			
			System.out.println("\nThese are all the currently ongoing payments\n");
			for (Payment payment : allPayments) {
				System.out.println(payment.getUsername() + " has " + payment.getRemainingPayments() + "\n");
			}
			
		} else if ("register".equals(option)) {
			
			System.out.println("\nEnter a username and password to register.");
			User user = getUserInfo();
			
			while(!users.registerUser(user)) {
				System.out.println("\nEnter 'exit' to exit the register page, or anything else to retry.");
				System.out.print("Option: ");
				option = scan.nextLine();
				if("exit".equals(option)) {
					break;
				} else {
					user = getUserInfo();
				}
			};
			
		} else if ("reject".equals(option)) {
			
			System.out.println("\nEnter the ID of the offer you'd like to reject");
			System.out.print("ID: ");
			offers.rejectOffer(scan.nextLine());
			
		} else if ("remove".equals(option)) {
			
			System.out.println("\nPlease enter the ID of the car you'd like to remove");
			System.out.print("ID: ");
			String id = scan.nextLine();
			
			lot.removeCar(id);
			offers.rejectOffersOfRemovedCar(id);
			
		} else {
			
			System.out.println("\nSorry, you have not entered a valid option. Please try again.");
			
		}
	}
	
	private static User getUserInfo() {
		
		User user = new User();
		
		System.out.print("Username: ");
		user.setUsername(scan.nextLine());
		
		System.out.print("Password: ");
		user.setPassword(scan.nextLine());
		
		return user;
	}
}
