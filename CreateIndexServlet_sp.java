package deduplicatable.data.auditing.mechanism.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import deduplicatable.data.auditing.mechanism.dao.SecurityDAO;

@WebServlet("/CreateIndexServlet_sp")
public class CreateIndexServlet_sp extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fid = Integer.parseInt(request.getParameter("fid"));
		if(fid!=0) 
		{
			try {
				int i = new SecurityDAO().spCreateIndex(fid);
				if(i!=0) 
				{
					RequestDispatcher rd = request.getRequestDispatcher("ViewUserData_sp.jsp?status=Index Created Successfully");
					rd.include(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher rd = request.getRequestDispatcher("ViewUserData_sp.jsp?status=Faild to Create");
				rd.include(request, response);
			}
		}
		else 
		{
			RequestDispatcher rd = request.getRequestDispatcher("ViewUserData_sp.jsp?status=Fid faild");
			rd.include(request, response);
		}
	}
}