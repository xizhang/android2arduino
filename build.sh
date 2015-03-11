cd generator
echo "Building generators..."
rm *.class
javac *.java
echo "Running generators..."
java Generate
cd ..