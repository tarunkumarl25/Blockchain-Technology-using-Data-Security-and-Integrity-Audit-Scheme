package deduplicatable.data.auditing.mechanism.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import deduplicatable.data.auditing.dbconnection.DbConnection;
import deduplicatable.data.auditing.mechanismbean.Bean;

public class ViewDAO extends DbConnection{
	Connection con = null;
	PreparedStatement ps = null;
	ArrayList<Bean> al = new ArrayList<Bean>();
	public ViewDAO() {
		con = getConnection();
	}
	
	public ArrayList<Bean> smViewTPA()throws Exception {
		ps = con.prepareStatement("select * from tpa");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setTpaid(rs.getInt(1));
			b.setTpaName(rs.getString(2));
			b.setMobile(rs.getString(3));
			b.setEmail(rs.getString(4));
			al.add(b);
		}
		return al;
	}
	
	public ArrayList<Bean> userSelectTPA()throws Exception {
		ps = con.prepareStatement("select tid,tpaname from tpa");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setTpaid(rs.getInt(1));
			b.setTpaName(rs.getString(2));
			al.add(b);
		}
		return al;
	}
	
	public ArrayList<Bean> smViewUserRequest()throws Exception {
		ps = con.prepareStatement("select uid,uname,email,dob,mobile,tpaname,address from userdetails where status='in active'");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setUid(rs.getInt(1));
			b.setName(rs.getString(2));
			b.setEmail(rs.getString(3));
			b.setDob(rs.getString(4));
			b.setMobile(rs.getString(5));
			b.setTpaName(rs.getString(6));
			b.setAddress(rs.getString(7));
			al.add(b);
		}
		return al;
	}
	
	public ArrayList<Bean> smViewUsers()throws Exception {
		ps = con.prepareStatement("select uid,uname,email,dob,mobile,tpaname,address,tpaid from userdetails where status='active'");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setUid(rs.getInt(1));
			b.setName(rs.getString(2));
			b.setEmail(rs.getString(3));
			b.setDob(rs.getString(4));
			b.setMobile(rs.getString(5));
			b.setTpaName(rs.getString(6));
			b.setAddress(rs.getString(7));
			b.setTpaid(rs.getInt(8));
			al.add(b);
		}
		return al;
	}
	
	public ArrayList<Bean> userViewProfile(int uid)throws Exception {
		ps = con.prepareStatement("select uid,uname,email,dob,mobile,tpaname,address,tpaid from userdetails where uid=?");
		ps.setInt(1, uid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setUid(rs.getInt(1));
			b.setName(rs.getString(2));
			b.setEmail(rs.getString(3));
			b.setDob(rs.getString(4));
			b.setMobile(rs.getString(5));
			b.setTpaName(rs.getString(6));
			b.setAddress(rs.getString(7));
			b.setTpaid(rs.getInt(8));
			al.add(b);
		}
		return al;
	}
	
	public ArrayList<Bean> spViewUserFileRequest()throws Exception {
		ps = con.prepareStatement("select fid,fname,uid,uname,data,tpaid,pkey from files where status='send to sp'");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setUid(rs.getInt(1));
			b.setFname(rs.getString(2));
			b.setTpaid(rs.getInt(3));
			b.setName(rs.getString(4));
			b.setData(rs.getString(5));
			b.setHashcode(rs.getInt(6));
			b.setPkey(rs.getString(7));
			al.add(b);
		}
		return al;
	}
	
	public ArrayList<Bean> userViewactiveFile(int uid)throws Exception {
		ps = con.prepareStatement("select fid,fname,data,tpaid,pkey from files where status='active' and uid=?");
		ps.setInt(1, uid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setFid(rs.getInt(1));
			b.setFname(rs.getString(2));
			b.setData(rs.getString(3));
			b.setTpaid(rs.getInt(4));
			b.setPkey(rs.getString(5));
			al.add(b);
		}
		return al;
	}
	
	public ArrayList<Bean> spViewAuditFiles()throws Exception {
		ps = con.prepareStatement("select aid,username,tpaid,fid,fname,auditlog,hashcode from audit where status='send to sp for audit'");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setUid(rs.getInt(1));
			b.setName(rs.getString(2));
			b.setTpaid(rs.getInt(3));
			b.setFid(rs.getInt(4));
			b.setFname(rs.getString(5));
			b.setData(rs.getString(6));
			b.setHashcode(rs.getInt(7));
			al.add(b);
		}
		return al;
	}
	public ArrayList<Bean> tpaViewAuditFiles(int tpaid)throws Exception {
		ps = con.prepareStatement("select aid,username,fid,fname,auditlog,hashcode from audit where status='send to TPA' and tpaid=?");
		ps.setInt(1, tpaid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setUid(rs.getInt(1));
			b.setName(rs.getString(2));
			b.setFid(rs.getInt(3));
			b.setFname(rs.getString(4));
			b.setData(rs.getString(5));
			b.setHashcode(rs.getInt(6));
			al.add(b);
		}
		return al;
	}
	
	public ArrayList<Bean> tpaViewPublishedFiles(int tpaid)throws Exception {
		ps = con.prepareStatement("select aid,username,fid,fname,auditlog,hashcode,hashvalues from audit where status='Published' and tpaid=?");
		ps.setInt(1, tpaid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setUid(rs.getInt(1));
			b.setName(rs.getString(2));
			b.setFid(rs.getInt(3));
			b.setFname(rs.getString(4));
			b.setData(rs.getString(5));
			b.setHashcode(rs.getInt(6));
			b.setHashvalue(rs.getString(7));
			al.add(b);
		}
		return al;
	}
	
	public ArrayList<Bean> userViewDuplicateData(int uid)throws Exception {
		ps = con.prepareStatement("select fid,fname,data from files where status='duplicate' and uid=?");
		ps.setInt(1, uid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setFid(rs.getInt(1));
			b.setFname(rs.getString(2));
			b.setData(rs.getString(3));
			al.add(b);
		}
		return al;
	}
	
	public ArrayList<Bean> spViewDeDuplicate()throws Exception {
		ps = con.prepareStatement("select fid,uname,tpaid,fname,data,uid from files where status='Deduplicate'");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setFid(rs.getInt(1));
			b.setName(rs.getString(2));
			b.setTpaid(rs.getInt(3));
			b.setFname(rs.getString(4));
			b.setData(rs.getString(5));
			b.setUid(rs.getInt(6));
			al.add(b);
		}
		return al;
	}
	
	public ArrayList<Bean> userViewAuditResult(int uid)throws Exception {
		ps = con.prepareStatement("select aid,tpaid,fname,auditlog,dataindex,hashcode,hashvalues from audit where status='Published' and uid=?");
		ps.setInt(1, uid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setFid(rs.getInt(1));
			b.setTpaid(rs.getInt(2));
			b.setFname(rs.getString(3));
			b.setBlock(rs.getString(4));
			b.setData(rs.getString(5));
			b.setHashcode(rs.getInt(6));
			b.setHashvalue(rs.getString(7));
			al.add(b);
		}
		return al;
	}
	
	public ArrayList<Bean> smViewResult()throws Exception {
		ps = con.prepareStatement("SELECT blocksize,COUNT(blocksize) FROM files WHERE STATUS='Published' GROUP BY blocksize");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean b = new Bean();
			b.setBlocksize(rs.getInt(1));
			b.setUid(rs.getInt(2));
			al.add(b);
		}
		return al;
	}
}