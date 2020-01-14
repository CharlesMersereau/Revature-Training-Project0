package com.revature.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.revature.users.UserService;

public class UserServiceDAOSerialization implements UserServiceDAO {

	public void persistUserService(UserService userService) {

		String filename = "UserServiceDatabase.dat";
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(userService);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}

	public UserService readUserService() {
		
		String filename = "UserServiceDatabase.dat";

		UserService users = null;
		
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try  {
			fis = new FileInputStream(filename);
			ois = new ObjectInputStream(fis);
			users = (UserService) ois.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return users;
	}

}
