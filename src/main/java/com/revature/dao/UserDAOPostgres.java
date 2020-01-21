package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.users.User;
import com.revature.util.LoggerUtil;

public class UserDAOPostgres implements UserDAO {
	
	private static LoggerUtil logger = new LoggerUtil();

	@Override
	public User authenticate(User u) {
			
		String sql = "select username, first_name, last_name, user_id, user_role_id from users where username = ? and user_password = ?";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			
			ResultSet rs = stmt.executeQuery();
			
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
				logger.warn(e.toString());
			}
		}
        
		return null;
	}
	
	@Override
	public ArrayList<ArrayList<String>> loadUserOptions(User u) {
		
		String sql = "select user_menu_option, user_menu_option_description from user_menu_options where user_role_id = ? or user_role_id is null order by option_order";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		ArrayList<ArrayList<String>> options = new ArrayList<ArrayList<String>>();
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, u.getUserRoleId());
			
			ResultSet rs = stmt.executeQuery();
			
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
				logger.warn(e.toString());
			}
		}
        
		return options;
	}

	@Override
	public void registerCustomer(User u) throws SQLException {
		
		String sql = "insert into users (username,user_password,first_name,last_name,user_role_id) values (?,?,?,?,?)";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		try {
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getFirstName());
			stmt.setString(4, u.getLastName());
			stmt.setInt(5, 0);
			
			stmt.executeUpdate();
			
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
