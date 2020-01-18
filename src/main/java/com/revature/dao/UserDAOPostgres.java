package com.revature.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.users.Customer;
import com.revature.users.Employee;
import com.revature.users.User;
import com.revature.users.UserService;

public class UserDAOPostgres implements UserDAO {

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

	@Override
	public User authenticate(User u) {
			
		String sql_get = "select username,first_name,last_name,user_id,user_role_id from users where username = '" + u.getUsername() + "' and user_password = '" + u.getPassword() + "'";
		Connection conn = ConnectionFactory.getConnection();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql_get);
			
			rs.next();
				
	        String username = rs.getString("username");
	        String firstName = rs.getString("first_name");
	        String lastName = rs.getString("last_name");
	        int userId = rs.getInt("user_id");
	        int userRoleId = rs.getInt("user_role_id");
	        
	        if (userRoleId == 1) {
	        	Customer customer = new Customer();
	        	customer.setUsername(username);
	        	return customer;
	        } else if (userRoleId == 2) {
	        	Employee employee = new Employee();
	        	employee.setUsername(username);
	        	return employee;
	        }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
		return null;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerUser(User user) {
		// TODO Auto-generated method stub
		
	}

}
