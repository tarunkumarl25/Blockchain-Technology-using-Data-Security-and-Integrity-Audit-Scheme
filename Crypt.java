package deduplicatable.data.auditing.util;

import java.security.MessageDigest;

import com.mysql.jdbc.StandardSocketFactory;

public class Crypt {
	public static String sha256(String input) 
    { 
        try { 
            MessageDigest sha = MessageDigest.getInstance("SHA-256"); 
            
            int i = 0; 
  
            byte[] hash 
                = sha.digest( 
                    input.getBytes("UTF-8")); 
            StringBuffer hexHash 
                = new StringBuffer(); 
  
            while (i < hash.length) { 
                String hex 
                    = Integer.toHexString( 
                        0xff & hash[i]); 
                if (hex.length() == 1) 
                    hexHash.append('0'); 
                hexHash.append(hex); 
                i++; 
            } 
            System.out.println("Hash--->"+hexHash.toString());
            return hexHash.toString(); 
        } 
        catch (Exception e) { 
            throw new RuntimeException(e); 
        } 
    } 
}
