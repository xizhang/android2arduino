import java.util.ArrayList;
import java.util.List;


public class MiddleObject {
    public String name;
    public int id;
    public List<MiddleMethod> methods = new ArrayList<>();
    public List<MiddleMethod> constructors = new ArrayList<>();
    
    public MiddleObject(String name, int id) {
        this.name = name.trim();
        this.id = id;
    }
    
    public void addMethod(MiddleMethod method) {
        this.methods.add(method);
    }
    
    // TODO: make this generic if there are objects have empty constructors
    public void addDefaultConstructor(int id) {
        MiddleMethod method = new MiddleMethod(id);
        method.name = this.name;
        this.constructors.add(method);
    }
    
    public String toString() {
        String result = "";
        result += "id: " + id + " name: " + name + "\n";
        for (MiddleMethod constructor : constructors) {
            result += "Constructor: " + constructor.toString() + "\n";
        }
        for (MiddleMethod method : methods) {
            result += "member method: " + method.toString() + "\n";
        }
        return result;
    }
    
}