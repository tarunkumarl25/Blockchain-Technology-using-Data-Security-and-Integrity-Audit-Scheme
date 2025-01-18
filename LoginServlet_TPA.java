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

@WebServlet("/LoginServlet_TPA")
public class LoginServlet_TPA extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Bean b = new Bean();
		b.setEmail(request.getParameter("email"));
		b.setPassword(request.getParameter("password"));
		int uid =0;
		String uname=null;
		String email=null;
		
		try {
		ArrayList<Bean> al = new SecurityDAO().tpaLogin(b);
		for(Bean login : al) 
		{
			uid = login.getTpaid();
			uname = login.getTpaName();
			email = login.getEmail();
		}
		if(!al.isEmpty()) 
		{
			HttpSession ses = request.getSession();
			ses.setAttribute("tid", uid);
			ses.setAttribute("tname", uname);
			ses.setAttribute("email", email);
			RequestDispatcher rd= request.getRequestDispatcher("tpaHome.jsp?status=Welcome "+uname);
			rd.include(request, response);
		}
		else {
			RequestDispatcher rd= request.getRequestDispatcher("tpaLogin.jsp?status=Invalid Username and Password");
			rd.include(request, response);
		}
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd= request.getRequestDispatcher("tpaLogin.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
	}
}