package deduplicatable.data.auditing.mechanism.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import deduplicatable.data.auditing.dbconnection.DbConnection;
import deduplicatable.data.auditing.mechanism.dao.SecurityDAO;
import deduplicatable.data.auditing.mechanismbean.Bean;
import deduplicatable.data.auditing.util.Block;
import deduplicatable.data.auditing.util.GFG;


@WebServlet("/UploadDataServlet_User")
public class UploadDataServlet_User extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Bean b = new Bean();
		Connection con = null;
		String info = null;
		HttpSession session = request.getSession();
		int uid =(Integer)session.getAttribute("uid");
		String uname =(String)session.getAttribute("uname");
		int tpaid =(Integer)session.getAttribute("tpaid");
		
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			List li = fileUpload.parseRequest(request);
			
			
			FileItem text=(FileItem) li.get(0);
			
			 String fname = text.getName();
			 System.out.println("File name--->"+fname);
			 long size = text.getSize();
			 int blocksize = (int)size;
			 System.out.println("Int i===>"+blocksize/1024);
			 String data = text.getString();
			 Thread.sleep(3000);
			 con = new DbConnection().getConnection();
			 PreparedStatement ps = con.prepareStatement("select data from files where uid=? and data=?");
			 ps.setInt(1, uid);
			 ps.setString(2, data);
			 ResultSet rs = ps.executeQuery();
			 while(rs.next()) 
			 {
				 info = rs.getString(1);
			 }
			 if(info!=null) 
			 {
				 RequestDispatcher rd = request.getRequestDispatcher("UploadData_user.jsp?status=Data is Unique");
				 rd.include(request, response);
			 }
			 else {
			 System.out.println("fi--->"+data);
			 int hashcode = text.hashCode();
			 System.out.println("hacode-->"+hashcode);
			 
			 MessageDigest msg = MessageDigest.getInstance("SHA-1");
			 byte[] hashvalue = msg.digest(data.getBytes("UTF-8"));
			 System.out.println("HashValue--->"+hashvalue);
			 
			 StringBuffer stringBuffer = new StringBuffer();
		        for (int i = 0; i < hashvalue.length; i++) {
		            stringBuffer.append(Integer.toString((hashvalue[i] & 0xff) + 0x100, 16)
		                    .substring(1));
		        }
		        System.out.println("String Buffer--->"+stringBuffer.toString());
		        
		        b.setUid(uid);
		        b.setName(uname);
		        b.setTpaid(tpaid);
		        b.setFname(fname);
		        b.setData(data);
		        b.setHashcode(hashcode);
		        b.setHashvalue(stringBuffer.toString());
		        b.setBlocksize(blocksize/1024);
		        
		        Random rand = new Random();
		        Long lo =(long)rand.nextInt(1000000000);
		        b.setPkey(lo.toString());
		        
		        ArrayList<Block> block = new GFG().createBlocks();
		        if(!block.isEmpty()) 
		        {
		        	Thread.sleep(3000);
		        	int i = new SecurityDAO().uploadData(b);
		        	if(i!=0) {
						 RequestDispatcher rd = request.getRequestDispatcher("UploadData_user.jsp?status=File send to Service Provider for accept");
						 rd.include(request, response);
						 }
						 else 
						 {
							 RequestDispatcher rd = request.getRequestDispatcher("UploadData_user.jsp?status=Faild to Outsource");
							 rd.include(request, response);
						 }
		        }
		        else 
		        {
		        	RequestDispatcher rd = request.getRequestDispatcher("UploadData_user.jsp?status=Blocks Not Created");
					 rd.include(request, response);
		        }
			 }
				 
			 }catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher rd = request.getRequestDispatcher("UploadImageFile_DO.jsp?status=Some Internal Error");
				 rd.include(request, response);
			}
	}
}