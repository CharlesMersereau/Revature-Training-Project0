package com.revature.driver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.revature.cars.CarService;
import com.revature.dao.CarDAO;
import com.revature.dao.CarDAOPostgres;
import com.revature.dao.OfferDAO;
import com.revature.dao.OfferDAOPostgres;
import com.revature.dao.PaymentDAO;
import com.revature.dao.PaymentDAOPostgres;
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
	private static OfferDAO offersDAO = new OfferDAOPostgres();
	private static OfferService offers = new OfferService();
	private static PaymentDAO paymentsDAO = new PaymentDAOPostgres();;
	private static PaymentService payments = new PaymentService(); 
	

	public static void main(String[] args) {
		
		String option = "";
	
		logger.info("System booted up");

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
					System.out.println("\nInvalid input".toUpperCase());
					option = "try again";
				} catch (Exception e) {
					// hopefully this doesn't happen
				}
				
			} while ("try again".equals(option));
			
			handleOption(option);
			
		} while(!"exit".equals(option));
		
	}
	
	private static void displayOptions() {
		
		System.out.println(bannerCreator("Select an option from the list below") + "\n");
		
		if (users.getCurrentUser() == null) {
			System.out.println("[1] Log into your account");
			System.out.println("[2] Register new user");
			System.out.println("[3] Exit the car lot app");
		} else {
			users.displayOptions();
		}
		System.out.print("\nOption: ");
		
	}
	
	private static void handleOption(String option) {
		option = option.toLowerCase();
		
		if ("accept offer".equals(option)) {
			
			ArrayList<Offer> pendingOffers = new ArrayList<Offer>();
			
			try {
				pendingOffers = offers.getPendingOffers();
			} catch (SQLException e) {
				System.out.println("There was an error loading the currently pending offers".toUpperCase());
			}
			
			if (pendingOffers.isEmpty()) {
				System.out.println("There are no currently pending offers".toUpperCase());
			} else {
				
				System.out.println(bannerCreator("Currently pending offers"));
				int counter = 0;
				Car car;
				User user;
				
				for (Offer offer: pendingOffers) {
					car = offer.getCar();
					user = offer.getUser();
					System.out.println(String.format("\n[%d] $%,d by %s %s for %d %s %s listed at $%,d", ++counter, offer.getAmount(), user.getFirstName(), user.getLastName(), car.getYear(), car.getMake(), car.getModel(), car.getPrice()));
				}
				
				Integer offerNumber = 0;
				Offer offer = null;
				
				do {
					
					System.out.println("\nWhich offer would you like to accept? (enter 0 to cancel)");
					System.out.print("Offer number: ");
					
					try {
						
						offerNumber = Integer.parseInt(scan.nextLine());
						
						if (offerNumber < 0 || offerNumber > pendingOffers.size()) {
							System.out.println("\nInvalid option".toUpperCase());
							option = "try again";
						} else if (offerNumber > 0) {
							offer = pendingOffers.get(offerNumber - 1);
							option = "";
						}
						
					} catch (NumberFormatException e) {
						System.out.println("\nInvalid input".toUpperCase());
						offerNumber = -1;
						option = "try again";
					}
					
				} while("try again".equals(option) && offerNumber != 0 );
					
				if (offerNumber != 0) {
					try {
						offers.acceptOffer(offer);
						System.out.println("\nOffer succesfully accepted!");
					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("\nThere was an error accepting the offer".toUpperCase());
					}
				}
			}
			
		} else if ("add car".equals(option)) {
			
			Car car = new Car();
			
			System.out.println(bannerCreator("Add a car"));
			
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
			
			ArrayList<Offer> userOffers = new ArrayList<Offer>();
			
			try {
				userOffers = offers.getUserOffers(users.getCurrentUser().getUserId());
			} catch (SQLException e) {
				System.out.println("\nThere was an error loading your offers".toUpperCase());
			}
			
			if (userOffers.isEmpty()) {
				System.out.println("\nYou have no offers".toUpperCase());
			} else {
				
				System.out.println(bannerCreator("Your current offers"));
				int counter = 0;
				Car car;
				
				for (Offer offer: userOffers) {
					car = offer.getCar();
					System.out.println(String.format("\n[%d] Status: %s, $%,d for %d %s %s listed at $%,d", ++counter, offer.getStatus().toUpperCase(), offer.getAmount(), car.getYear(), car.getMake().toUpperCase(), car.getModel().toUpperCase(), car.getPrice()));
				}
				
				Integer offerNumber = 0;
				Offer offer = null;
				
				do {
					
					System.out.println("\nWhich offer would you like to cancel?");
					System.out.println("(an accepted offer cannot be cancelled, enter 0 to exit)");
					System.out.print("Offer number: ");
					
					try {
						
						offerNumber = Integer.parseInt(scan.nextLine());
						
						if (offerNumber < 0 || offerNumber > userOffers.size()) {
							System.out.println("\nInvalid option".toUpperCase());
							option = "try again";
						} else if (offerNumber > 0) {
							offer = userOffers.get(offerNumber - 1);
							option = "";
							
							if (offer.getStatus().toUpperCase().equals("accepted".toUpperCase())) {
								System.out.println("\nAn accepted offer cannot be cancelled".toUpperCase());
								option = "try again";
							}
						} 
						
					} catch (NumberFormatException e) {
						System.out.println("\nInvalid input".toUpperCase());
						offerNumber = -1;
						option = "try again";
					}
					
				} while("try again".equals(option) && offerNumber != 0 );
					
				if (offerNumber != 0) {
					try {
						offer.setUser(users.getCurrentUser());
						offers.cancelOffer(offer);
						System.out.println("\nOffer succesfully cancelled!");
					} catch (SQLException e) {
						System.out.println("\nThere was an error canceling the offer".toUpperCase());
					}
				}
			}
			
						
		} else if ("exit".equals(option)) {
			
			System.out.println("\nThank you for using the car lot tool. Goodbye!");
			
		} else if ("login".equals(option)) {
			
			User user;
			
			System.out.println(bannerCreator("login"));
			
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
			offer.setUser(users.getCurrentUser());

			ArrayList<Car> available = new ArrayList<Car>();
			
			try {
				available = cars.getCars();
			} catch (SQLException e) {
				// set flag to reload
			}

			if (available.isEmpty()) {
				System.out.println("\nThere are no vehicles currently available".toUpperCase());
			} else {
				System.out.println(bannerCreator("vehicles available for offer"));
				int counter = 0;
				for (Car car : available) {
					System.out.println(String.format("\n[%d] %d %s %s with %,d miles for $%,d", ++counter, car.getYear(), car.getMake().toUpperCase(), car.getModel().toUpperCase(), car.getMileage(), car.getPrice()));
				}
			}
			
			Integer carNumber;
			Car car = new Car();
			
			do {
				
				System.out.println("\nWhich car would you like to make an offer on?");
				System.out.print("Car number: ");
				
				try {
					
					carNumber = Integer.parseInt(scan.nextLine());
					
					if (carNumber < 1 || carNumber > available.size()) {
						System.out.println("\nInvalid option".toUpperCase());
						option = "try again";
					} else {
						car = available.get(carNumber - 1);
						offer.setCar(car);
						option = "";
					}
					
				} catch (NumberFormatException e) {
					System.out.println("\nInvalid input".toUpperCase());
					option = "try again";
				}
				
			} while("try again".equals(option));
			
			do {
				
				try {
					
					System.out.print("\nOffer mount: ");
					Integer amount = Integer.parseInt(scan.nextLine());
					offer.setAmount(amount);
					option = "";
					
				} catch (NumberFormatException e) {
					System.out.println("\nInvalid input".toUpperCase());
					option = "try again";
				}
				
			} while("try again".equals(option));
			
			do {
				
				try {
					
					System.out.print("\nNumber of months: ");
					Integer numberOfMonths = Integer.parseInt(scan.nextLine());
					offer.setNumberOfMonths(numberOfMonths);
					option = "";
					
				} catch (NumberFormatException e) {
					System.out.println("\nInvalid input".toUpperCase());
					option = "try again";
				}
				
			} while("try again".equals(option));
			
			try {
				offers.makeOffer(offer);
				System.out.println("\nOffer succesfully submitted!");
			} catch (SQLException e) {
				System.out.println("There was an error submitting your offer".toUpperCase());
			}
			
						
		} else if ("make payment".equals(option)) {
			
			ArrayList<Car> myCars;
			
			try {
				
				myCars = cars.loadUserCars(users.getCurrentUser().getUserId());
				
				if (myCars.isEmpty()) {
					System.out.println("\nYou have not bought any cars".toUpperCase());
				} else {
					System.out.println(bannerCreator("Here are your cars"));
					int counter = 0;
					for (Car car : myCars) {
						System.out.println(String.format("\n[%d] %d %s %s",++counter, car.getYear(), car.getMake().toUpperCase(), car.getModel().toUpperCase()));
					}
				}
				
				Integer carNumber = 0;
				Car car = new Car();
				
				do {
					
					System.out.println("\nWhich car would you like to make a payment on? (enter 0 to cancel)");
					System.out.print("Car number: ");
					
					try {
						
						carNumber = Integer.parseInt(scan.nextLine());
						
						if (carNumber < 0 || carNumber > myCars.size()) {
							System.out.println("\nInvalid option".toUpperCase());
							option = "try again";
						} else if (carNumber > 0) {
							car = myCars.get(carNumber - 1);
							option = "";
							if (car.getPaidOff()) {
								System.out.println("\nThe car you have chosen is already paid off".toUpperCase());
								option = "try again";
							}
						}
						
					} catch (NumberFormatException e) {
						System.out.println("\nInvalid input".toUpperCase());
						carNumber = -1;
						option = "try again";
					}
					
				} while("try again".equals(option) && carNumber != 0);
				
				
				if (carNumber != 0) {
					do {
						
						System.out.print("\nAmount of payment: ");
						Float amount = (float)0;
						
						try {
							
							amount = Float.parseFloat(scan.nextLine());
							
							if (amount > 0) {
								
								Payment payment = new Payment();
								payment.setAmount(amount);
								payment.setUser(users.getCurrentUser());
								payment.setCar(car);
								
								try {
									payments.makePayment(payment);
									System.out.println("\nPayment succesful!");
								} catch (SQLException e) {
									System.out.println("\nThere was an error making your payment".toUpperCase());
								}
								
								option = "";
								
							} else {
								System.out.println("\nPayments must be greater than 0");
								option = "try again";
							}
							
						} catch (NumberFormatException e) {
							System.out.println("\nPAYMENT AMOUNTS MUST CONTAIN NUMBERS ONLY".toUpperCase());
							option = "try again";
						}
					} while ("try again".equals(option));
				}
				
			} catch (SQLException e) {
				System.out.println("\nThere was an error loading your cars".toUpperCase());
			}
			
		} else if ("my cars".equals(option)) {
			
			ArrayList<Car> myCars;
			
			try {
				
				myCars = cars.loadUserCars(users.getCurrentUser().getUserId());
				
				if (myCars.isEmpty()) {
					System.out.println("\nYou have not bought any cars".toUpperCase());
				} else {
					System.out.println(bannerCreator("Your cars"));
					
					for (Car car : myCars) {
						System.out.println(String.format("\n%d %s %s", car.getYear(), car.getMake().toUpperCase(), car.getModel().toUpperCase()));
					}
				}
				
			} catch (SQLException e) {
				System.out.println("\nThere was an error loading your cars".toUpperCase());
			}
			
		} else if ("my offers".equals(option)) {
			
			ArrayList<Offer> myOffers = new ArrayList<Offer>();
			
			try {
				myOffers = offers.getUserOffers(users.getCurrentUser().getUserId());
			} catch (SQLException e) {
				System.out.println("\nThere was an error retrieving your offers");
			}

			if (myOffers.isEmpty()) {
				System.out.println("\nYou have no offers".toUpperCase());
			} else {
				System.out.println(bannerCreator("Your current offers"));
				
				Car car;
				for (Offer offer : myOffers) {
					car = offer.getCar();
					System.out.println(String.format("\nStatus: %s, $%,d for %d %s %s listed at $%,d", offer.getStatus().toUpperCase(), offer.getAmount(), car.getYear(), car.getMake().toUpperCase(), car.getModel().toUpperCase(), car.getPrice()));
					
				}
			}
			
		} else if ("my payments".equals(option)) {
			
			ArrayList<Payment> userPayments = new ArrayList<Payment>();
			ArrayList<Car> myCars = new ArrayList<Car>();
			
			try {
				myCars = cars.loadUserCars(users.getCurrentUser().getUserId());
			} catch (SQLException e) {
				System.out.println("There was an error loading your cars to pay on".toUpperCase());
			}
			
			if (myCars.isEmpty()) {
				System.out.println("\nYou have no cars to make payments on".toUpperCase());
			} else {
				
				try {
					
					System.out.println(bannerCreator("Your cars"));
					
					userPayments = payments.getRemainingPayments(users.getCurrentUser());
					 
					Map<Integer,ArrayList<Float>> sums = new HashMap<Integer,ArrayList<Float>>();
					
					for (Payment payment : userPayments) {
						
						if (sums.containsKey(payment.getCar().getId())) {
							
							ArrayList<Float> current = sums.get(payment.getCar().getId());
							Float currentSum = current.get(0);
							Float currentMonths = current.get(1);
							current.clear();
							
							currentSum += payment.getAmount();
							currentMonths+= (float)1;
							current.add(currentSum);
							current.add(currentMonths);
							sums.replace(payment.getCar().getId(), current);
							
						} else {
							ArrayList<Float> newSums = new ArrayList<Float>();
							newSums.add(payment.getAmount());
							newSums.add((float)1);
							sums.put(payment.getCar().getId(), newSums);
						}
						
					}
						
					for (Car car : myCars) {
						
						if (car.getPaidOff()) {
							System.out.println(String.format("\n%d %s %s is paid off!", car.getYear(), car.getMake(), car.getModel()));
						} else {

							float remainingAmount = (float)car.getPurchaseAmount();
							float remainingMonths = car.getNumberOfMonths();
							
							if (sums.containsKey(car.getId())) {
								remainingAmount -= sums.get(car.getId()).get(0);
								remainingMonths -= sums.get(car.getId()).get(1);
							}
							
							float monthly = remainingAmount / remainingMonths;
							
							System.out.println(String.format("\n%d payments of $%,.2f remaining on %d %s %s TOTAL: $%,.2f", (int)remainingMonths, monthly, car.getYear(), car.getMake(), car.getModel(), remainingAmount));
						
						}
						
					}
					
				} catch (SQLException e) {
					System.out.println("\nThere was an error loading your payments".toUpperCase());
				}
			}
			
		} else if ("register".equals(option)) {
			
			User customer;
			
			System.out.println(bannerCreator("Register an account"));
			
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
			
			ArrayList<Offer> pendingOffers = new ArrayList<Offer>();
			
			try {
				pendingOffers = offers.getPendingOffers();
			} catch (SQLException e) {
				System.out.println("There was an error loading the currently pending offers".toUpperCase());
			}
			
			if (pendingOffers.isEmpty()) {
				System.out.println("There are no currently pending offers".toUpperCase());
			} else {
				
				System.out.println(bannerCreator("Currently pending offers"));
				int counter = 0;
				Car car;
				User user;
				
				for (Offer offer: pendingOffers) {
					car = offer.getCar();
					user = offer.getUser();
					System.out.println(String.format("\n[%d] $%,d by %s %s for %d %s %s listed at $%,d", ++counter, offer.getAmount(), user.getFirstName(), user.getLastName(), car.getYear(), car.getMake(), car.getModel(), car.getPrice()));
				}
				
				Integer offerNumber = 0;
				Offer offer = null;
				
				do {
					
					System.out.println("\nWhich offer would you like to reject? (enter 0 to cancel)");
					System.out.print("Offer number: ");
					
					try {
						
						offerNumber = Integer.parseInt(scan.nextLine());
						
						if (offerNumber < 0 || offerNumber > pendingOffers.size()) {
							System.out.println("\nInvalid option".toUpperCase());
							option = "try again";
						} else if (offerNumber > 0) {
							offer = pendingOffers.get(offerNumber - 1);
							option = "";
						}
						
					} catch (NumberFormatException e) {
						System.out.println("\nInvalid input".toUpperCase());
						offerNumber = -1;
						option = "try again";
					}
					
				} while("try again".equals(option) && offerNumber != 0 );
					
				if (offerNumber != 0) {
					try {
						offers.rejectOffer(offer);
						System.out.println("\nOffer succesfully rejected!");
					} catch (SQLException e) {
						System.out.println("\nThere was an error rejecting the offer".toUpperCase());
					}
				}
			}
			
		} else if ("remove car".equals(option)) {

			ArrayList<Car> available = new ArrayList<Car>();
			
			try {
				available = cars.getCars();
			} catch (SQLException e) {
				// set flag to reload
			}

			if (available.isEmpty()) {
				System.out.println("There are no vehicles currently available".toUpperCase());
			} else {
				System.out.println(bannerCreator("Available vehicles"));
				int counter = 0;
				for (Car car : available) {
					System.out.println(String.format("\n[%d] %d %s %s with %,d miles for $%,d", ++counter, car.getYear(), car.getMake().toUpperCase(), car.getModel().toUpperCase(), car.getMileage(), car.getPrice()));				
				}
			}
			
			Integer carNumber = 0;
			Car car = new Car();
			
			do {
				
				System.out.println("\nWhich car would you like to remove? (enter 0 to cancel)");
				System.out.print("Car number: ");
				
				try {
					
					carNumber = Integer.parseInt(scan.nextLine());
					
					if (carNumber < 0 || carNumber > available.size()) {
						System.out.println("\nInvalid option".toUpperCase());
						option = "try again";
					} else if (carNumber > 0) {
						car = available.get(carNumber - 1);
						option = "";
					}
					
				} catch (NumberFormatException e) {
					System.out.println("\nInvalid input".toUpperCase());
					carNumber = -1;
					option = "try again";
				}
				
			} while("try again".equals(option) && carNumber != 0);
			
			if (carNumber != 0) {
				
				try {
					
					cars.removeCar(car);
					
					System.out.println("\nCar succesfully removed");
					
				} catch (SQLException e) {
					System.out.println("There was an error removing the car from the database".toUpperCase());
				}
				
			}
			
		} else if ("show lot".equals(option)) {
			
			ArrayList<Car> available = new ArrayList<Car>();
			
			try {
				available = cars.getCars();
			} catch (SQLException e) {
				// set flag to reload
			}

			if (available.isEmpty()) {
				System.out.println("There are no vehicles currently available".toUpperCase());
			} else {
				System.out.println(bannerCreator("Available vehicles"));
				for (Car car : available) {
					System.out.println(String.format("\n%d %s %s with %,d miles for $%,d", car.getYear(), car.getMake().toUpperCase(), car.getModel().toUpperCase(), car.getMileage(), car.getPrice()));				
				}
			}
			
		} else if ("view offers".equals(option)) {
			
			ArrayList<Offer> pendingOffers = new ArrayList<Offer>();
			
			try {
				pendingOffers = offers.getPendingOffers();
			} catch (SQLException e) {
				System.out.println("\nThere was an error loading offers".toUpperCase());
			}
			
			if (pendingOffers.isEmpty()) {
				System.out.println("\nThere are no currently pending offers".toUpperCase());
			} else {
				System.out.println(bannerCreator("Currently pending offers"));
				int counter = 0;
				Car car;
				User user;
				for (Offer offer: pendingOffers) {
					car = offer.getCar();
					user = offer.getUser();
					System.out.println(String.format("\n[%d] $%,d by %s %s for %d %s %s listed at $%,d", ++counter, offer.getAmount(), user.getFirstName().toUpperCase(), user.getLastName().toUpperCase(), car.getYear(), car.getMake().toUpperCase(), car.getModel().toUpperCase(), car.getPrice()));
				}
			}
			
		} else if ("view payments".equals(option)) {
			
			ArrayList<Payment> allPayments;
			
			try {
				allPayments = payments.getPayments();
				
				System.out.println(bannerCreator("User payments"));
				
				for (Payment payment : allPayments) {
					User user = payment.getUser();
					Car car = payment.getCar();
					System.out.println(String.format("\n%s %s paid $%,.2f for %d %s %s", user.getFirstName().toUpperCase(), user.getLastName().toUpperCase(), payment.getAmount(), car.getYear(), car.getMake().toUpperCase(), car.getModel().toUpperCase()));
				}
				
			} catch (SQLException e) {
				System.out.println("Error loading payments".toUpperCase());
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
		
		String string = "\n" + line + "\n\n" + s.toUpperCase() + "\n" + line;
		
		return string;
	}
	
}
