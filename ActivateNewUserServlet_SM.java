package deduplicatable.data.auditing.mechanism.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import deduplicatable.data.auditing.mechanism.dao.SecurityDAO;

@WebServlet("/ActivateNewUserServlet_SM")
public class ActivateNewUserServlet_SM extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int uid =Integer.parseInt(request.getParameter("uid"));
		if(uid!=0) 
		{
			try {
				int i = new SecurityDAO().smAcceptUser(uid);
				if(i!=0) {
				RequestDispatcher rd = request.getRequestDispatcher("newUser_SM.jsp?status=Successfully Activated");
				rd.include(request, response);
				}
				else 
				{
					RequestDispatcher rd = request.getRequestDispatcher("newUser_SM.jsp?status=Not Activated");
					rd.include(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher rd = request.getRequestDispatcher("newUser_SM.jsp?status=Some Internal Error");
				rd.include(request, response);
			}
		}
		else 
		{
			RequestDispatcher rd = request.getRequestDispatcher("newUser_SM.jsp?status=Uid is null");
			rd.include(request, response);
		}
	}
}
