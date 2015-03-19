echo "Cleaning up..."
rm generator/*.class
rm gen/android/*
rm gen/arduino/*
echo "Building generators..."
cd generator
javac *.java
echo "Running generators..."
java Generate
cd ..
echo "Building java code..."
javac gen/android/*.java
echo "Building c++ code..."
gcc gen/arduino/android.cpp -o gen/arduino/android.out
echo "Done"