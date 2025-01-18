package deduplicatable.data.auditing.util;

import java.util.ArrayList;

public class GFG {
	 public static ArrayList<Block> blockchain 
     = new ArrayList<Block>(); 


 public ArrayList<Block> createBlocks() 
 { 
     blockchain.add(new Block( 
         "First block", "0")); 
     blockchain.add(new Block( 
         "Second block", 
         blockchain 
             .get(blockchain.size() - 1) 
             .hash)); 

     blockchain.add(new Block( 
         "Third block", 
         blockchain 
             .get(blockchain.size() - 1) 
             .hash)); 

     blockchain.add(new Block( 
         "Fourth block", 
         blockchain 
             .get(blockchain.size() - 1) 
             .hash)); 

     blockchain.add(new Block( 
         "Fifth block", 
         blockchain 
             .get(blockchain.size() - 1) 
             .hash));
     return blockchain;
     
 }
 
 public static Boolean isChainValid() 
 { 
     Block currentBlock; 
     Block previousBlock; 
   
     for (int i = 1; 
          i < blockchain.size(); 
          i++) { 
         currentBlock = blockchain.get(i); 
         previousBlock = blockchain.get(i - 1); 
   
         if (!currentBlock.hash 
                  .equals( 
                      currentBlock 
                          .calculateHash())) { 
             System.out.println( 
                 "Hashes are not equal"); 
             return false; 
         } 
         if (!previousBlock 
                  .hash 
                  .equals( 
                      currentBlock 
                          .previousHash)) { 
             System.out.println( 
                 "Previous Hashes are not equal"); 
             return false; 
         } 
     } 
     return true; 
}
}
