package deduplicatable.data.auditing.mechanism.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import deduplicatable.data.auditing.mechanism.dao.SecurityDAO;

@WebServlet("/SendIntegrityProofServlet_sp")
public class SendIntegrityProofServlet_sp extends HttpServlet {
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int aid =  Integer.parseInt(request.getParameter("aid"));
		if(aid!=0) 
		{
			try {
				int i = new SecurityDAO().spSentAuditToTPA(aid);
				if(i!=0) 
				{
					RequestDispatcher rd = request.getRequestDispatcher("Audit_sp.jsp?status=Audit File Send to TPA for Publish");
					rd.include(request, response);
				}
				else 
				{
					RequestDispatcher rd = request.getRequestDispatcher("Audit_sp.jsp?status=Faild to Send");
					rd.include(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher rd = request.getRequestDispatcher("Audit_sp.jsp?status=Some Internal Error");
				rd.include(request, response);
			}
		}else 
		{
			RequestDispatcher rd = request.getRequestDispatcher("Audit_sp.jsp?status=problem with aid");
			rd.include(request, response);
		}
	}
}