/* Windtalker.java */
import java.nio.file.*;
import java.io.*;
import java.util.*;
/** WINDTALKER *********
 * @author ScottRobbins 
 * @date 10/2017 
 **********************/
public class Windtalker {

    private static long                 start;
    private static int                  tries;

    private static String[]             letters   = {"A", "B", "C", "D", 
                                                     "E", "F", "G", "H",
                                                     "I", "J", "K", "L",
                                                     "M", "N", "O", "P", 
                                                     "Q","R", "S", "T", 
                                                     "U", "V", "W", "X", 
                                                     "Y", "Z"};
    //Alphabet Mapping
    private static Map<Integer, String> alphabet  = new HashMap<>();
    private static Map<Integer, String> cipherBet = new HashMap<>();
    private static Map<String, Integer> setciph   = new HashMap<>();
    private static Map<String, Integer> getBet    = new HashMap<>();
    private static Vector<Integer>      iv        = new Vector<>();
    //Keys for setting alphabets 
    private static int[]                IvP       = new int[26];
    private static int[]                norm      = new int[26];

    private static String[]             words;
    public static String                fastciphertext;
    
    //Use Dictionary.txt and variables for cracking ciphers
    private static Map<Integer,String> dictionary = new HashMap<>();
    
    private static Map <Integer,String> fLets   = new HashMap<>();
    private static Map <Integer,String> smallws = new HashMap<>();
    private static Map <Integer,String>  avgws  = new HashMap<>();


    public Windtalker() {


        String sample = "My job is a total bummer.";

        start = System.currentTimeMillis();
        /** Generate the Initialization Vector Parameter **/
        genIV();

        /** Initialize the alphabet **/
        setBet();

        System.out.println("\n* ** *** *BEGINNING ENCRYPTION* *** ** *\n");
        /** Encrypt **/
        String cipherText = encrypt(sample);
        System.out.println(cipherText);
        /* Now the interesting part, trying to crack a 1:1 substitution cipher with brute force. */
        System.out.println("Using maps to decrypt this message");
        String clearText = quickSolve(cipherText);
        System.out.println("Decrypted:\n" + clearText + " [ " + timeElapsed() + " seconds]");


        /**Now Try Even quicker 1:1 sub cipher encryption **/
        System.out.print("* * * * Faster Encryption  Starting* * * *\n");
        Log("* * * * Faster Encryption  Starting* * * *\n");
        start = System.currentTimeMillis();
        String ans = fastCrypto(sample);
        double done = timeElapsed();
        Log("\n***********************************************" + "\nScrambled: "+
        ans+"\n Decrypted:"+ clearText+" [" + done + " seconds] ");
        System.out.println("\n***********************************************" +
        "\nFastScramble: \n" + ans + "\n[" + done + " seconds] ");

        Log("\n***********************************************");

        /* Do brute force example */
        Log("\nNow cracking " + ans + " with brute force");
        start = System.currentTimeMillis();
        int numTries = 25;
        String res = Br34k(ans, numTries);
        if (res.split("").length < cipherText.split("").length) {
            Log("Unsolved after " + numTries + "[" + timeElapsed() + " seconds]");
        }
    }

    /** Initialize the alphabet **/
    private void setBet() {
        //Initialize alphabet
        System.out.println("\t\t\t*Initializing Alphabet*");
        for (int index = 0; index < letters.length; index++) {
            alphabet.put(index, letters[index]);
            getBet.put(letters[index], index);
            norm[index] = index;
            System.out.print(alphabet.get(index) + " ");
        }
        System.out.print("\n");
        for (int i = 0; i < letters.length; i++) {
            cipherBet.put(i, alphabet.get(IvP[i]));
            setciph.put(alphabet.get(IvP[i]), i);
            System.out.print(cipherBet.get(i) + " ");
        }
        System.out.print("\n");
    }

