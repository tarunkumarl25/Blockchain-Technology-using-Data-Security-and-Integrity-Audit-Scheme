package deduplicatable.data.auditing.mechanism.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import deduplicatable.data.auditing.mechanism.dao.SecurityDAO;

@WebServlet("/DuplicateFileSendtoSPServlet_user")
public class DuplicateFileSendtoSPServlet_user extends HttpServlet {
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fid =Integer.parseInt(request.getParameter("fid"));
		if(fid!=0) 
		{
			try {
				int i = new SecurityDAO().userFileforDeduplication(fid);
				if(i!=0) 
				{
					RequestDispatcher rd = request.getRequestDispatcher("CheckForDuplications_user.jsp?status=File Send to SP for Ownership and Deduplication");
					rd.include(request, response);
				}
				else 
				{
					RequestDispatcher rd = request.getRequestDispatcher("CheckForDuplications_user.jsp?status=Faild to Send");
					rd.include(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher rd = request.getRequestDispatcher("CheckForDuplications_user.jsp?status=Some Internal Error");
				rd.include(request, response);
			}
		}
		else 
		{
			RequestDispatcher rd = request.getRequestDispatcher("CheckForDuplications_user.jsp?status=Faild in fid");
			rd.include(request, response);
		}
	}
}