import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.*;

/** 
 * 
 */
public class FitFunction {

    Alpha                         abet     = new Alpha();
    Map<String, String>           soln     = new HashMap<>();
    Map<String, Vector<Integer>>  core     = new HashMap<>();
    Map<Integer, Vector<Integer>> cortex   = new HashMap<>();

    Vector<Integer>               identity = new Vector<>();  //<1,1,1,1,1,1,1,1,...>
    Vector<Map<String, String>>   trials   = new Vector<>();
    long                          start;

    public FitFunction(String element) {

        //initialize crypto settings
        new Vocab();
        init();

        start = System.currentTimeMillis();
        Map<String, String> encDict = Vocab.encryptDictionary(new Alpha().soln);
        /* <ALGORITHM/STRATEGY> 
        Vector<Integer> f_e = flatten(element, "q");
        getValue(f_e);
        Vector<Integer> fee = flatten(encDict.get(element), "q");
        getValue(fee);
        Vector<Integer> diff = flatSubtract(fee, f_e);
        getID(getValue(diff));
        System.out.print("\n Applying Dictionary comparisons to produce collisions ");
        */
        this.core = fitInit(encDict);
        
        // ^^ this is the process that should be employed in fitinit 
        // but for every dictionary entry and its encrypted pair 
        

        System.out.print("**"+this.core.size()+
            " entries flattened and structured [" + timeElapsed() + " ms ]" + "\n");
        //Run the core through Reductionist 
        Reductionist redux = new Reductionist(this.core,this.soln);
    }


    public static void main(String[] args) {
        
    }

    public void init() {

        soln = abet.cipher;
        trials.add(soln);
        List<String> keys = new ArrayList<String>(soln.keySet());
        Vector<String> vals = new Vector<>();

        for (Map.Entry<String, String> entry : soln.entrySet()) {
            vals.add(entry.getValue());
            System.out.print(entry.getValue() + " ");
            identity.add(1);
        }
        System.out.println("");
        for (String s : keys) {
            System.out.print(s + " ");
        }
        System.out.println(" ");


    }


    /** Initialize the structure of how the fitness of guesses will be implemented*/
    public Map<String, Vector<Integer>> fitInit(Map<String, String> words) {

        //Repeat this process for whole encrypted Dictionary!
        // Then add a training vector with the identity (alphabet iv) to get a value 
        System.out.print("\nTraining entire Dictionary ... this may take a while.\n");
        List<String> normWords = new ArrayList<String>(words.keySet());
        List<String> encWords = new ArrayList<String>(words.values());
        /*
        System.out.println("|<" + normWords.get(0) + "," + encWords.get(0) + ">"+
                           "|<" + normWords.get(1) + "," + encWords.get(1) + ">|"+
                        "...|<" + normWords.get(890)+"," + encWords.get(890)+">|");
        */
        Map<String, Vector<Integer>> genome = new HashMap<>();
        for (Map.Entry<String, String> entry : words.entrySet()) {
            genome.put(entry.getKey(), new Vector<>(flatten(entry.getValue(), "q")));
            Integer iq = getID(flatSubtract(flatten(entry.getValue(), "q"), new Vector<Integer>(flatten(entry.getKey(), "q"))));
            cortex.put(iq, new Vector<>(flatten(entry.getValue(), "q")));
        }
        return genome;
    }

    public static Vector<Integer> flatten(String trainer, String mode) {
        boolean display = true;
        //|A|B|C|D|E|F|....  
        //|1|1|1|1|1|1|....[0-25]
        //|1|0|1|0|1|1| = F A C E 
        //|2|1|2|1|2|2| = val 
        // val - 
        ////////////////display letters///////////////
        if (mode.compareTo("v") == 0) {
            display = true;
        } else {
            display = false;
        }
        if (display == false) {

        } else {
            System.out.print("\n|");
        }
        int index = 0;
        String[] chars = new String[trainer.length()];
        for (String letters : trainer.split("")) {
            if (display == true) {
                System.out.print(letters + "|");
            }
            chars[index] = letters;
            index += 1;
        }
        if (display == true) {
            System.out.print("\n|");
        }
        //////////////////////////////////////////

        Vector<Integer> id = new Vector<>();
        Map<Integer, Integer> matrix = new HashMap<>();
        int j = 0;
        for (String l : Alpha.letters) {
            if (trainer.contains(l)) {
                id.add(1);
            } else {
                id.add(0);
            }
        }
        if (display == true) {
            //Display nums 
            System.out.print("\n|");
            int sum = 0;
            for (Integer i : id) {
                System.out.print(i + "|");
                sum += i;

            }
            if (display == true) {
                System.out.print(" " + sum + "\n");
                //Show Alphabet for context 
                System.out.print("\n|");

                for (String le : Alpha.letters) {
                    System.out.print(le + "|");
                }
                System.out.print("\n");

            }

            for (Map.Entry<Integer, Integer> entry : matrix.entrySet()) {
                System.out.print(entry.getValue() + " ");

            }
            System.out.print("\n");
        }

        return id;
    }

    public static void showVector(Vector<Integer> chroma) {
        //Display nums 
        System.out.print("\n|");
        int sum = 0;
        for (Integer i : chroma) {
            System.out.print(i + "|");
            sum += i;
        }
        System.out.print(" : " + sum + "\n");
    }

    //Fitness Score 
    public Integer getID(Vector<Integer> unique) {
        Integer chroma = 0;
        for (Integer i : unique) {
            chroma += i;
        }
        return chroma;
    }

    //unique value
    public Vector<Integer> getValue(Vector<Integer> chain) {
        Vector<Integer> result = new Vector<>();
        if (chain.size() != identity.size()) {
            System.out.print("\nDIMENSION ERROR?" + "Identity_Size: " + identity.size() + " / Chain_Size: " + chain.size());
        } else {
            int index = 1;
            for (Integer i : identity) {
                result.add(i + chain.get(index - 1));
                index += 1;
            }
        }
        showVector(result);
        return result;
    }

    //Vector Subtraction : <A> - <B>
    public Vector<Integer> flatSubtract(Vector<Integer> a, Vector<Integer> b) {
        Vector<Integer> resultant = new Vector<>();
        if (a.size() != b.size()) {
            System.out.print("\nDIMENSION ERROR?" + "Identity_Size: " + a.size() + " / Chain_Size: " + b.size());
        } else {
            int index = 0;
            for (Integer n : a) {
                resultant.add(n - b.get(index));
                index += 1;
            }
        }

        return resultant;
    }

    public int UI() {
        System.out.print("Enter a word (please use all lower case):\n");
        Integer d;
        Scanner sc = new Scanner(System.in);
        String response = sc.nextLine();
        int ans = Vocab.searchDictionary(response);
        sc.close();
        return ans;
    }


    //Simple method for getting time programs been running
    public double timeElapsed() {
        return System.currentTimeMillis() - start;
    }

    /** Create the weights for every word in the dictionary for a given random alphabet 
    with a test word */
    public void trainDictionary(Vector<Integer> testFlat, Map<String, String> encDict, String word) {
        Map<Integer, String> guesses = new HashMap<>();
        int nEntries = 0;
        for (Map.Entry<String, String> entry : encDict.entrySet()) {
            if (getValue(flatten(entry.getKey(), "q")) == getValue(flatten(word, "q"))) {
                nEntries += 1;
            }
        }

        System.out.print("\n" + nEntries + " possible matches. \n");

    }


}
