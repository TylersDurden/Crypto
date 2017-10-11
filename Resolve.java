/** Resolve.java */
import java.nio.file.*;
import java.io.*;
import java.util.*;

/**********************
 * Resolve.java is an added component to Windtalker.java (which is a dependency). 
 * Windtalker quickly encrypts text, while Resolve will crack the resulting 
 * cipher text with a mix of brute force and a dictionary attack. 
 * 
 * @author ScottRobbins
 * @date 10/17 
 *********************/
public class Resolve {
    
    private static String ciphText;
    
    private static Map<Integer,String> alphaB = new HashMap<>();
    private static Map<String,Integer> getBetdex = new HashMap<>();
    private static Map<String,Integer> getGBet = new HashMap<>();
    private static Map<Integer,String> galpha = new HashMap<>();
    private static Map<String,String> diplo = new HashMap<>();
    //diplomat that goes between both alphabets
    
    //dictionary mapped by line (1 letter = 1st line, 2ltrs words 2nd, etc.)
    private static Map<Integer,String> dictionary = new HashMap<>();
    private static ArrayList<String> words;
    private static List<String> testAlpha;
    public static String [] ws;
    
    public static Vector<String>solution = new Vector<>();
    
    public Resolve(String ctext,Map<Integer,String>tempBet,
                Map<String,String>solve,Map<Integer,
                String>alphabet,Map<Integer,String>dict){
                    
        ciphText = ctext;
        alphaB = alphabet;
        galpha = tempBet;
        diplo = solve;//diplomatic map to convert between guess alpha and real alpha 
        dictionary = dict;
        getBetdex = indexedMap(galpha);
        
        //create testAlphabet template to be manipulated
        testAlpha = new ArrayList<>(diplo.values());
        
        /* Prepare the words */
        ws = ciphText.split(" ");
        // System.out.print("Attempting to crack "+ws.length+" words");
        
        /* start sifting words and substituting from dictionary*/
        analyze(ws);
        
        
  
  }
  
    /**  **/
    private static void analyze(String[] ciphWords){
        
        for(String word:ws){
            String[]cs=word.toUpperCase().split("");
            
            if(word.length()==1){
                String[]gc = dictionary.get(0).toUpperCase().split(" ");
               // Log("\n"+cs[0]+" : ");
                String[]tries=new String[dictionary.get(0).split(" ").length];
                
                int index=0;
                for(String c:gc){
                   if(tries!=null){
                    tries[index] = c;
                    diplo.put(c,cs[0]);
                    index+=1;
                   }
                }
                //System.out.print(tries[2]);
                //TODO: Do a cipherText Check
                 diplo.put(galpha.get(getBetdex.get(tries[0].split("")[0])),cs[0].split("")[0]);
                String key = galpha.get(getBetdex.get(cs[0].split("")[0]));
                String ks="";
                String [] keywords = new String[tries.length];
                for(int k=0;k<tries.length;k++){
                    keywords[k] = tries[k].split("")[0];
                    ks+=keywords[k]+" , ";
                }
                Log(" lookup_["+key+"]->"+ks);
                runAndCheck(key,keywords);                
                
            }
            if(word.length()==2){
                  String [] gs = dictionary.get(2).toUpperCase().split(" ");
                Log("\n"+cs[0]+cs[1]+" : "+gs[0]+","+gs[1]);
                String[]tries=new String[gs.length];
                int index=0;
                for(String c:gs){
                    if(c!=null){
                        tries[index] = c;
                         index+=1;
                    }     
                   
                }
                
               // diplo.put(galpha.get(getBetdex.get(gs[0].split("")[0])),cs[0].split("")[0]);
                //diplo.put(galpha.get(getBetdex.get(gs[1].split("")[0])),cs[1].split("")[0]);
                String [] key = new String[2];
                //create guess word keys
                key[0] = galpha.get(getBetdex.get(cs[0].split("")[0]));
                key[1] = galpha.get(getBetdex.get(cs[1].split("")[0]));
                Log(" lookup_["+galpha.get(getBetdex.get(cs[0].split("")[0]))+"]->"+tries[0].split("")[0]);
                Log(" lookup_["+galpha.get(getBetdex.get(cs[1].split("")[0]))+"]->"+tries[0].split("")[1]);
                
                
                String [] keywords = new String[tries.length];
            
                String ks = ""; String kFin = "";
                for(int k=0;k<tries.length;k++){
                    keywords[k] = tries[k].split("")[0]+tries[k].split("")[1];
                    ks += keywords[k]+" , ";
                }
                for(String k:key ){kFin+=k;}
                
                Log(" lookup_["+key[0]+key[1]+"]->"+ks);
                runAndCheck(kFin,keywords);
            }
            //TODO: It's starting to come together, but it's iterating very separately. 
            //maybe inside run another method on each iterated guess and figure out how 
            //close it is to being right. Then the program can zero in on answer quicker
        }
        
        //remap and check
        
        
    }
    
