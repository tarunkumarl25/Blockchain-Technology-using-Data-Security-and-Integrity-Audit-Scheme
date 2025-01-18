package deduplicatable.data.auditing.mechanism.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import deduplicatable.data.auditing.dbconnection.DbConnection;
import deduplicatable.data.auditing.mechanism.dao.SecurityDAO;
import deduplicatable.data.auditing.mechanismbean.Bean;

@WebServlet("/SendChallengeServlet_user")
public class SendChallengeServlet_user extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fid = Integer.parseInt(request.getParameter("fid"));
		String fname = request.getParameter("fname");
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		int audit = 0;
		String data = null;
		String blocks = null;
		int hashcode = 0;
		String hashvalues = null;
		Bean b = new Bean();
		HttpSession ses = request.getSession();
		int uid =(Integer)ses.getAttribute("uid");
		String uname = (String)ses.getAttribute("uname");
		int tpaid =(Integer)ses.getAttribute("tpaid");
		
		if(fid!=0) 
		{
			try {
				con = new DbConnection().getConnection();
				ps = con.prepareStatement("select data,blocks,hashcode,hashvalues from files where fid=?");
				ps.setInt(1, fid);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) 
				{
					data = rs.getString(1);
					blocks = rs.getString(2);
					hashcode = rs.getInt(3);
					hashvalues = rs.getString(4);
				}
				ps1 = con.prepareStatement("select distinct(dataindex) from audit");
				ResultSet rs1 = ps1.executeQuery();
				while(rs1.next()) 
				{
					if(data.equals(rs1.getString(1))) 
					{
						audit++;
					}
				}
				if(audit!=0) 
				{
					int duplicate = new SecurityDAO().changeDuplicate(fid);
					if(duplicate!=0) {
					RequestDispatcher rd = request.getRequestDispatcher("SendChallengeNonce_user.jsp?status=This File is audited by Other user so please check in diplications");
					rd.include(request, response);
					}
					else 
					{
						RequestDispatcher rd = request.getRequestDispatcher("SendChallengeNonce_user.jsp?status=faild to Duplicate");
						rd.include(request, response);
					}
				}
				else 
				{
				int i = new SecurityDAO().userFileForAudit(fid);
				if(i!=0) 
				{
					b.setUid(uid);
					b.setTpaid(tpaid);
					b.setFid(fid);
					b.setName(uname);
					b.setFname(fname);
					b.setData(data);
					b.setBlock(blocks);
					b.setHashcode(hashcode);
					b.setHashvalue(hashvalues);
					int sendaudit = new SecurityDAO().auditFile(b);
					if(sendaudit!=0) {
					RequestDispatcher rd = request.getRequestDispatcher("SendChallengeNonce_user.jsp?status=File send to sp for Audit Result");
					rd.include(request, response);
					}
					else 
					{
						RequestDispatcher rd = request.getRequestDispatcher("SendChallengeNonce_user.jsp?status=Not Sended");
						rd.include(request, response);
					}
				}
				else 
				{
					RequestDispatcher rd = request.getRequestDispatcher("SendChallengeNonce_user.jsp?status=Faild to Send");
					rd.include(request, response);
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher rd = request.getRequestDispatcher("SendChallengeNonce_user.jsp?status=Some Internal Error");
				rd.include(request, response);
			}
		}
		else 
		{
			RequestDispatcher rd = request.getRequestDispatcher("SendChallengeNonce_user.jsp?status=fid not valid");
			rd.include(request, response);
		}
	}
}