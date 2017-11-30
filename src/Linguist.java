/** <LINGUIST.java>*/
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Linguist implements Runnable {

    public static String target;
    public int        currentAttempt = 0;
    public final long start          = System.currentTimeMillis();
    public Scribe scribe;

    public Linguist() {
        //Linguist only generates Alphas really really fast 
        run();
        System.out.print(" DONE [" + timeElapsed() + "ms]");
    }

    public void run() {

        Thread t = new Thread(new genBet(10));
        t.start();
        /**
         * Now read log.txt and apply some fit function
         * methods to figure out encrypted text. 
         * Also, figure out how to use EOWL(DATA) in
         * the program! 
         * 
         */
         scribe  =new Scribe(genBet.purpose);

    }

    /** MAIN method  */
    public static void main(String[] args) {
        new Linguist();
    }

    /** Strip <A,B,C,D,E> vals from a <0,1,0,1,1> style vector */
    public static Map<String, String> betFromVec(Vector<Integer> vec) {
        Map<String, String> resbet = new HashMap<>();
        Vector<String> lets = new Vector<>();
        /** <> */
        for (String let : Alpha.letters) { lets.add(let); }
        
        int k = 0;
        for (Integer i : vec) {
            resbet.put(lets.get(i), lets.get(k));
            k++;
        }

        return resbet;
    }
    
    public static class genBet implements Runnable {
        int                    size;
        Map<Integer, String>   thisBet    = new HashMap<>();
        Map<String, String>    nextBet    = new HashMap<>();
        Map<String, String>    brains     = new HashMap<>();
        Map<String, String>    vocabulary = new HashMap<>();
        static Vector<Integer> iv         = new Vector<>();
        public static Map<Vector<Integer>, Integer> purpose = new HashMap<>();
        
        public boolean         exists     = false;

        private genBet(int numBets) {
            this.size = numBets;
        }

        @Override
        public void run() {

            int nTimes = 0;
            Map<Vector<Integer>, Integer> trainingData = new HashMap<>();
            while (nTimes < size) {
                
                if (exists == true) {
                    trainingData.put(getiv(), nTimes);
                }
                init();
                //run some tests on trainingData! 
                nTimes += 1;
            }
             purpose = trainingData;
        }

        /** <*~Initialize~*> */
        public void init() {

            Alpha bet = new Alpha();
            log(Alpha.showIV(iv).toString());

            thisBet = Alpha.alphabet;
            nextBet = bet.cipher;
            brains = bet.soln;
            iv = bet.stdiv;
            
            exists = true;
             
        }

        /* Log method */
        public static void log(String in) {
            BufferedWriter writer = null;
            try {
                Path p = Paths.get("/media/root/UNTITLED/Crypto2.0/src", "./Log.txt");
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

        public synchronized Vector<Integer> getiv() {
            return iv;
        }

        public synchronized Map<String, String> getWords() {
            return vocabulary;
        }


    }

    /**
     * 
     */
    public static class Scribe implements Runnable {

        List<Vector<Integer>> queue;
        public List<Map<String,String>> alphaList; 
         
        public Scribe(Map<Vector<Integer>, Integer> trainingData) {
            queue = new ArrayList<Vector<Integer>>(trainingData.keySet());
            run();
            
        }

        public void run() {
            /**
             * Iterate through trainingdata using Linguist.betFromVec to get
             * sample alphabets. Then use those to start encryping dictionary 
             */
            //Linguist.expandDictionary("/projects/Fitness/src./dict.txt");
            Map<Map<String,String>,Integer> trials = new HashMap<>();
            
            for(Vector <Integer> iv : queue){
                Map<String,String> newBet = new HashMap<>();
                int i = 0;
                for(Integer j : iv){
                    newBet.put(Alpha.letters[j],Alpha.letters[i]);
                     i++;
                }
                trials.put(newBet,i);
            }
            this.alphaList = new ArrayList<Map<String,String>>(trials.keySet());
        }
        
        /* */
        public List<Map<String,String>> dumpBets(){return this.alphaList;}

    }

    

    //Simple method for getting time programs been running
    public double timeElapsed() {
        return System.currentTimeMillis() - start;
    }

}
