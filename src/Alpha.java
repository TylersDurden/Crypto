/** <ALPHA.java>*/
import java.util.*;
import java.io.*;

/**
 * 
 */
public class Alpha {
    
    public static final String[]    letters   = {"A", "B", "C", "D", 
                                                  "E", "F", "G", "H",
                                                  "I", "J", "K", "L",
                                                  "M", "N", "O", "P", 
                                                  "Q","R", "S", "T", 
                                                  "U", "V", "W", "X", 
                                                  "Y", "Z"};
    
    public static Map<Integer,String> alphabet = new HashMap<>();
    public static Map<String,Integer>   revBet = new HashMap<>();
    public Vector<Integer>       stdiv  = new Vector<>();
    
    public Map<String,String>   cipher  = new HashMap<>();
    public Map<String,String>   soln    = new HashMap<>();
    
    public Alpha(){
        //initialize alphabet
        init();
        //scramble 
        this.cipher = scramble();
    }
    
    
    /** <_Initialize_Alphabet_> */
    private void init(){
        int i=0;
        for(String s:letters){
            alphabet.put(i,s);
            revBet.put(s,i);
            stdiv.add(i);
            i++;
        }
    }
    
    /** Scramble IV and generate new alphabet*/
   public Map<String,String> scramble(){
       Collections.shuffle(stdiv);
       Map<String,String> newBet = new HashMap<>();
       int j=0;
       for(int i:stdiv){
           newBet.put(alphabet.get(j),alphabet.get(i));
           cipher.put(alphabet.get(j),alphabet.get(i));
           soln.put(alphabet.get(i),alphabet.get(j));
           j++;
       }
       return newBet;
   }
   
   public static Vector<String> showIV(Vector<Integer>iv){
       Vector<String>ans = new Vector<>();
       for(Integer i : iv){ans.add(alphabet.get(i));}
       return ans;
   }
   
   
   /** Get the current initialization vector of alphabet */
   public Vector<Integer> getiv(){return stdiv;}
   
   
   
    
}
