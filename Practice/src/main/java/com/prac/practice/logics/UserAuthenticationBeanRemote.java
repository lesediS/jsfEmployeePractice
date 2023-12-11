package com.prac.practice.logics;

import com.prac.practice.Configuration;
import com.prac.practice.Employee;
import com.prac.practice.dao.Constants;

import jakarta.ejb.Stateless;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.jakartaee.bcel.Const;

/**
 * Session Bean implementation class UserAuthenticationBeanRemote
 */
@Stateless
public class UserAuthenticationBeanRemote extends Configuration {

    public UserAuthenticationBeanRemote() {
        // TODO Auto-generated constructor stub
    }

    public boolean registerEmp(String firstname, String lastname, String username, String address, String contactNum, String pass) {
        // Check if the user already exists
        Employee existsEmployee = findEmployeeByUsername(username);

        if (existsEmployee != null) {
            System.out.println("User already exists, registration failed");
            return false;
        }

        // Create a new employee
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(firstname);
        newEmployee.setLastName(lastname);
        newEmployee.setUserName(username);
        newEmployee.setAddress(address);
        newEmployee.setContact(contactNum);
        newEmployee.setPass_Word(pass);

        // Persist the new employee
        if (insertEmployee(newEmployee)) {
            System.out.println("User registration successful");
            return true;
        } else {
            System.out.println("User registration failed");
            return false;
        }
    }

    public Employee loginUser(String username, String password) {
        String loginUsername = username.trim();
        String loginPass = password.trim();

        if (loginUsername.isEmpty() || loginPass.isEmpty()) {
            System.out.println("Both fields must be filled");
            return null;
        }

        try {
            Employee loggedInEmp = verify(username, password);

            if (loggedInEmp != null) {
                System.out.println("Logged in");
                return loggedInEmp;
            } else {
                System.out.println("Credentials not correct");
                return null;
            }
        } catch (Exception e) {
            System.out.println("loginUser error : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Employee verify(String username, String password) {
        Employee employee = findEmployeeByUsername(username);

        if (employee != null && employee.getPass_Word().equals(password)) {
            return employee;
        }

        return null;
    }

    private Employee findEmployeeByUsername(String username) {
        // Use JDBC to find the employee by username
        String query = "SELECT * FROM employee WHERE userName = ?";
        try (Connection connection = getDBCon();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Employee(
                            resultSet.getInt("id"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("userName"),
                            resultSet.getString("address"),
                            resultSet.getString("contact"),
                            resultSet.getString("password")
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("findEmployeeByUsername error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private boolean insertEmployee(Employee employee) {
        // Use JDBC to insert the new employee
        String insertQuery = "INSERT INTO " + Constants.EMP_TBL + " (" + 
        Constants.EMP_FIRSTNAME + ", " + Constants.EMP_LASTNAME + ", " 
        + Constants.EMP_USERNAME + ", " + Constants.EMP_ADDRESS + ", "
        + Constants.EMP_CONTACT + ", " + Constants.EMP_PASSWORD + ") " + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = getDBCon();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getUserName());
            preparedStatement.setString(4, employee.getAddress());
            preparedStatement.setString(5, employee.getContact());
            preparedStatement.setString(6, employee.getPass_Word());

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("insertEmployee error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Connection getDBCon() throws ClassNotFoundException, SQLException {
        // Your existing code for obtaining a database connection
        String connString = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(connString, dbUser, dbPassword);
    }
}
