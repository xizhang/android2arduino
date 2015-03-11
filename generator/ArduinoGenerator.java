
public class ArduinoGenerator {
    
    MiddleCode mCode;
    public static final String TAB = "  ";
    
    public ArduinoGenerator(MiddleCode code) {
        mCode = code;
    }
    
    public String generate() {
        String result = "void OnActionRecevied(int id) {\n";
        result += TAB + "switch(id) {\n";
        
        for (MiddleMethod method : mCode.methods) {
            result += generateMethod(method, TAB + TAB);
        }
        
        /*
        for (MiddleObject o : mCode.objects) {
            result += generateObject(o, TAB);
        }*/
        result += TAB + "}\n";
        result += "}\n";
        return result;
    }
    
    private String generateObject(MiddleObject object) {
        String result = "";
        for (MiddleMethod method : object.constructors) {
            
        }
        return result;
    }
    
    private String generateMethod(MiddleMethod method, String prefix) {
        return generateMethod(method, prefix, null);
    }
    
    private String generateMethod(MiddleMethod method, String prefix, String objectName) {
        String result = "";
        boolean isMember = objectName != null;
        String returnValueString = "";
        if (method.hasReturnValue()) {
            returnValueString =  generateType(method.returnType.type) + " returnValue = ";
        }
        
        result += prefix + "case " + method.id + ":\n";
        String methodParamString = "";
        for (int i = 0; i < method.params.size(); i++) {
            MiddleParam param = method.params.get(i);
            result += prefix + TAB + generateType(param.type) + " " + param.name 
                    + " = " + getTypeReader(param.type) + "();\n";    
            methodParamString += param.name;
            if (i < method.params.size() - 1) {
                methodParamString += ", ";
            }
        }
        result += prefix + TAB + returnValueString + method.name + "(" + methodParamString + ");\n";
        result += prefix + TAB + "break;\n";
        return result;
    }

    private String generateType(MiddleType type) {
        switch (type) {
            case IntType:
                return "int";
            case VoidType:
                return "void";
        }
        throw new RuntimeException("unknown type " + type);
    }
    
    private String getTypeReader(MiddleType type) {
        switch (type) {
            case IntType:
                return "ReadInt";
        }
        throw new RuntimeException("unknown type " + type);
    }
}