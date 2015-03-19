

public class Generate {
    
    public static void main(String[] args) {
        MiddleCode code = new MiddleCode();
        AndroidGenerator android = new AndroidGenerator(code);
        android.run();
        ArduinoGenerator arduino = new ArduinoGenerator(code);
        arduino.run();
        //System.out.println(arduino.generate());
    }
    
}