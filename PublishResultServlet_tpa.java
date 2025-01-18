package deduplicatable.data.auditing.mechanism.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import deduplicatable.data.auditing.mechanism.dao.SecurityDAO;

@WebServlet("/PublishResultServlet_tpa")
public class PublishResultServlet_tpa extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int aid =  Integer.parseInt(request.getParameter("aid"));
		int fid =  Integer.parseInt(request.getParameter("fid"));
		if(aid!=0) 
		{
			try {
				int i = new SecurityDAO().tpaPublishResultAudit(aid);
				if(i!=0) 
				{
					int j = new SecurityDAO().tpaPublishResultFile(fid);
					if(j!=0) {
					RequestDispatcher rd = request.getRequestDispatcher("Audit_tpa.jsp?status=Audit Published Successfully");
					rd.include(request, response);
					}
				}
				else 
				{
					RequestDispatcher rd = request.getRequestDispatcher("Audit_tpa.jsp?status=Faild to Publish");
					rd.include(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher rd = request.getRequestDispatcher("Audit_tpa.jsp?status=Some Internal Error");
				rd.include(request, response);
			}
		}else 
		{
			RequestDispatcher rd = request.getRequestDispatcher("Audit_tpa.jsp?status=problem with aid");
			rd.include(request, response);
		}
	}
}