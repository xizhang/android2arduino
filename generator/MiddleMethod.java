

import java.util.ArrayList;
import java.util.List;


public class MiddleMethod {
    public String name;
    public int id;
    public List<MiddleParam> params;
    public MiddleParam returnType;
    
    public MiddleMethod(String line, int id) {
        line = line.trim();
        this.id = id;
        
        // Parse method name and return type.
        String first = line.substring(0, line.indexOf("("));
        String[] firsts = first.split(" ");
        this.returnType = new MiddleParam(firsts[1], "");
        this.name = firsts[2];
        
        // Parse params.
        String second = line.substring(line.indexOf("(") + 1).replace(")", "");
        this.params = new ArrayList<>();
        if (!second.isEmpty()) {
            for (String paramString : second.split(",")) {
                String[] paramStrings = paramString.split(" ");
                params.add(new MiddleParam(paramStrings[0], paramStrings[1]));
            }
        }
    }
    
    /**
     * Creates a defult object constructor with empty params
     */
    // TODO make this generic if there is objects with params.
    public MiddleMethod(int id) {
        this.params = new ArrayList<>();
        this.id = id;
    }
    
    public boolean hasReturnValue() {
        return returnType != null && returnType.type != MiddleType.VoidType;
    }
    
    public String toString() {
        String result = "";
        result += "id:" + id + " ";
        result += "name:" + name + " ";
        result += "type:" + returnType + " ";
        result += "params: ";
        for (MiddleParam param : params) {
            result += param.toString() + " ";
        }
        return result;
    }
}