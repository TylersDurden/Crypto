import java.nio.file.*;
import java.io.*;
import java.util.*;

/**<TRSLTR>
 * Final Class of the Crypto2.0 workflow
 * that will implement a fitness analysis of the procession
 * of guesses, and evaluate them for collisions, or detect
 * legitamate substrings of english clear text. 
 */
public class TRSLTR {
    private static final String [] common = {"E","T","A","O","I","N","S","H","R","D","L","U"};
    static Map<String,String> guessBet = new HashMap<>();
    /**<Constructor>
     * A good start for figuring out algorithm
     * would be to make a program that allows user
     * input to be a query into dictionary, and return
     * all of the most similar words, with numerical
     * vals for HOW close/similar the restults are! 
     */
    public TRSLTR() {

        /** Load encrypted message from CipherTextFile */
        String txt = loadCipherText("/media/root/UNTITLED/Crypto2.0/src");
        System.out.print("Loaded CipherText: " + txt.replaceAll("\n"," ") + "\n");
        //System.out.print("Using Dictionary to Reduce Number of Guesses...");
        System.out.print("Analyzing text, and restructuring for deeper look.\n");
        Map<String,Vector<Integer>> cText = prepCiphText(txt);
        Vector<Integer> letFreq = cTextLetFreq(cText,"v");
        buildAlpha(letFreq);
        //organize by word size as well
        siftCiph(cText,"v");
        // Now start making substitutions based on word length 
        System.out.print("\nMaking substitutions of most frequent letters,"+
                         " based on dictionary entries of same word lengths\n");
        //Create a linguist to iterate substitution over different combinations of alphabets 
        Linguist clever = new Linguist();
        
        List<Map<String,String>> trainingData =  clever.scribe.dumpBets();
        System.out.print("\n"+trainingData.size()+" different Alphabets ready for testing.");
       // new FitFunction(txt);
       
    }

    public String loadCipherText(String path) {
        Path p = Paths.get(path, "./sample.txt");
        BufferedReader br = null;
        String cipherText = "";
        try {
            br = new BufferedReader(new FileReader(p.toFile()));
            String line;
            while ((line = br.readLine()) != null) {
                cipherText += line + "\n";
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cipherText;
    }
    
    public Map<String,Vector<Integer>> prepCiphText(String textin){
        String [] words = textin.replaceAll("\n"," ").split(" ");
        int sz = words.length;
        System.out.print(sz+" words in cText.\n...");
        Map<String,Vector<Integer>> cTextual = new HashMap<>();
        for(String word : words){
            Vector<Integer> flat = FitFunction.flatten(word,"q");// "v": verbose mode , else quiet
            cTextual.put(word,flat);
        }
        return cTextual;
        
    }
    
    /** Prepared Cipher Text cannot be put into the Reductionist, or analyzed by linguist
    because we do not understand the alphabet. Use some of the tools from the classes, 
    and some local to TRSLTR, to start building potential alphabet solutions. Then 
    use Reductionist to start making educated guesses on how/which letters to substitute*/
    public void buildAlpha(Vector<Integer>flat){
        
        Map<String,Integer> letFreq = new HashMap<>();
        Map<Integer,Vector<String>> lookup  = new HashMap<>();
        
        Vector<String>twoltr = new Vector<>();
        Vector<String>treltr = new Vector<>();
        Vector<String>fourlt = new Vector<>();
        Vector<String>fveltr = new Vector<>();
        Vector<String>sixltr = new Vector<>();
        Vector<String>svnltr = new Vector<>();
        Vector<String>eitltr = new Vector<>();
        
        int max = 0;
        int index = 0;
        for(Integer i : flat){
           letFreq.put(Alpha.letters[index],i);
           if(i==2){twoltr.add(Alpha.letters[index]);}
           if(i==3){treltr.add(Alpha.letters[index]);}
           if(i==4){fourlt.add(Alpha.letters[index]);}
           if(i==5){fveltr.add(Alpha.letters[index]);}
           if(i==6){sixltr.add(Alpha.letters[index]);}
           if(i==7){svnltr.add(Alpha.letters[index]);}
           if(i==8){eitltr.add(Alpha.letters[index]);}
           System.out.print(Alpha.letters[index]+":"+i+" ");
           index++;
           if(i>max){max=i;}
           if(index%5==0){System.out.print("\n");}  
        }
        lookup.put(2,twoltr);
        lookup.put(3,treltr);
        lookup.put(4,fourlt);
        lookup.put(5,fveltr);
        lookup.put(6,sixltr);
        lookup.put(7,svnltr);
        lookup.put(8,eitltr);
        
        
        
        System.out.print("\nMost Frequent Letter(s) in Cipher Text:\n"+
                                                    max+" occurences of: "+lookup.get(max)+"\n"+
                                                (max-1)+" occurences of: "+lookup.get(max-1)+"\n"+
                                                (max-2)+" occurences of: "+lookup.get(max-2));
    }
    
    /**  */
    Vector<Integer> cTextLetFreq(Map<String,Vector<Integer>>ctext,String mode){
        //Create bins for [A-Z]
        Integer [] bins = new Integer[26];
        Arrays.fill(bins,0);
        //Cipher text reduced to series of vectors 
        Collection<Vector<Integer>> dat = ctext.values();
        for(Vector<Integer> flat : dat){
            int index = 0;
            for(Integer i : flat){
                bins[index]+=i;
                index++;
            }
        }
        Vector<Integer>baseAlpha = new Vector<>();
        for(Integer e : bins){
            baseAlpha.add(e);
        }
        return baseAlpha;
        
    }
    
    /** sort a set of words by their length, can be done quietly or verbosely */
    public TreeMap<Integer,Vector<String>> siftCiph(Map<String,Vector<Integer>>ctext,String mode){
        List<String> encWords = new ArrayList<String>(ctext.keySet());
        TreeMap<Integer,Vector<String>> ctbysz = new TreeMap<Integer,Vector<String>>();
        System.out.println("\nAnalyzing length of words in Cipher Text.\n");
        
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
        ctbysz.put(2,twoltr);
        ctbysz.put(3,treltr);
        ctbysz.put(4,fourlt);
        ctbysz.put(5,fveltr);
        ctbysz.put(6,sixltr);
        ctbysz.put(7,svnltr);
        ctbysz.put(8,eitltr);
        if(mode.compareTo("v")==0){
         for(Map.Entry<Integer,Vector<String>>entry:ctbysz.entrySet()){
             System.out.println(entry.getKey()+" | "+entry.getValue());
         }               
        }else{/** <_QUIET_MODE_>*/}
        return ctbysz;
    }

    public static void main(String[] args) {
        new TRSLTR();
    }
    
    private class STACK {
    	
    	TRSLTR machina;
    	Alpha alphabet;
    	Vocab dicto;
    	Reductionist organize;
    	Linguist brute;
    	FitFunction think;
    	
    	
    	
    	public STACK() {
    		
    	}
    	

    }


}
