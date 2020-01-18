package com.revature.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.users.Customer;
import com.revature.users.Employee;
import com.revature.users.User;
import com.revature.users.UserService;
import com.revature.util.LoggerUtil;

public class UserDAOPostgres implements UserDAO {
	
	private static LoggerUtil logger = new LoggerUtil();

	@Override
	public User authenticate(User u) {
			
		String sql_get = "select username,first_name,last_name,user_id,user_role_id from users where username = '" + u.getUsername() + "' and user_password = '" + u.getPassword() + "'";
		Connection conn = ConnectionFactory.getConnection();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql_get);
			
			System.out.println(rs);
			
			if(rs.next()) {
				String username = rs.getString("username");
		        String firstName = rs.getString("first_name");
		        String lastName = rs.getString("last_name");
		        int userId = rs.getInt("user_id");
		        int userRoleId = rs.getInt("user_role_id");
		        
		        if (userRoleId == 0) {
		        	Customer customer = new Customer();
		        	customer.setUsername(username);
		        	return customer;
		        } else if (userRoleId == 1) {
		        	Employee employee = new Employee();
		        	employee.setUsername(username);
		        	return employee;
		        }
			}
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public void registerCustomer(User u) throws SQLException {
		String sql = "insert into users (username,user_password,first_name,last_name,user_role_id) values ('" + u.getUsername() + "','" + u.getPassword() + "','" + u.getFirstName() + "','" + u.getLastName() + "',0)";
		Connection conn = ConnectionFactory.getConnection();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			int rowsUpdated = stmt.executeUpdate(sql);
			
			System.out.println("rows updated: " + rowsUpdated);
			
	        if(rowsUpdated == 0) {
	        	System.out.println("error registering user");
	        }
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn(e.toString());
			}
		}
        
	}

}
