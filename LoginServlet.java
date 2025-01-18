package deduplicatable.data.auditing.mechanism.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import deduplicatable.data.auditing.mechanism.dao.SecurityDAO;
import deduplicatable.data.auditing.mechanismbean.Bean;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Bean b = new Bean();
		String mail =  request.getParameter("email");
		String password = request.getParameter("password");
		
		int uid =0;
		String uname=null;
		String email=null;
		int tpaid = 0;
		
		if(mail.equalsIgnoreCase("sm@gmail.com")&&password.equalsIgnoreCase("sm")) 
		{
			RequestDispatcher rd= request.getRequestDispatcher("systemManagerHome.jsp?status=Welcome System Manager");
			rd.include(request, response);
		}
		else if(mail.equalsIgnoreCase("csp@gmail.com")&&password.equalsIgnoreCase("csp")) 
		{
			RequestDispatcher rd= request.getRequestDispatcher("serviceProviderHome.jsp?status=Welcome Service Provider");
			rd.include(request, response);
		}
		else 
		{
			b.setEmail(mail);
			b.setPassword(password);
			try {
			ArrayList<Bean> al = new SecurityDAO().userLogin(b);
			System.out.println("Array--->"+al);
			for(Bean login : al) 
			{
				uid = login.getUid();
				uname = login.getName();
				email = login.getEmail();
				tpaid = login.getTpaid();
			}
			if(!al.isEmpty()) 
			{
				HttpSession ses = request.getSession();
				ses.setAttribute("uid", uid);
				ses.setAttribute("uname", uname);
				ses.setAttribute("email", email);
				ses.setAttribute("tpaid", tpaid);
				RequestDispatcher rd= request.getRequestDispatcher("userHome.jsp?status=Welcome "+uname);
				rd.include(request, response);
			}
			else {
				RequestDispatcher rd= request.getRequestDispatcher("login.jsp?status=Invalid Username and Password");
				rd.include(request, response);
			}
			}catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher rd= request.getRequestDispatcher("login.jsp?status=Some Internal Error");
				rd.include(request, response);
			}
		}
	}
}