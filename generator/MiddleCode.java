
import java.util.ArrayList;
import java.util.List;

public class MiddleCode {
    
    private static final String METHOD = "method";
    private static final String OBJECT = "object";
    private static final String OBJECT_METHOD = "object_method";
    private static final String EVENT = "event";
    
    List<MiddleMethod> methods = new ArrayList<>();
    
    List<MiddleObject> objects = new ArrayList<>();
    
    List<MiddleEvent> events = new ArrayList<>();
    
    public MiddleCode() {
        List<String> lines = FileUtils.loadInterfaceArray();
        int id = 0;
        for (String line : lines) {
            if (line.startsWith("//")) {
                // Skips comments
                continue;
            }
            String[] keywords = line.split(" ");
            switch (keywords[0]) {
                case METHOD:
                    methods.add(new MiddleMethod(line, id++));
                    break;
                case OBJECT:
                    String newObjectName = keywords[1];
                    if (getObjectByName(newObjectName) != null) {
                        throw new RuntimeException("Object " + newObjectName + " already exists!");
                    }
                    MiddleObject newObject = new MiddleObject(newObjectName, id++);
                    newObject.addDefaultConstructor(id++);
                    objects.add(newObject);
                    break;
                case OBJECT_METHOD:
                    String objectName = keywords[1];
                    MiddleObject o = getObjectByName(objectName);
                    if (o == null) {
                        throw new RuntimeException("Cannot find object: " + objectName);
                    }
                    // Hacky here for getting the method string
                    o.addMethod(new MiddleMethod(line.substring((OBJECT_METHOD).length() + 1), id++));
                    break;
                case EVENT:
                    MiddleEvent event = new MiddleEvent(keywords[1], id++);
                    break;
            }
        }
    }
    
    public String toString() {
        String result = "";
        for (MiddleMethod method : methods) {
            result += "method: " + method.toString() + "\n";
        }
        for (MiddleObject o : objects) {
            result += "object: " + o.toString() + "\n";
        }
        for (MiddleEvent event : events) {
            result += "event: " + event.toString() + "\n";
        }
        return result;
    }
    
    private MiddleObject getObjectByName(String name) {
        name = name.trim();
        for (MiddleObject o : objects) {
            if (o.name.equals(name)) {
                return o;
            }
        }
        return null;
    }
}