    /** Generate the Initialization Vector Parameter **/
    private void genIV() {
        System.out.print("\nGenerating IvP:\n");
        for (int i = 0; i < norm.length; i++) {
            IvP[i] = 25 - i;
            System.out.print(IvP[i] + " ");
            if (i % 5 == 0 && i > 0) {
                System.out.print("\n");
            }
        }
    }

    private static String encrypt(String in) {

        String[] cs = in.replaceAll(" ", "").replaceAll(";", "").replaceAll(",", "").split("");
        String result = "";
        for (int i = 0; i < cs.length; i++) {
            if (cipherBet.get(setciph.get(cs[i].toUpperCase())) != null) {
                result += alphabet.get(setciph.get(cs[i].toUpperCase()));

            }
        }
        return result;

    }

    /** Very Fast monoalphabet cipher encryption method **/
    static String fastCrypto(String clearText) {

        String[] ws = clearText.split(" ");
        String[] cs = clearText.split("");
        List<String> newAlpha = new ArrayList<>(alphabet.values());

        Collections.shuffle(newAlpha);
        Map<Integer, String> newbet = new HashMap<>();
        Map<String, String >solution = new HashMap<>();
        String ciph = "";
        for (int i = 0; i < newAlpha.size(); i++) {
            newbet.put(i, newAlpha.get(i));
            solution.put(alphabet.get(i),newbet.get(i));
            ciph += solution.get(alphabet.get(i));
        }
        fastciphertext = ciph;
        Log("Using Alphabet: "+fastciphertext);
        String result = "";
        //Alphabet Set now Encrypt 
        int i=0;
        for (String word : ws) {
            String[] c = word.split("");
            for (int lets=0;lets<c.length;lets++) {
                String letter = c[lets].toUpperCase();
                int index = 0;
                result += solution.get(c[lets].toUpperCase());
                index += 1;
                }
            
            result+=" ";    
            i+=1;
            }
       
        return result;
       
    }


