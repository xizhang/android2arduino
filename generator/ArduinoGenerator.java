
public class ArduinoGenerator {
    
    MiddleCode mCode;
    public static final String TAB = "    ";
    
    public ArduinoGenerator(MiddleCode code) {
        mCode = code;
    }
    
    public void run() {
        // Generate code
        String template = FileUtils.loadTemplateAsString("arduino/android_template.cpp");
        String switchString = template.replace("// _BIG_SWITCH_", generateBigSwitch());
        FileUtils.writeStringToGen("arduino/android.cpp", switchString);
    }
     
    
    public String generateBigSwitch() {
        String result = "\n";
        result += TAB + "switch(id) {\n";
        
        for (MiddleMethod method : mCode.methods) {
            result += generateMethod(method, TAB + TAB);
        }
        
        /*
        for (MiddleObject o : mCode.objects) {
            result += generateObject(o, TAB);
        }*/
        result += TAB + "}\n";
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
            returnValueString =  generateType(method.returnType.type) + " returnValue" + method.id + " = ";
        }
        
        result += prefix + "case " + method.id + ": {\n";
        String methodParamString = "";
        for (int i = 0; i < method.params.size(); i++) {
            MiddleParam param = method.params.get(i);
            result += prefix + TAB + generateType(param.type) + " " + param.name + method.id
                    + " = " + getTypeReader(param.type) + "();\n";    
            methodParamString += param.name + method.id;
            if (i < method.params.size() - 1) {
                methodParamString += ", ";
            }
        }
        result += prefix + TAB + returnValueString + method.name + "(" + methodParamString + ");\n";
        result += prefix + TAB + "break;\n";
        result += prefix + "}\n";
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