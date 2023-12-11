package com.prac.practice.logics.servlets;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import javax.inject.Inject;

import com.prac.practice.Employee;
import com.prac.practice.logics.UserAuthenticationBeanRemote;

@WebServlet(urlPatterns = "/login") 
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserAuthenticationBeanRemote userAuthBean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/views/Login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String pass = request.getParameter("password");
		
		try {
			
			Employee empLoggedIn = userAuthBean.loginUser(username, pass);
			System.out.println("success");
			
			if(empLoggedIn != null) {
				HttpSession session = request.getSession();
				session.setAttribute("username", empLoggedIn);
				
				getServletContext().getRequestDispatcher("/views/Welcome.jsp").forward(request, response);
			} else {
				System.out.println("error logging in -> if statement doPost");
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
	}

}