    /* More like runAndCheck, this method subs in input[element] corresponding to k and remaps alphabet
    then checks the cipher to see if new cipher text is more clear. It could be smart to make the string K a vector, 
    and depending on the elements lengths, start subbing all different mappings at once, instead of individually. */
    private static void runAndCheck(String k, String[]input){
        /* These two lists would definitely be useful at some point  */
        List<String>vs = new ArrayList<String>(diplo.values());
        List<String>ks = new ArrayList<String>(diplo.keySet());
        //^^This will allow me to use Collections.swap(<List>,index1,index2) for remapping
        
        /** Here is where the method really starts. Accumulate guess words from dictionary stored as input **/
        String res = "";
        for(String s:input){
            res+=s+" ";
        }
        
        Log("Remapping letters from "+k+" to all "+input.length+ " elements in:\n"+res); 
        
        //A bit clumsy, but must remap individual characters to eachother 
        int coll=0;
        if(k.length()==1){
            for(int j=0;j<input.length;j++){
                String testRun = "";
                for(String w:ws){
                    for(String s:w.split("")){
                        if(w!=null && s.compareTo(k)==0){
                            diplo.put(k.toUpperCase(),input[j].split("")[0]);
                            testRun+=diplo.get(s);
                            coll+=1;
                        }else{testRun+=s;}
                    }
                    testRun+=" ";
                }
                Log(testRun);//sort of remapping 
            }
            
           // Log("Should have remapped "+k+" to E: "+diplo.get(k.toUpperCase()));//yup 
        } // now do the same thing for 2 letter words. each letter in k remapped to letters from input
        int col=0;
        if(k.length()==2){
            for(int j=0;j<input.length;j++){
                String testRun = "";
                for(String w:ws){
                        if(w!=null && w.compareTo(k)==0){
                            //sub 1:1 bc k and w have len 2 
                            diplo.put(k.split("")[0],input[j].split("")[0]);
                            diplo.put(k.split("")[1],input[j].split("")[1]);
                            testRun+=input[j].split("")[0]+input[j].split("")[1];
                            col+=1;
                        }else{testRun+=k;}
                    testRun+=" ";
                }
               Log(testRun);
               remap(testRun);
            }
            
        }
        
        
        //After remap go through cipherText and add subs in 
        String ans = "";
        int collision=0;
        for(String w:ciphText.split(" ")){
            if(w!=null && w.toUpperCase().compareTo(k)==0){
                ans+=diplo.get(k);
                collision+=1;
            }else if(w!=null){
                ans+=w.toUpperCase();
            }
            ans+=" ";
        }
        Log("Guess: "+ans);
    }
    
    /* TODO: Map cipherGuess into existing cipherText and then 
    re encrypted the estimated clear text and compare accuracy to original ciphertext,
    using the test alphabet. If the rencrypted guess matches substring of original ciphertext 
    then the guess made is an accurate piece of clear text. You can then add these letter mappings
    from the correct guess to zero in on the full encrypted alphabet 
    */
    private static Map<String,String> remap(String cipherGuess){
        Map<String,String>template = new HashMap<>();
        
        return template;
    }
    
  
  
  /**  **/
  private static Map<String,Integer> indexedMap(Map<Integer,String>inputMap){
      int j=0;
      Map<String,Integer> revMap = new HashMap<>();
      //create a reverse Mapping of the guess alphabet
        for(Map.Entry<Integer,String>entry:inputMap.entrySet()){                                                                                                                                                                                                                                         
            revMap.put(entry.getValue(),entry.getKey());
            j+=1;
        }
      return revMap;
  }
    
     /* Log method */
    public static void Log(String in) {
        BufferedWriter writer = null;
        try {
            Path p = Paths.get("/projects/Crypto/src", "./results.txt");
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


    public static void main(String[]args){} 
  }
   
 /** Resolve.java */