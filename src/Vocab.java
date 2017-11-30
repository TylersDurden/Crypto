/** <VOCAB.java>*/
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * 
 */
public class Vocab {
    
    public static Map<Integer,String> dictionary = new HashMap<>();
    
    
    public Vocab(){
       init();
       
    }
     
     public static void main(String[]args){
        new Vocab();
    }
    
    /** Initialize vocabulary with the txt file wordlist */
    private void init(){
        try{
            Path p = Paths.get("/projects/Fitness/src","./dict.txt");
            BufferedReader br = null;
            br = new BufferedReader(new FileReader(p.toFile()));
            String currentLine;
            int index = 0;
            while((currentLine = br.readLine()) != null){
                dictionary.put(index,currentLine);
                index+=1;
            }
            br.close();
            System.out.print("\n"+index+" dictionary entries \n");
       }catch(IOException e){e.printStackTrace();}
    }
    
    /** Method for searching dictionary*/
   public static int searchDictionary(String guess){
       int ans = -1;
       for(Map.Entry<Integer,String>entry:dictionary.entrySet()){
           if(entry.getValue().compareTo(guess)==0){
               ans = entry.getKey();
           }
       }
       return ans;
   }
   
   /** retrieve a dictionary entry*/ 
   public String getDictoEntry(int index){
       return dictionary.get(index);
   }
   
   /** Encrypted Dictionary to check for collisions */
   public static Map<String,String> encryptDictionary(Map<String,String>ref){
       Map<String,String> encDict = new HashMap<>();
       Map<String,String> joker = new HashMap<>();
       int i=0;
       for(Map.Entry<Integer,String>entry:dictionary.entrySet()){
           String word = entry.getValue();
           String encryptedEntry = "";
           String caps = "";
           for(String lets:word.split("")){
               encryptedEntry+=(ref.get(lets.toUpperCase()));
               caps += lets.toUpperCase();
           }
           encDict.put(encryptedEntry,entry.getValue());
           joker.put(caps,encryptedEntry);
           if(i<8){
               System.out.print("<"+encryptedEntry+","+entry.getValue()+"> ");
           }
           i+=1;
       }
        
       return joker;
   }
   
   
  
}
/** <VOCAB.java>*/