    /** CRACKit **/
    static String Br34k(String ctext, int nTries) {
        Log("* * * * * * * * * * * * * *  *  *  *\n"+
            "* * *     BREAKING CIPHER    *  *  *\n"+
            "* * * * * * * * * * * * * * *  *  *\n");
       
       String ans = "";
        /**TODO: Cipher Cracking Algorithm 
         (1) - Use frequency Analysis on words to start trying to break*/
        String [] ws = ctext.replaceAll("\n"," ").split(" ");//create String [] of encrypted words
        String [][] histo = new String [ws.length][26];
        int wordcount=0;
        
       try{
            readDictionary();
       }catch(NullPointerException e){}
        
        System.out.print("*********************************************************************\n"+
                         "**              Beginning Dictionary Attack on Cipher              **\n"+
                         "*********************************************************************\n");
                        /**   Map<Integer,String> fLets, smallws  and avgws     
                            and can't forget Map<LineNumber,String>dictionary !         **/
                         
            int index=0;
         
                List<String>testAlpha = new ArrayList<>(alphabet.values());//for reference
                Map<Integer,String> ciphtxt = new HashMap<>();
                Map<Integer,String> tempBet = new HashMap<>();
                Map<String,String> solve = new HashMap<>();
                
                int attempt=0;
                //initialize a test alphabet (should be iterate over the number of tries)
              
                    int ind=0;
                    Collections.shuffle(testAlpha);
                    Map<String,Integer>ciphLetFreq = new HashMap<>();
                    
                    for(Map.Entry<Integer,String>entry:alphabet.entrySet()){
                        tempBet.put(ind,(testAlpha.get(ind)));
                        solve.put(alphabet.get(ind),tempBet.get(ind));
                        System.out.print(solve.get(alphabet.get(ind))+" ");
                        ind+=1;
                    }
                    int wordCount=0; int wc=0;
                    //Now iterate through the ciphertext to get letter freq and word size
                   
                    for(String word :ws){
                        ciphtxt.put(wc,word);
                        wc+=1;
                    }
                    System.out.println("\nRandom alphabet created, now analyzing "+ciphtxt.size()+" encrypted words"); 
                
                    
                   for(Map.Entry<Integer,String> entry:ciphtxt.entrySet()){
                       String word="";
                       if(entry.getValue()!=null){word = entry.getValue();}
                       String[]let_arr = word.split("");
                       List<String>lets = new ArrayList<String>(Arrays.asList(let_arr));
                       //System.out.print(entry.getValue());
                       int fmax=0;
                       Vector <String> commLets = new Vector<>();
                       for(int n=0;n<let_arr.length;n++){
                          ciphLetFreq.put(tempBet.get(let_arr[n]), Collections.frequency(lets,let_arr[n]));
                          //System.out.print(" "+let_arr[n]+" : "+ciphLetFreq.get(tempBet.get(let_arr[n]))+" ");
                          int num = ciphLetFreq.get(tempBet.get(let_arr[n]));
                          if(fmax>num){
                              commLets.add(let_arr[n]);
                          }
                          fmax = num;
                       }
                       for(String s:commLets){
                           //System.out.print(s);
                       }
                       System.out.println(entry.getValue());
                       if(entry.getValue().length()==1){
                        //Sub in the random single letter for I and A and check results
                        Log("Found 1 letter word: "+entry.getValue()+". Letter Frequency: "+
                            ciphLetFreq.get(entry.getValue()));
                    }
                    if(entry.getValue().length()==2){
                        //sub in two letter words from smallws and check results
                    }
                    if(entry.getValue().length()==3){
                        //try three letter words 
                    }
        
                       wordCount+=1;
                   }
              
                
           // System.out.print(" "+ciphtxt.get());
              
    
    return ans;
    
}
    static String [] readDictionary() throws NullPointerException{
        Log("Reading from Dictionary...\n");
        String path = "./Dictionary";
        BufferedReader br = null;
        FileReader fr = null;
        String[] lines = new String[100];
        String [] words = new String [500];
        int ln = 0;

        Path p = Paths.get("/projects/Crypto/src", "./Dictionary.txt");
        try {
            fr = new FileReader(p.toFile());
            br = new BufferedReader(fr);

            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                dictionary.put(ln, currentLine);
                lines[ln] = currentLine;
                //Log(currentLine);
                //System.out.print(dictionary.get(ln));
                ln+=1;
            }
        } catch (IOException e) {e.printStackTrace();}
        finally { try {
                if (br != null) br.close();
                if (fr != null) fr.close();
            } catch (IOException e) {e.printStackTrace();}
        }
        
        /*Do some manipulation on data read */
       
       int index=0;
       for(String line:lines){
           line = lines[index];
           String [] elements = line.split("");
           if(index==0){
               int count=0;
               for(String e:elements){
                   fLets.put(count,e);
                   count++;
                   }
           }if(index>1&index<4){
               int count=0;
               for(String e:elements){
                   smallws.put(count,e);
                   count++;
                   }
           }if(index>4&&index<19){
               int count=0;
               for(String e:elements){
                   avgws.put(count,e);
                   count++;
                   }
           }
           index+=1;
       }
        
        return lines;   
    }



    /** quick solve **/
    static String quickSolve(String ciphertext) {
        //because this is really what should be timed 

        int[][] ivps = new int[26][26];
        int shift = 0;
        //System.out.print(cipherBet.get(setciph.get(ciphertext.split("")[0])));
        words = ciphertext.split(" ");
        String[] lets = ciphertext.split("");
        String result = "";
        for (int i = 0; i < lets.length; i++) {
            result += cipherBet.get(getBet.get(lets[i]));
        }
        return result;

    }

    /* This method returns elapsed runtime of program*/
    static double timeElapsed() {
        return (System.currentTimeMillis() - start) * 0.001;
    }

    /* Log method */
    public static void Log(String in) {
        BufferedWriter writer = null;
        try {
            Path p = Paths.get("/projects/Crypto/src", "./brute.txt");
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


    /*Main Method*/
    public static void main(String[] args) {
        new Windtalker();
    }


}
/* Windtalker.java */
