# **Crypto**  
A repository for Cryptographic projects I am actively working on. 

## Contents
1. _words.java_

### Description - _words.java_
This is a simple class for encryption and decryption with a monoalphabetic substitution cipher. A monoalphabetic cipher is essentially just a new "encrypted" alphabet where each letter is linked instead with an alternative letter, allowing the resulting text to be initially unreadable without knowing how the encrypted alphabet has been scrambled.  

The first Step is to initialize an alphabet. 
```java 
private static String [] letters = {"A","B","C","D","E",
                                       "F","G","H","I","J",
                                       "K","L","M","N","O",
                                       "P","Q","R","S","T",
                                       "U","V","W","X","Y",
                                       "Z"};
```
To easily switch between letters in the alphabet, and their corresponding location, I will use Maps. This will be useful because as described above, the cipher will rely on creating a new mapping of the alphabet (for example where A!=0, B!=1, etc.) 
```java
 private static Map <Integer, String > alphabet  = new HashMap<>();
 private static Map <String,  Integer> reference = new HashMap<>();
 private static Map <Integer, String > scrambled = new HashMap<>(); 
```
The scrambled alphabet requires a key to initialize it. So get a key from user input, and remove these letters from the alphabet first. This is done with the method hashbBet(String key). I will step through this method to explain what's going on. 
```java
 private void hashabet(String in){
        String[] keyLets = in.split("");
        int[] avoid = new int[in.split("").length];
        //fill the <String,Integer> Map
        for (int i = 0; i < letters.length; i++) {
            alphabet.put(i, letters[i]);
            reference.put(letters[i], i);
        } ... 
```
The String input, which will represent encryption key, is broken into individual letters held in array keyLets. We want to remove these letters from the cipher alphabet (cipherBet), so array `int[] avoid = new int[keyLets.length]` is declared to hold the indexes of the letters that must be removed from the alphabet map. 

Then while looping through the String [] containing the alphabet a few different operations are performed consecutively. First we populate the `Map<String,Integer> alphabet` by assigning the key value (a letter) to a corresponding integer indexed by simply stepping through the alphabet String []. We immediately also Map the current index as a key to the corresponding letter, filling the `Map<Integer,String> reference` at the same time as alphabet. Now that the alphabetic map is initialized, we have to remove the letters of the key. 
```java
       for(int i=0;i<letters.length;i++){
             boolean remove = false;
           for(int j=0;j<keyVec.size();j++){
               if(letters[i].equals(keyVec.get(j))==true 
               || letters[i].toLowerCase().equals(keyVec.get(j))==true){
                 //Now remove the key from the alphabet 
                 alphabet.remove(i);
                 j=keyVec.size();
                 remove = true;
               }
           }
       }
```
Values of alphabet which previously contained a letter found in the key will instead contain a null value. The next step is to create a cipher alphabet, skipping over the null values by taking advantage of a `NullPointerException`. 
```java
       System.out.print("\nCreating CipherBet avoiding: ");
      /* Use the refernce Map to create cipherBet, avoiding
       * Alphabet entries that are found in Key */
      int collisions = 0;   int index =0; 
      for(Map.Entry<String, Integer> entry: reference.entrySet()){
           try{
                if(entry.getKey().compareTo(alphabet.get(index))==0){
                    cipherBet.add(alphabet.get(index));
                }
                
           }catch(NullPointerException e){
               //System.out.print("alphabet["+index+"] ");
               avoid[collisions] = index;
               collisions+=1;
               clean-=1;
           }
           index+=1;
           }
```
Great! We've created a cipher alphabet starting point, which doesn't contain the key values. The next step, (*_work in progress_*) will be to scramble this cipher alphabet to effectively render mapping between the cipherBet and alphabet indecipherable, and map it to the regular alphabet for fast encryption. 

```java
        System.out.print("\nCreating CipherBet ");
        /* Use the refernce Map to create cipherBet, avoiding
         * Alphabet entries that are found in Key */
        int collisions = 0;
        int index = 0;
        int clean = 0;
        for (Map.Entry<String, Integer> entry : reference.entrySet()) {
            try {
                if (entry.getKey().compareTo(alphabet.get(index)) == 0) {
                    cipherBet.add(alphabet.get(index));
                } 
            } catch (NullPointerException e) {
                //System.out.print("alphabet["+index+"] ");
                avoid[collisions] = index;
                collisions += 1;
            }
            index += 1;
        }
        ```
We want to avoid making our encryption key obvious by having it's letters absent from the encrypted text. This is addressed by recombining them into the cipherBet Map, which hasn't been mapped yet anyway. 
```java
        //add key letters to cipherbet, otherwise key letters are missing from encrypted text
        for (int j = 0; j < keyLets.length; j++) {
            cipherBet.add(keyLets[j]);
        }
             System.out.print("\tcipherBet Size = " + 
             cipherBet.size() +" alphabet size = 26"+
            "\nMaking a map of CipherBet to input ... \n");
```
Now finish building Map<String,String> scrambled by linking letters from alphabet Map<String,Int> to random letters 

```java
        int ix = 0;// result of a little improvised hashFunction 
        int[] randos = new int[26];
        for (int i = 0; i < cipherBet.size(); i++) {
            ix = (int)(Math.random()*(cipherBet.size() - i) + Math.random());
            randos[i] = ix;
            scrambled.put(alphabet.get(i), cipherBet.get(ix));
           
        }
    }
```
