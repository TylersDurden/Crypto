#!/usr/bin
echo 'Attempting to get Project from git onto USB'
echo 'From there, Need to compile and run it (all on USB).'

# Create a live running directory on the USB
cd /media/root/UNTITLED/CRYPTO

# Fetch Crypto Repository from GitHub 
git config --global user.name "{}"
git config --global user.email "{}"
git clone https://www.github.com/TylersDurden/Crypto

#git location: cd /media/root/UNTITLED/CRYPTO/Crypto/src

# Before java Build, put EOWL into txt file 
# (it will be zipped for portability) 
cd /media/root/UNTITLED/Crypto2.0/src/DATA
chmod +x install.sh 
su root ./install.sh
# Now put unzipped files into a txt file for reading w Java Apps
cd /media/root/UNTITLED/CRYPTO/Crypto/src/DATA/Words/EOWL-v1.1.2
echo 'EOWL unzipped. Relocating to a txt file...'
cd CSV\ Format
cat AWords.csv >> words.txt
cat B\ Words.csv >> words.txt
cat C\ Words.csv >> words.txt
cat D\ Words.csv >> words.txt
cat E\ Words.csv >> words.txt
cat F\ Words.csv >> words.txt
cat G\ Words.csv >> words.txt
cat H\ Words.csv >> words.txt
cat I\ Words.csv >> words.txt
cat J\ Words.csv >> words.txt
cat K\ Words.csv >> words.txt
cat L\ Words.csv >> words.txt
cat M\ Words.csv >> words.txt
cat N\ Words.csv >> words.txt
cat O\ Words.csv >> words.txt
cat P\ Words.csv >> words.txt
cat Q\ Words.csv >> words.txt
cat R\ Words.csv >> words.txt
cat S\ Words.csv >> words.txt
cat T\ Words.csv >> words.txt
cat U\ Words.csv >> words.txt
cat V\ Words.csv >> words.txt
cat W\ Words.csv >> words.txt
cat X\ Words.csv >> words.txt
cat Y\ Words.csv >> words.txt
cat Z\ Words.csv >> words.txt

# Now Build and Run Java Applications 
cd/media/root/UNTITLED/CRYPTO/Crypto/src/
chmod +x run.sh
su root ./run.sh 
