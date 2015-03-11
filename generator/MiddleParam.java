
public class MiddleParam {
    public final String name;
    public final MiddleType type;
    
    public MiddleParam(String typeString, String nameString) {
        typeString = typeString.trim();
        nameString = nameString.trim();
        this.type = getType(typeString);
        this.name = nameString;
    }
    
    private MiddleType getType(String typeString) {
        switch(typeString) {
            case "void":
                return MiddleType.VoidType;
            case "int":
                return MiddleType.IntType;
        }
        throw new RuntimeException("Wrong type String: " + typeString);
    }
    
    public String toString() {
        return type + " " + name;
    }
}
