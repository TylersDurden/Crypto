# **Crypto**  
A repository for Cryptographic projects I am actively working on. 

## Contents
1. Crypto.java - *Now Updated to support multiple line text files* 
2. Windtalker.java - *encryption with 1:1 alphabetic mapping* 
3. Resolve.java - *Brute Force/Dictionary attack on Windtalker cipher*

### *Crypto.java* 
A quick and dirty class that encrypts data from a text file put into the src folder in classpath, and decrypts to a textfile in the same folder. The console will also give a detailed print out of how the text was encrypted. 

## Variables 
The HashMaps defined in the beginning of this class make up the entire mechanism of encryption and decryption. To make these Maps possible, two arrays are initialized. The most important of which is an int [] used as a sort of initialization vector of how the cipher alphabet is mapped to the regular alphabet used in clear text. This int [] is completely up to you. I chose to use 20 or so digits of pi and then add some random numbers at the end. 


```java 

 private static final int[]          pi        = {3, 1, 4, 1, 5, 9, 
                                                  2, 6, 5, 3, 5, 8, 
                                                  9, 7, 9, 3, 2, 3,
                                                  8, 4, 6, 2, 3, 7,
                                                  3, 3};
    /** pi is essentially the equivalent of an IvP in DES. ^ could be made into a Vector as the private key **/
 private static final String[]       alpha     = {"A", "B", "C", "D",
                                                  "E", "F", "G", "H", 
                                                  "I", "J", "K", "L",
                                                  "M", "N", "O", "P",
                                                  "Q","R", "S", "T", 
                                                  "U", "V", "W", "X", 
                                                  "Y", "Z"};
```

Three HashMaps control encryption. The first one is the regular alphabet mapped to their relative indices,  `<i,alpha[i]>` . This provides the logical structure for both of the following Maps, and for how the cleartext letters are scrambled.  

```java
    static Map<Integer, String>         alphabet  = new HashMap<Integer, String>();
    static Map<Integer, String>         cipherBet = new HashMap<Integer, String>();
    static Map<String, String>          solution  = new HashMap<String, String>();

```
## Initializing the Cipher 
After filling alphabet with aforementioned values, the cipherBet is created by placing alphabet's mapped value for the key `pi[i]` , where i is the index in the cipherBet. The resulting two maps can be visualized below.  

```java 
    /*--- - ---- - -----Initialize the Cipher ~*[_pi_]*~ ----- - ---- - --- *
     * A B C D E F G H I J K L M N O P Q R S T U V W X Y Z Map<Integer,String> alphabet
     * D B E B F J C G F D F I J H J D C D I E G C D H D D Map<Integer,String> cipherBet
     * --- - --- - ---- ----------- -- ----- -- --------- ----- - ---- - ---*/
    static void initialize() {
        //Initialize Map <Integer,String> alphabet 
        System.out.println("\nInitializing Cipher");
        for (int i = 0; i < alpha.length; i++) {
            alphabet.put(i, alpha[i]);
        }
        //Initialize CipherBet
        for (int i = 0; i < pi.length; i++) {
            cipherBet.put(i, alphabet.get(pi[i]));
            viper.put(alphabet.get(i), pi[i]);
            revBet.put(alphabet.get(pi[i]), pi[i]);
            System.out.print(cipherBet.get(i) + " ");

        }

    }
```
What is interesting about this mapping is that there are many reoccuring values in the cipherBet. Therefore, encrypted words cannot be deciphered by simply figuring out which letters correspond to which on a 1:1 level. In this case, any given letter of the alphabet could correspond to the a range of different letters, whose size would be attributed to the mode of the array and the number of unique elements in the initializing Integer array (in this case `pi`). 

## Encrypting Text Files 
A FileReader gets data from a specific path, into a BufferedReader, and stores the content as first  `String [] lines` and then the current line saved with `static Map<lineNumber,textFromLine> linemap` to be read or referenced easily later. Once the text is prepared the encryption method is called. This method contains a lot of extra information simply to create an informative printout, but the encryption process itself is much smaller, and included here. 

