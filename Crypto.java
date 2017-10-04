/* Crypto.java */
import java.nio.*;
import java.net.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;

/****** --- - ---- - ----- ~*CRYPTO*~ ----- - ---- - --- ************
 * Compilation: Crypto.java {no_dependencies}
 * 
 * Substitution Cipher With Maps.
 * Runs really quick, and has no dependencies and relies on very few
 * libraries. 
 * 
 * @Author ScottRobbins
 * 10/3/2017
 ****** --- - ---- - ----- ~*CRYPTO*~ ----- - ---- - --- ************/
public class Crypto {
    
    static long start;
    
    
    //File I/O mapping
    static Map <Integer, String> linemap = new HashMap<Integer, String>();
    static Map<Integer, String> words = new HashMap<Integer,String>(); 
    //crypto alphabetic mapping 
    static Map<Integer,String> piPlay = new HashMap<Integer,String>();
    static Map<Integer, String> alphabet = new HashMap<Integer,String>();
    static Map<Integer,String> cipherBet = new HashMap<Integer,String>();
    static Map<String,String> solution = new HashMap<String,String>();
    
     private static final int [] pi = {3,1,4,1,5,9
                                      ,2,6,5,3,5,8,
                                      9,7,9,3,2,3,8,
                                      4,6,2,3,7,3,3};
                                      
    private static final String [] alpha = {"A", "B", "C", "D",
                                            "E", "F", "G", "H", 
                                            "I", "J", "K", "L", 
                                            "M", "N", "O", "P", 
                                            "Q","R", "S", "T", 
                                            "U", "V", "W", "X", 
                                            "Y", "Z"};
    
    static int fileSize;
    static String [] lets;
    public Crypto(){
       
        /* Initialize the Crypto Engine */
        
        /* Read In data to be encrypted from a text file */
        readTextFile("Data.txt");
        System.out.println("\n****************************************************");
        /* Parse the file for words and letters */
        parseFile();
        /*Intialize cipher */
       System.out.println("\n****************************************************");
       initialize();
        /* Encrypt the information */
       System.out.println("\n****************************************************");
        /* Decrypt the information */
      // System.out.println("\n****************************************************")
        /* Display runtime */
        System.out.println(" Time: "+"["+timeElapsed()+"]s");
        
    }

    /* Prepare text file for encryption */
    static void parseFile(){
        System.out.print(lets.length+" letters to encrypt");
    }
    
    /*--- - ---- - -----Initialize the Cipher ~*[_pi_]*~ ----- - ---- - --- *
     * A B C D E F G H I J K L M N O P Q R S T U V W X Y Z Map<Integer,String> alphabet
     * D B E B F J C G F D F I J H J D C D I E G C D H D D Map<Integer,String> cipherBet
     * 
     */
    static void initialize(){
        //Initialize Map <Integer,String> alphabet 
        System.out.println("\nInitializing Cipher");
        for(int i=0;i<alpha.length;i++){
            alphabet.put(i,alpha[i]);
            System.out.print(alphabet.get(i)+" ");
        }System.out.println("");  
        //Initialize CipherBet
        for(int i=0;i<pi.length;i++){
            cipherBet.put(i,alphabet.get(pi[i]));
            System.out.print(cipherBet.get(i)+" ");
        }
        
    }
    
    /* - - - - - - - - - - ~*Encryption Method*~ - - - - - - - - - - -  */
   private void encrypt(){
       /** * * * * *<~*ALGORITHM*~>* * * * *
        * <1> get characters (save breaks in words)
        * <2> map characters to encrpted characters
        * <3> recombine as words 
        * <4> output 
        * * * * * * * * * * * * * * * * * */
       
       
       
       
       
   }
    
    /* read a text file for a given path*/
   static void readTextFile(String path){
           BufferedReader br = null;
           FileReader fr = null;
           String [] lines = new String [100];
           int ln=0;
           
               String [] temparr = new String [80];
           try{
               fr = new FileReader(path);
               br = new BufferedReader(fr);
               
               String currentLine;
               String time = null;
               
               while((currentLine=br.readLine()) != null){
                    lets = currentLine.split("");
                   linemap.put(ln,currentLine);
                   lines[ln] = currentLine;
                   //Log(currentLine);
                  System.out.println(currentLine);
                   
                  ;
                  System.out.print(linemap.get(ln));
               }
               
               
           } catch(IOException e){e.printStackTrace();}
           finally{
               try{
                   if(br!=null)
                   br.close();
                   if(fr != null)
                   fr.close();
               }catch(IOException e){
                   e.printStackTrace();
               }
           }
   }
   
   
    /* Log method */
    public static void Log(String in) {
        BufferedWriter writer = null;
        try {
            Path p = Paths.get("/projects/CLIbrowser/src","./Data.txt");
            File log = p.toFile();
            writer = new BufferedWriter(new FileWriter(log, true));
            writer.write(in + "\n");// <--logs it here
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    
     /* This method returns elapsed runtime of program*/
    static double timeElapsed() {
        return (System.currentTimeMillis() - start) * 0.001;
    }
    
    public static void main(String[]args){
        
        new Crypto();
     
    }
    
}
/* Crypto.java */