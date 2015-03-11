

public class Generate {
    
    public static void main(String[] args) {
        MiddleCode code = new MiddleCode();
        AndroidGenerator android = new AndroidGenerator(code);
        ArduinoGenerator arduino = new ArduinoGenerator(code);
    }
    
}