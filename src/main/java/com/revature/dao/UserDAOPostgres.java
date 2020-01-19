package com.revature.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.users.User;
import com.revature.util.LoggerUtil;

public class UserDAOPostgres implements UserDAO {
	
	private static LoggerUtil logger = new LoggerUtil();

	@Override
	public User authenticate(User u) {
			
		String sql = "select username,first_name,last_name,user_id,user_role_id from users where username = '" + u.getUsername() + "' and user_password = '" + u.getPassword() + "'";
		Connection conn = ConnectionFactory.getConnection();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
		        
		        User user = new User();
		      
		        user.setUsername(rs.getString("username"));
	        	user.setFirstName(rs.getString("first_name"));
	        	user.setLastName(rs.getString("last_name"));
	        	user.setUserId(rs.getInt("user_id"));
	        	user.setUserRoleId(rs.getInt("user_role_id"));

	        	return user;
	        	
			}
	        
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        
		return null;
	}
	
	@Override
	public ArrayList<ArrayList<String>> loadUserOptions(User u) {
		String sql = "select user_menu_option, user_menu_option_description from user_menu_options where user_role_id = " + u.getUserRoleId() + " or user_role_id is null order by option_order";
		Connection conn = ConnectionFactory.getConnection();
		Statement stmt;
		ArrayList<ArrayList<String>> options = new ArrayList<ArrayList<String>>();
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				ArrayList<String> option = new ArrayList<String>();
				
				option.add(rs.getString("user_menu_option"));
				option.add(rs.getString("user_menu_option_description"));
		        
				options.add(option);
			}
	        
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        
		return options;
	}

	@Override
	public void registerCustomer(User u) throws SQLException {
		String sql = "insert into users (username,user_password,first_name,last_name,user_role_id) values ('" + u.getUsername() + "','" + u.getPassword() + "','" + u.getFirstName() + "','" + u.getLastName() + "',0)";
		Connection conn = ConnectionFactory.getConnection();
		Statement stmt;
		
		try {
			
			stmt = conn.createStatement();
			
			stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.toString());
			}
		}
        
	}

}
