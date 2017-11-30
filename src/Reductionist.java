import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * Reductionist class takes the encrypted text and the 
 * Log.txt file containing the massive amount of generated
 * random alphabets to start making a large volume of guesses
 * These guesses will be narrowed down/Evaluated by a SEPARATE
 * class that does a fitness analysis!! (problem before was condensing
 * classes too compactly, need to distribute the tasks better.)
 */
public class Reductionist implements Runnable {
    
    List<String>vocab;
    Map<String,Vector<Integer>> genome;
    Map<String,String>translate;
    Genome self;
    
    
    public Reductionist(Map<String, Vector<Integer>>g,
                        Map<String,String>tran){
        genome = g;
        translate = tran;
        vocab = new ArrayList<String>(genome.keySet());
        Genome self = new Genome(genome);
        System.out.println(self.condenseChroma(genome.get(vocab.get(0))));// automate
        System.out.print(vocab.get(0)+":"+genome.get(vocab.get(0)));
        System.out.println("\nRunning Letter Frequency Analysis on the Dictionary.\n");
        letterFrequency();
        System.out.print("\n*Reductionist Initialized for Cipher Text."); 
    }

    public void run(){
        
        
    }
    
    public void genePool(){
        for(Vector<Integer> word : genome.values()){
           self.cryptoCount(word,translate);
        }
    }
    
    public void letterFrequency(){
        
        Map<Integer,Vector<Integer>> letFreq = new HashMap<>();
        int j = 0;
        //fill each slot [A-Z] with the sum of each words value @ that index 
         Integer [] alphabin = new Integer [26];
         Arrays.fill(alphabin,0);
        for(Vector<Integer> word : genome.values()){
            //First create an empty vector for the alphabet 
            Vector<Integer> alphaBin = new Vector<>();
            int index = 0;
            for(Integer i : word){
                alphaBin.add(index,i);
                alphabin[index]+=alphaBin.get(index);
                index+=1;
            }
            letFreq.put(j,alphaBin);
            j++;
        }      
        int k=0;
        Integer [] bins = new Integer [26];
        Arrays.fill(bins,0);
        while(k<5){
           System.out.println(vocab.get(k)+"\t:"+letFreq.get(k));
           int i = 0;
           for(Integer e : letFreq.get(k)){
               bins[i] += e;
               i++;
           }
            k++;
        }
        System.out.print("\n-------------------------------------------------\n");
        for(Integer h : bins){ System.out.print(h+" ");}
        System.out.print("\n-------------------------------------------------\n");
        System.out.print("Below are the Letter Frequency Results for Entire Encrypted Dictionary:\n");
        for(Integer p : alphabin){ System.out.print(p+" "); }
        System.out.print("\n");
        for(String s : Alpha.letters){ System.out.print(s+" "); }
        System.out.print("\n");
        for(String s : Alpha.letters){System.out.print(translate.get(s) + " ");}
        System.out.print("\n--------------------------------------------------\n");
    }
    
    /** Given a Map<String,Vector<Integer>> object, get all the values for a given size */
    public static TreeMap<Integer,Vector<String>> sortDictionaryByLength(Map<String,Vector<Integer>> object, int size){
       List<String> encWords = new ArrayList<String>(object.keySet());
        TreeMap<Integer,Vector<String>> tree = new TreeMap<Integer,Vector<String>>();
        System.out.println("\nOrganizing Dicitonary by Word Length\n");
        
        Vector<String>twoltr = new Vector<>();
        Vector<String>treltr = new Vector<>();
        Vector<String>fourlt = new Vector<>();
        Vector<String>fveltr = new Vector<>();
        Vector<String>sixltr = new Vector<>();
        Vector<String>svnltr = new Vector<>();
        Vector<String>eitltr = new Vector<>();
        
        for(String word : encWords){
            //ctbysz.put(word.length(),word);
            if(word.length()==2){twoltr.add(word);}
            if(word.length()==3){treltr.add(word);}
            if(word.length()==4){fourlt.add(word);}
            if(word.length()==5){fveltr.add(word);}
            if(word.length()==6){sixltr.add(word);}
            if(word.length()==7){svnltr.add(word);}
            if(word.length()==8){eitltr.add(word);}
        }
        tree.put(2,twoltr);
        tree.put(3,treltr);
        tree.put(4,fourlt);
        tree.put(5,fveltr);
        tree.put(6,sixltr);
        tree.put(7,svnltr);
        tree.put(8,eitltr);
        
        return tree;
    }
    
    
       private class Genome {
           // maybe instead make this an anonymous
           //class to run once per dict word
        
        
        Set<String>  elements;
        Collection<Vector<Integer>> design;
        Vector<Integer>chroma;
        Collection<Vector<Integer>> genetics;
        
        Genome(Map<String,Vector<Integer>> design){
          elements =  design.keySet();
          genetics =  design.values();
        }
        
        String condenseChroma(Vector<Integer>chr){
            int index = 0;
            String result = "";
            for(int i : chr){
               if(i==1){
                   result += Alpha.alphabet.get(index);}
                index++;
            }
            return result;
        }
        
        String cryptoCount(Vector<Integer>gene, Map<String,String> tar){
            int index = 0;
            String result = "";
            for(Integer i : gene){
                if(i==1){result += Alpha.alphabet.get(tar.get(index));}
                index += 1;
            }
            return result;
        }
        
        
        
        
    }
    
    
    
}
