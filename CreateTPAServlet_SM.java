package deduplicatable.data.auditing.mechanism.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import deduplicatable.data.auditing.mechanism.dao.SecurityDAO;
import deduplicatable.data.auditing.mechanismbean.Bean;

@WebServlet("/CreateTPAServlet_SM")
public class CreateTPAServlet_SM extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Bean b = new Bean();
		b.setName(request.getParameter("name"));
		b.setEmail(request.getParameter("email"));
		b.setMobile(request.getParameter("mobile"));
		b.setPassword(request.getParameter("password"));
		
		try {
			int i = new SecurityDAO().createTPA(b);
			if(i!=0) 
			{
				RequestDispatcher rd = request.getRequestDispatcher("createTPA_SM.jsp?status=Created Successfully");
				rd.include(request, response);
			}else 
			{
				RequestDispatcher rd = request.getRequestDispatcher("createTPA_SM.jsp?status=Faild to Creat");
				rd.include(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("createTPA_SM.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
	}
}