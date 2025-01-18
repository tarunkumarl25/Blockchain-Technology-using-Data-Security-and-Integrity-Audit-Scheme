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


@WebServlet("/RegisterServlet_TPA")
public class RegisterServlet_TPA extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Bean b = new Bean();
		b.setName(request.getParameter("name"));
		b.setEmail(request.getParameter("email"));
		b.setPassword(request.getParameter("password"));
		b.setDob(request.getParameter("dob"));
		b.setMobile(request.getParameter("mobile"));
		String tpa = request.getParameter("tpa");
		String[] data = tpa.split(",");
		b.setTpaid(Integer.parseInt(data[0]));
		b.setTpaName(data[1]);
		b.setAddress(request.getParameter("address"));
		
		try {
			int  i = new SecurityDAO().reg(b);
			if(i!=0) 
			{
				RequestDispatcher rd = request.getRequestDispatcher("register.jsp?status=Request Send to System Manager Waiting for Activate");
				rd.include(request, response);
			}
			else 
			{
				RequestDispatcher rd = request.getRequestDispatcher("register.jsp?status=Faild to Register");
				rd.include(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
	}
}