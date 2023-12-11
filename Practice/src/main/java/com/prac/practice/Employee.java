package com.prac.practice;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Employee extends Configuration{
	private int ID;
	private String FirstName;
	private String LastName;
	private String UserName;
	private String Address;
	private String Contact;
	private String Pass_Word;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getPass_Word() {
		return Pass_Word;
	}
	public void setPass_Word(String pass_Word) {
		Pass_Word = pass_Word;
	}
		
	public Employee() {}
	public Employee(int iD, String firstName, String lastName, String userName, String address, String contact,
			String pass_Word) {
		this.ID = iD;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.UserName = userName;
		this.Address = address;
		this.Contact = contact;
		this.Pass_Word = pass_Word;
	}
	
	public static String hashPass(String password) {
		try {
			MessageDigest mdigest = MessageDigest.getInstance("SHA-1");
			byte[] hashedPass = mdigest.digest(password.getBytes());
			StringBuilder hexString = new StringBuilder();
			
			for(byte by : hashedPass) {
				hexString.append(String.format("%02x", by));
			}
			
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("hashPass error : " + e.getMessage());
			return null;
		}
	}
	
	public boolean verifyPass(Employee user) {
		String storedHashPass = getHashedPassFromDB(user.getUserName());
		
		if(storedHashPass == null) {
			JOptionPane.showMessageDialog(null, "Invalid hashed password");
			return false;
		}
		
		String enteredHashPass = hashPass(user.getPass_Word());
		
		return storedHashPass.equals(enteredHashPass);
	}


	private String getHashedPassFromDB(String userName2) {
		String dbURL = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
		String dbUser = "root";
		String dbPassword = "password123?!";
		
		try(Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
				PreparedStatement preStatement = con.prepareStatement("SELECT password FROM user WHERE username = ?")){
			preStatement.setString(1, userName2);
			
			try(ResultSet resSet = preStatement.executeQuery()){
				if(resSet.next()) {
					return resSet.getString("password");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
}
