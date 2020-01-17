package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.users.Customer;
import com.revature.users.Employee;
import com.revature.users.User;

public class DBService {
	
	private static final String URL = "";
	private static final String USERNAME= "";
	private static final String PASSWORD="";
	
	public User login(String username, String password) {
		
		try (Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD)) {
			
			String sql_get = "select username,first_name,last_name,user_id,user_role_id from users where username = '" + username + "' and user_password = '" + password + "'";
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql_get);
			
		    while (rs.next()) {
		        String username_ = rs.getString("username");
		        String firstName = rs.getString("first_name");
		        String lastName = rs.getString("last_name");
		        int userId = rs.getInt("user_id");
		        int userRoleId = rs.getInt("user_role_id");
		        System.out.println(firstName + " " + lastName);
		        if (userRoleId == 1) {
		        	Customer c = new Customer();
		        	c.setUsername(username_);
		        	return c;
		        } else if (userRoleId == 2) {
		        	Employee e = new Employee();
		        	e.setUsername(username_);
		        	return e;
		        }
		    }
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
	
}
