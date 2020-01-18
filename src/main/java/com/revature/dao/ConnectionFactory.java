package com.revature.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.revature.users.Customer;
import com.revature.users.Employee;
import com.revature.users.User;

public class ConnectionFactory {
	
	private static final String PROPERTIES_FILE = "src/main/resources/database.properties";
	private static String url = "";
	private static String username= "";
	private static String password="";
	private static ConnectionFactory cf;
	
	private ConnectionFactory() {
		
		Properties prop = new Properties();
		
		try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)){

			prop.load(fis);
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() {

		if (cf == null) {
			cf = new ConnectionFactory();
		}

		return cf.createConnection();

	}
	
	private Connection createConnection() {

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;

	}
	
}
