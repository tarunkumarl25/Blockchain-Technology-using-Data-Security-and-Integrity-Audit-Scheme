package deduplicatable.data.auditing.mechanism.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import deduplicatable.data.auditing.dbconnection.DbConnection;
import deduplicatable.data.auditing.mechanism.dao.SecurityDAO;
import deduplicatable.data.auditing.mechanismbean.Bean;

@WebServlet("/DeduplicationIntegrityProofServlet_sp")
public class DeduplicationIntegrityProofServlet_sp extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Bean b = new Bean();
		int fid = Integer.parseInt(request.getParameter("fid"));
		String uname = request.getParameter("uname");
		int uid = Integer.parseInt(request.getParameter("uid"));
		String fname = request.getParameter("fname");
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		String data = null;
		String dataindex = null;
		if(fid!=0) 
		{
			con = new DbConnection().getConnection();
			try {
				ps = con.prepareStatement("select data from files where fid=?");
				ps.setInt(1, fid);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) 
				{
					data = rs.getString(1);
				}
				ps1 = con.prepareStatement("select distinct(dataindex) from audit");
				ResultSet rs1 = ps1.executeQuery();
				while(rs1.next()) 
				{
					if(data.equals(rs1.getString(1))) 
					{
						dataindex = rs1.getString(1);
					}
				}
				if(data.equals(dataindex)) 
				{
					ps2 = con.prepareStatement("select tpaid,fid,auditlog,hashcode,hashvalues from audit where dataindex=? group by dataindex");
					ps2.setString(1, dataindex);
					ResultSet rs2 = ps2.executeQuery();
					while(rs2.next()) 
					{
						b.setTpaid(rs2.getInt(1));
						b.setFid(rs2.getInt(2));
						b.setAddress(rs2.getString(3));
						b.setHashcode(rs2.getInt(4));
						b.setHashvalue(rs2.getString(5));
					}
					b.setUid(uid);
					b.setName(uname);
					b.setFname(fname);
					b.setData(dataindex);
					int owner = new SecurityDAO().spSendOwnerShip(b);
					if(owner!=0) 
					{
						int deduplicate = new SecurityDAO().spDeduplicatUserFile(fid);
						if(deduplicate!=0) 
						{
							RequestDispatcher rd = request.getRequestDispatcher("Deduplication_sp.jsp?status=Succssfully Data Deleted from Server and Send Ownership to "+uname);
							rd.include(request, response);
						}
						else 
						{
							RequestDispatcher rd = request.getRequestDispatcher("Deduplication_sp.jsp?status=File Deletion Faild");
							rd.include(request, response);
						}
					}
				}
				else 
				{
					RequestDispatcher rd = request.getRequestDispatcher("Deduplication_sp.jsp?status=Data Index Faild");
					rd.include(request, response);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				RequestDispatcher rd = request.getRequestDispatcher("Deduplication_sp.jsp?status=Some Internal Error");
				rd.include(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher rd = request.getRequestDispatcher("Deduplication_sp.jsp?status=Some Internal Error");
				rd.include(request, response);
			}
			
		}else 
		{
			RequestDispatcher rd = request.getRequestDispatcher("Deduplication_sp.jsp?status=Fid not valid");
			rd.include(request, response);
		}
	}
}
