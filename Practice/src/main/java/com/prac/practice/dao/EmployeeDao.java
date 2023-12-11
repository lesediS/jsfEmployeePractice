package com.prac.practice.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.prac.practice.Configuration;
import com.prac.practice.Employee;

public class EmployeeDao extends Configuration {
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public Connection getDBCon() throws ClassNotFoundException, SQLException{
		if(dataSource != null){
			return dataSource.getConnection();
		} else {
			String connString = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(connString, dbUser, dbPassword);
		}
	}
	
	public int regEmp(Employee employee) throws ClassNotFoundException {
		String createEmp = "INSERT INTO "+ Constants.EMP_TBL + " ("
		+ Constants.EMP_FIRSTNAME + ", " + Constants.EMP_LASTNAME + ", "
		+ Constants.EMP_USERNAME + ", " + Constants.EMP_ADDRESS + ", "
		+ Constants.EMP_CONTACT + ", " + Constants.EMP_PASSWORD + ") "
		+ "VALUES (?, ?, ?, ?, ?, ?)";
		
		
		
		int result = 0;
		
		try(Connection connect = getDBCon();
			
			PreparedStatement prepStat = connect.prepareStatement(createEmp, Statement.RETURN_GENERATED_KEYS)){
				
				prepStat.setString(1, employee.getFirstName());
				prepStat.setString(2, employee.getLastName());
				prepStat.setString(3, employee.getUserName());
				prepStat.setString(4, employee.getAddress());
				prepStat.setString(5, employee.getContact());
				prepStat.setString(6, employee.getPass_Word());
				
				result = prepStat.executeUpdate();
				try (ResultSet generatedKeys = prepStat.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                int generatedId = generatedKeys.getInt(1);
		                System.out.println("Generated ID: " + generatedId);
		            } else {
		                throw new SQLException("Failed to retrieve auto-generated key.");
		            }
		        }
				System.out.println("yay");
			} catch (SQLException ex) {
				System.out.println("sql exception" + ex.getMessage());
			}
			return result;
	}

	public boolean verifyUser(String username, String password) throws ClassNotFoundException {
		try {
			Connection conn = getDBCon();
	        String query = "SELECT * FROM " + Constants.EMP_TBL + " WHERE " + Constants.EMP_USERNAME + " = ?";
	        PreparedStatement preparedStatement = conn.prepareStatement(query);
	        preparedStatement.setString(1, username);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        
	        if(resultSet.next()) {
	        	String storedPass = resultSet.getString(Constants.EMP_PASSWORD);
	        	String hashedPass = Employee.hashPass(password);
	        	
	        	if(storedPass.equals(hashedPass)) {
	        		return true;
	        	}
	        }
		} catch(SQLException e) {
			System.err.println("verifyUser error " + e.getMessage());
		}
		return false;
	}
}
