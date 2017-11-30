#!/usr/local/bin 
echo 'Compiling Java Programs'
cd /media/root/UNTITLED/Crypto2.0/src
javac Alpha.java Vocab.java FitFunction.java Linguist.java
javac TRSLTR.java Reductionist.java
echo ' < < Package Compilation Successful. > > '
java TRSLTR 
