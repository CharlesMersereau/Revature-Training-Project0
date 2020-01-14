package com.revature.lot;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.revature.pojo.Car;

public class LotTest {
	
	private static Lot lot;
	
	@Before
	public void setUp() {
		lot = new Lot();
	}

	@Test
	public void addCar() {
		Car newCar = new Car("ford","fiesta");
		lot.addCar(newCar);
		ArrayList<Car> cars = lot.getCars();
		assertTrue(cars.get(0).getMake().equals(newCar.getMake()));
	}
	
	@Test
	public void addAnotherCarOfSameMakeAndModel() {
		ArrayList<Car> cars = lot.getCars();
		assertEquals(0, cars.size());
		Car newCar1 = new Car("toyota","camry");
		Car newCar2 = new Car("toyota","camry");
		lot.addCar(newCar1);
		lot.addCar(newCar2);
		cars = lot.getCars();
		assertTrue(cars.get(0).getModel().equals(cars.get(1).getModel()));
	}
	
	@Test
	public void testCreateCarId() {
		Car car = new Car("honda","civic","2003","143,000");
		String id = lot.createCarId(car);
		assertTrue("HC0".contentEquals(id));
	}
	
	@Test
	public void carsOfSameMakeAndModelHaveDifferentIdm() {
		ArrayList<Car> cars = lot.getCars();
		assertEquals(0, cars.size());
		Car newCar1 = new Car("toyota","camry");
		Car newCar2 = new Car("toyota","camry");
		lot.addCar(newCar1);
		lot.addCar(newCar2);
		cars = lot.getCars();
		assertFalse(cars.get(0).getId().equals(cars.get(1).getId()));
	}
	
	@Test
	public void removeCarByObject() {
		Car newCar = new Car("ford","mustang");
		lot.addCar(newCar);
		lot.removeCar(newCar);
		ArrayList<Car> cars = lot.getCars();
		assertEquals(0,cars.size());
	}
	
	@Test
	public void removeCarByObjecThatIsNotThere() {
		
	}
	
	@Test
	public void removeCarById() {
		Car newCar = new Car("ford","mustang");
		lot.addCar(newCar);
		lot.removeCar("FM0");
		ArrayList<Car> cars = lot.getCars();
		assertEquals(0,cars.size());
	}

}
