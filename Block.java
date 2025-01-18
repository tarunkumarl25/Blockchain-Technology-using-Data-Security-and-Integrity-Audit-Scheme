package deduplicatable.data.auditing.util;

import java.util.Date;

public class Block {
	
	Crypt crypt = new Crypt();
    public String hash; 
    public String previousHash; 
    private String data; 
    private long timeStamp; 
  
    public Block(String data, 
                 String previousHash) 
    { 
        this.data = data; 
        this.previousHash 
            = previousHash; 
        this.timeStamp 
            = new Date().getTime(); 
        this.hash 
            = calculateHash(); 
    } 
  
    public String calculateHash() 
    { 
        String calculatedhash 
            = Crypt.sha256(previousHash 
                + Long.toString(timeStamp) 
                + data);
        
        System.out.println("Blocks--->"+calculatedhash);
  
        return calculatedhash; 
    } 
}
