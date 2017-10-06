/* Crypto.java */
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

    static long                         start;


    //File I/O mapping
    static Map<Integer, String>         linemap   = new HashMap<Integer, String>();
    static Map<Integer, String>         words     = new HashMap<Integer, String>();

    //crypto alphabetic mapping 
    static Map<Integer, String>         piPlay    = new HashMap<Integer, String>();
    private static Map<String, Integer> viper     = new HashMap<String, Integer>();
    static Map<Integer, String>         alphabet  = new HashMap<Integer, String>();
    static Map<Integer, String>         cipherBet = new HashMap<Integer, String>();
    static Map<String, String>          solution  = new HashMap<String, String>();

    static Map<String, Integer>         revBet    = new HashMap<String, Integer>();
    static Map<String, String>          revSol    = new HashMap<String, String>();
    static Map<Integer, Integer>        numerics  = new HashMap<Integer, Integer>();
    static Map<String, String>          magic     = new HashMap<String, String>();

    private static final int[]          pi        = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9, 3, 2, 3, 8, 4, 6, 2, 3, 7, 3, 3};
    /** pi is essentially the equivalent of an IvP in DES. ^ could be made into a Vector as the private key **/
    private static final String[]       alpha     = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    static int                          fileSize;
    static String[]                     lets;
    static String[]                     ws;
    static int                          wordCount = 0;
    static String                       cipherText;
    static String[]                     ciphWords = new String[30];

    public Crypto() {

        /* Initialize the Crypto Engine */
        start = System.currentTimeMillis();
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
        cipherText = encrypt();
        LogResults("Encrypted Message:\n" + cipherText + "\t[" + timeElapsed() + " seconds]\n");
        /* Decrypt the information */
        decrypt();
        System.out.println("\n****************************************************");

        /* Display runtime */
        System.out.println(" Time: " + "[" + timeElapsed() + " seconds]");
    }

    /* Prepare text file for encryption */
    static void parseFile() {
        ws = linemap.get(0).split(" ");
        System.out.println(ws.length + " words");
    }

    /*--- - ---- - -----Initialize the Cipher ~*[_pi_]*~ ----- - ---- - --- *
     * A B C D E F G H I J K L M N O P Q R S T U V W X Y Z Map<Integer,String> alphabet
     * D B E B F J C G F D F I J H J D C D I E G C D H D D Map<Integer,String> cipherBet
     * 
     */
    static void initialize() {
        //Initialize Map <Integer,String> alphabet 
        System.out.println("\nInitializing Cipher");
        for (int i = 0; i < alpha.length; i++) {
            alphabet.put(i, alpha[i]);
            System.out.print(alphabet.get(i) + " ");
        }
        System.out.println("");
        //Initialize CipherBet
        for (int i = 0; i < pi.length; i++) {
            cipherBet.put(i, alphabet.get(pi[i]));
            viper.put(alphabet.get(i), pi[i]);
            numerics.put(pi[i], i);//<K,V> = <pi#,index>
            revBet.put(alphabet.get(pi[i]), pi[i]);
            System.out.print(cipherBet.get(i) + " ");

        }

    }

    /* - - - - - - - - - - ~*Encryption Method*~ - - - - - - - - - - -  */
    private String encrypt() {
        /** * * * * *<~*ALGORITHM*~>* * * * *
         * <1> get characters (save breaks in words)
         * <2> map characters to encrpted characters
         * <3> recombine as words 
         * <4> output 
         * 
         * ___Keep in Mind__: Encryption Mapping as follows : *******************************  
         * A B C D E F G H I J K L M N O P Q R S T U V W X Y Z Map<Integer,String> alphabet
         * D B E B F J C G F D F I J H J D C D I E G C D H D D Map<Integer,String> cipherBet

         *        Map<String,String>Solution = new HashMap<alphabet(i),cipherBet(i)>
                                                         < key        ,     value  >
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ** * * * * * * */


        for (int i = 0; i < alphabet.size(); i++) {
            solution.put(alphabet.get(i), cipherBet.get(i));
            revSol.put(cipherBet.get(i), alphabet.get(i));
        }

        String result = "";
        for (int j = 0; j < ws.length; j++) {
            //System.out.println(ws[0]);

            String[] chars = ws[j].split("");
            String temp = "";
            for (int i = 0; i < chars.length; i++) {
                result += solution.get(chars[i].toUpperCase());
                System.out.print(solution.get(chars[i].toUpperCase()));

                temp += solution.get(chars[i].toUpperCase());
            }
            ciphWords[wordCount] = temp;
            System.out.print(" ");
            wordCount += 1;
            if (j > 0 && j % 8 == 0) {
                System.out.print("\n");
            }
        }

        /***** Creating an Encryption Key **/

        String[] encLets = result.trim().split("");

        System.out.println("\n\t***Decrypting " + ciphWords.length + " words and " + encLets.length + " letters***");


        /* Display data visually */
        String[] guessWords = new String[ciphWords.length];
        for (int i = 0; i < encLets.length; i++) {
            System.out.print(encLets[i] + ": ");
            int index = 0;
            String line = "";
            for (Map.Entry<Integer, String> entry : cipherBet.entrySet()) {
                //entry.getKey(); 
                if (encLets[i].compareTo(entry.getValue()) == 0) {
                    System.out.print(alphabet.get(entry.getKey()) + ":" + viper.get(alphabet.get(entry.getKey())) + "|");
                    index += 1;
                    line += alphabet.get(entry.getKey());

                }
                magic.put(encLets[i], line);
                guessWords[index] = line;
            }
            System.out.println("");

        }


        System.out.println("\n***** Results mapped. Untangling possible solutions *****\n");

        /** Remember: guess[0] = AJPRWYZ, and magic <i,guess[i]> **/

        /* Nulls on non alphabetic characters, but other than that it's
        working and doing so fast */

        return result;
    }

    /* Decrypt a message that has been encrpted with the Solution HashMap */
    static String decrypt() {
        /**TODO: Do this, but with ciphWords one word at a time
        IDEA: 
        1 - use this reverse solution map
        2 - re encrypt this solution, and save the lettesr that were mapped correctly
        (correct if they match ciphText)
        3 - if not, save the letters that were mapped correctly, and alternate the letters that were wrong
        4 - repeat until the ciphertext spit out matches the ciphertext put in. Then you know you have the 
        correct clear text! 
         */
        LogResults("\t\t\tBEGINNING DECRYPTION");

        String[] encLets = cipherText.split("");
        String solution = "";
        //check clearText with ws[].split("")[]
        boolean addLet = false;
        for (String word : ws) {
            String[] lets = word.split("");
            for (int i = 0; i < lets.length; i++) {
                for (String letter : encLets) {
                    String[] characters = magic.get(letter).split("");//[A,J,P,R,W,Y,Z]
                    for (String c : characters) {
                        if (c.compareTo(lets[i].toUpperCase()) == 0) {
                            addLet = true;
                        }
                    }
                }
                if (addLet == true) {
                    solution += lets[i];
                    addLet = false;
                }
            }
            solution += " ";
        }

        System.out.print("Answer: " + solution);
        LogResults(solution + " Time Elapsed: " + timeElapsed() + " seconds");

        return solution;

    }

    /* read a text file for a given path*/
    static void readTextFile(String path) {
        BufferedReader br = null;
        FileReader fr = null;
        String[] lines = new String[100];
        int ln = 0;

        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);

            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                lets = currentLine.split("");
                linemap.put(ln, currentLine);
                lines[ln] = currentLine;
                System.out.print(linemap.get(ln));
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /* Log method */
    public static void Log(String in) {
        BufferedWriter writer = null;
        try {
            Path p = Paths.get("/projects/Crypto/src", "./Data.txt");
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

    /* Log method */
    public static void LogResults(String in) {
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

    /* This method returns elapsed runtime of program*/
    static double timeElapsed() {
        return (System.currentTimeMillis() - start) * 0.001;
    }

    public static void main(String[] args) {

        new Crypto();
    }

}
/* Crypto.java */
