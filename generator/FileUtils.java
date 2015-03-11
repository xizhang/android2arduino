import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
	
	
    private static final String SRC_PATH = "../source/android/";
    private static final String DES_PATH = "../gen/android/";
    
	
    public static String loadTemplateAsString(String fileName) {
    	try {
        	byte[] encoded = Files.readAllBytes(Paths.get(SRC_PATH + fileName));
        	return new String(encoded, "UTF-8");
    	} catch (Exception e) {
    		throw new RuntimeException(e.toString());
    	}
    }
    
    public static List<String> loadInterfaceArray() {
        List<String> result = new ArrayList<>();
        try {
			File file = new File("../source/interface.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
			    result.add(line);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
    }
}