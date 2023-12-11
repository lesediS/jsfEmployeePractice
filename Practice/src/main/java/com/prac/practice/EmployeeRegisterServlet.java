package com.prac.practice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.prac.practice.dao.EmployeeDao;
import com.prac.practice.logics.UserAuthenticationBeanRemote;

@WebServlet(urlPatterns = "/register") 
public class EmployeeRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //private EmployeeDao employeeDao;
	
	private UserAuthenticationBeanRemote userAuthBean;
    

    public void init() {
    	userAuthBean = new UserAuthenticationBeanRemote();
    	//employeeDao = new EmployeeDao();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstname = request.getParameter("firstName");
		String lastname = request.getParameter("lastName");
		String username = request.getParameter("userName");
		String address = request.getParameter("address");
		String contactNum = request.getParameter("contact");		
		String pass = request.getParameter("password");
		
		
		/*Employee employee = new Employee();
		employee.setFirstName(firstname);
		employee.setLastName(lastname);
		employee.setUserName(username);
		employee.setAddress(address);
		employee.setContact(contactNum);
		employee.setPass_Word(pass);*/
		
		
		
		try {
			
			//employeeDao.regEmp(employee);	
			
			boolean regSuccess = userAuthBean.registerEmp(firstname, lastname, username, address, contactNum, pass);
			
			if(regSuccess) {
				System.out.println("success");
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				
				getServletContext().getRequestDispatcher("/Welcome.jsp").forward(request, response);
			} else {
				getServletContext().getRequestDispatcher("/Error.jsp").forward(request, response);
			}
			
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}	
	}

}