```java
        for (int i = 0; i < alphabet.size(); i++) {
            solution.put(alphabet.get(i), cipherBet.get(i));
        }

        String result = "";
        for (int j = 0; j < ws.length; j++) {
            String[] chars = ws[j].split("");
            String temp = "";
            for (int i = 0; i < chars.length; i++) {
                result += solution.get(chars[i].toUpperCase());
                temp += solution.get(chars[i].toUpperCase());
            }
            ciphWords[wordCount] = temp;
            wordCount += 1;
        }
```
The first step of the encryption process consists of creating the HashMap `solution`, with `alphabet<Integer,String>` as the keys for corresponding `cipherBet<Integer,String>` values. Encrypting the text is then as simple as looping through the Characters of the message and swapping them with the corresponding cipherBet value in `solution`. 

## Results of Encryption 
The results of the encryption are then saved in the text file "results.txt", along with the time it took to encrypt the message. Using a very small sample in "Data.txt" to be encrypted the resulting encrypted text is: 

```txt
Encrypted Message:
DJGEDHDDJBDBIDEDDEFEGFIBGEFEGFHFFEFIDDFEEDEIFCFD	[0.021 seconds]
```
For the sake of clarity, the console prints out a very informative display on the encryptions results, and the logic behind retrieving the original message (and in effect also illustrating how difficult it would be to crack the encrypted message alone using brute force). 

First the initial message and it's word count is displayed.  

```txt
you can probably crack this but I think it is pretty clever
****************************************************
12 words
 
****************************************************
 
Initializing Cipher
A B C D E F G H I J K L M N O P Q R S T U V W X Y Z 
D B E B F J C G F D F I J H J D C D I E G C D H D D 
****************************************************
DJG EDH DDJBDBID EDDEF EGFI BGE F EGFHF FE 
FI DDFEED EIFCFD 
	***Decrypting 12 words and 48 letters***
D: A:3|J:3|P:3|R:3|W:3|Y:3|Z:3|
J: F:9|M:9|O:9|
G: H:6|U:6|
E: C:4|T:4|
D: A:3|J:3|P:3|R:3|W:3|Y:3|Z:3|
H: N:7|X:7|
D: A:3|J:3|P:3|R:3|W:3|Y:3|Z:3|
D: A:3|J:3|P:3|R:3|W:3|Y:3|Z:3|
J: F:9|M:9|O:9|
B: B:1|D:1|
D: A:3|J:3|P:3|R:3|W:3|Y:3|Z:3|
B: B:1|D:1|
I: L:8|S:8|
D: A:3|J:3|P:3|R:3|W:3|Y:3|Z:3|
E: C:4|T:4|
D: A:3|J:3|P:3|R:3|W:3|Y:3|Z:3|
D: A:3|J:3|P:3|R:3|W:3|Y:3|Z:3|
E: C:4|T:4|
F: E:5|I:5|K:5|
E: C:4|T:4|
G: H:6|U:6|
F: E:5|I:5|K:5|
I: L:8|S:8|
B: B:1|D:1|
G: H:6|U:6|
E: C:4|T:4|
F: E:5|I:5|K:5|
E: C:4|T:4|
G: H:6|U:6|
F: E:5|I:5|K:5|
H: N:7|X:7|
F: E:5|I:5|K:5|
F: E:5|I:5|K:5|
E: C:4|T:4|
F: E:5|I:5|K:5|
I: L:8|S:8|
D: A:3|J:3|P:3|R:3|W:3|Y:3|Z:3|
D: A:3|J:3|P:3|R:3|W:3|Y:3|Z:3|
F: E:5|I:5|K:5|
E: C:4|T:4|
E: C:4|T:4|
D: A:3|J:3|P:3|R:3|W:3|Y:3|Z:3|
E: C:4|T:4|
I: L:8|S:8|
F: E:5|I:5|K:5|
C: G:2|Q:2|V:2|
F: E:5|I:5|K:5|
D: A:3|J:3|P:3|R:3|W:3|Y:3|Z:3|
 
***** Results mapped. Untangling possible solutions *****
 
Answer: you can probably crack this but I think it is pretty clever 
****************************************************
 Time: [0.058 seconds]
```
### *Windtalker.java*
This class encrypts text using a 1:1 mapping of every alphabetic character to another character selected from a randomly assembled alphabet. Single sentences can be encryped an decrypted in less than 100ms. Tested up to 200 words, and maximum elapsed encryption/decryption time is >200ms. 
