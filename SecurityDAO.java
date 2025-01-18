package deduplicatable.data.auditing.mechanism.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import sun.misc.BASE64Encoder;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import deduplicatable.data.auditing.dbconnection.DbConnection;
import deduplicatable.data.auditing.mechanismbean.Bean;

public class SecurityDAO extends DbConnection {
	Connection con =null;
	ArrayList<Bean> al = new ArrayList<>();
	PreparedStatement ps = null;
	int i = 0;
	
	public SecurityDAO() {
		con = getConnection();
	}
	
	public int reg(Bean b)throws Exception {
		ps = con.prepareStatement("insert into userdetails(uname,email,password,dob,mobile,tpaid,tpaname,status,address)values(?,?,?,?,?,?,?,'in active',?)");
		ps.setString(1, b.getName());
		ps.setString(2, b.getEmail());
		ps.setString(3, b.getPassword());
		String dob = b.getDob();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(sd.parse(dob).getTime());
		ps.setDate(4, date);
		ps.setString(5, b.getMobile());
		ps.setInt(6, b.getTpaid());
		ps.setString(7, b.getTpaName());
		ps.setString(8, b.getAddress());
		i = ps.executeUpdate();
		return i;
	}
	
	public ArrayList<Bean> userLogin(Bean userlogin)throws Exception {
		ps = con.prepareStatement("select uid,uname,email,tpaid from userdetails where email=? and password=? and status='active'");
		ps.setString(1, userlogin.getEmail());
		ps.setString(2, userlogin.getPassword());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean login = new Bean();
			login.setUid(rs.getInt(1));
			login.setName(rs.getString(2));
			login.setEmail(rs.getString(3));
			login.setTpaid(rs.getInt(4));
			al.add(login);
		}
		return al;
	}
	public ArrayList<Bean> tpaLogin(Bean tpalogin)throws Exception {
		ps = con.prepareStatement("select tid,tpaname,email from tpa where email=? and password=?");
		ps.setString(1, tpalogin.getEmail());
		ps.setString(2, tpalogin.getPassword());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			Bean tpa = new Bean();
			tpa.setTpaid(rs.getInt(1));
			tpa.setTpaName(rs.getString(2));
			tpa.setEmail(rs.getString(3));
			al.add(tpa);
		}
		return al;
	}
	
	public int  createTPA(Bean b)throws Exception {
		ps = con.prepareStatement("insert into tpa(tpaname,mobile,email,password)values(?,?,?,?)");
		ps.setString(1, b.getName());
		ps.setString(2, b.getMobile());
		ps.setString(3, b.getEmail());
		ps.setString(4, b.getPassword());
		i = ps.executeUpdate();
		return i;
	}
	
	public int  smAcceptUser(int uid)throws Exception {
		ps = con.prepareStatement("update userdetails set status='active' where uid=?");
		ps.setInt(1, uid);
		i = ps.executeUpdate();
		return i;
	}
	
	public int  uploadData(Bean b)throws Exception {
		ps = con.prepareStatement("insert into files(fname,data,hashcode,uid,uname,tpaid,hashvalues,blocks,status,blocksize,skey,pkey,status1)values(?,?,?,?,?,?,?,?,'send to sp',?,?,?,'create')");
		ps.setString(1, b.getFname());
		ps.setString(2, b.getData());
		ps.setInt(3, b.getHashcode());
		ps.setInt(4, b.getUid());
		ps.setInt(6, b.getTpaid());
		ps.setString(5, b.getName());
		ps.setString(7, b.getHashvalue());
		
		String data = b.getData();
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		SecretKey secretkey = keyGen.generateKey();
		String stringKey = Base64.encode(secretkey.getEncoded());
		System.out.println("scretkey" + stringKey);
		Cipher aescipher = Cipher.getInstance("AES");
		aescipher.init(Cipher.ENCRYPT_MODE, secretkey);
		byte[] byteDataToEncrypt = data.getBytes();
		byte[] byteCipherText = aescipher.doFinal(byteDataToEncrypt);
		String cipherText = new BASE64Encoder().encode(byteCipherText);
		
		ps.setString(8, cipherText);
		ps.setInt(9, b.getBlocksize());
		ps.setString(10, stringKey);
		ps.setString(11, b.getPkey());
		i = ps.executeUpdate();
		return i;
	}
	
	public int  spCreateIndex(int fid)throws Exception {
		ps = con.prepareStatement("update files set status='active',status1='Index Created' where fid=?");
		ps.setInt(1, fid);
		i = ps.executeUpdate();
		return i;
	}
	public int  userFileForAudit(int fid)throws Exception {
		ps = con.prepareStatement("update files set status='send to sp for audit' where fid=?");
		ps.setInt(1, fid);
		i = ps.executeUpdate();
		return i;
	}
	
	public int  changeDuplicate(int fid)throws Exception {
		ps = con.prepareStatement("update files set status='duplicate' where fid=?");
		ps.setInt(1, fid);
		i = ps.executeUpdate();
		return i;
	}
	
	
	public int  auditFile(Bean b)throws Exception {
		ps = con.prepareStatement("insert into audit(uid,tpaid,fid,username,fname,auditlog,status,dataindex,hashcode,hashvalues)values(?,?,?,?,?,?,'send to sp for audit',?,?,?)");
		ps.setInt(1, b.getUid());
		ps.setInt(2, b.getTpaid());
		ps.setInt(3, b.getFid());
		ps.setString(4, b.getName());
		ps.setString(5, b.getFname());
		ps.setString(6, b.getBlock());
		ps.setString(7, b.getData());
		ps.setInt(8, b.getHashcode());
		ps.setString(9, b.getHashvalue());
		i = ps.executeUpdate();
		return i;
	}
	
	public int  spSentAuditToTPA(int aid)throws Exception {
		ps = con.prepareStatement("update audit set status='send to TPA' where aid=?");
		ps.setInt(1, aid);
		i = ps.executeUpdate();
		return i;
	}
	
	public int  tpaPublishResultAudit(int aid)throws Exception {
		ps = con.prepareStatement("update audit set status='Published' where aid=?");
		ps.setInt(1, aid);
		i = ps.executeUpdate();
		return i;
	}
	public int  tpaPublishResultFile(int fid)throws Exception {
		ps = con.prepareStatement("update files set status='Published' where fid=?");
		ps.setInt(1, fid);
		i = ps.executeUpdate();
		return i;
	}
	
	public int  userFileforDeduplication(int fid)throws Exception {
		ps = con.prepareStatement("update files set status='Deduplicate' where fid=?");
		ps.setInt(1, fid);
		i = ps.executeUpdate();
		return i;
	}
	public int  spSendOwnerShip(Bean b)throws Exception {
		ps = con.prepareStatement("insert into audit(uid,tpaid,fid,username,fname,auditlog,status,dataindex,hashcode,hashvalues)values(?,?,?,?,?,?,'Published',?,?,?)");
		ps.setInt(1, b.getUid());
		ps.setInt(2, b.getTpaid());
		ps.setInt(3, b.getFid());
		ps.setString(4, b.getName());
		ps.setString(5, b.getFname());
		ps.setString(6, b.getAddress());
		ps.setString(7, b.getData());
		ps.setInt(8, b.getHashcode());
		ps.setString(9, b.getHashvalue());
		i = ps.executeUpdate();
		return i;
	}
	public int  spDeduplicatUserFile(int fid)throws Exception {
		ps = con.prepareStatement("delete from files where fid=?");
		ps.setInt(1, fid);
		i = ps.executeUpdate();
		return i;
	}
}