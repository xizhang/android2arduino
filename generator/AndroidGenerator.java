import java.util.List; 

public class AndroidGenerator {
    
    private MiddleCode mCode;
    public static final String TAB = "  ";
    
    public AndroidGenerator(MiddleCode code) {
        mCode = code;
    }
    
    public void run() {
        String androidMainCode = generate();
        String mainTemplate = FileUtils.loadTemplateAsString("Arduino.java");
        String mainFileString = mainTemplate.replace("// _PLACE_HOLDER_", androidMainCode);
        System.out.println(mainFileString);
    }
     
    public String generate() {
        String result = "";
        for (MiddleMethod method : mCode.methods) {
            result += generateMethod(method, TAB);
        }
        for (MiddleObject o : mCode.objects) {
            result += generateObject(o, TAB);
        }
        return result;
    }
    
    private void copyFiles() {
        
    }
    
    private String generateObject(MiddleObject o, String prefix) {
        String memberString = prefix + TAB + "public final int objectId = Utils.getNextObjectId();\n";
        String constructorString = "";
        for (MiddleMethod method : o.constructors) {
            constructorString += generateMethod(method, prefix + TAB, true, true);
        }
        String methodString = "";
        for (MiddleMethod method : o.methods) {
            methodString += generateMethod(method, prefix + TAB, false, true);
        }
        return String.format("\n" +
                prefix + "public class %s {\n\n" +
                memberString +
                constructorString +
                methodString +
                prefix + "}\n",
                o.name);
    }
    
    private String generateMethod(MiddleMethod method, String prefix) {
        return generateMethod(method, prefix, false, false);
    }
    
    private String generateMethod(MiddleMethod method, String prefix, boolean isConstructor, boolean isMember) {
        String returnPrefixString = "";
        String sendMethodName = "send";
        if (method.hasReturnValue()) {
            returnPrefixString = "byte[] bytes = ";
            sendMethodName = "sendForResult";
        }
        String returnTypeString = "";
        if (!isConstructor) {
            returnTypeString =  " " + generateType(method.returnType.type);
        }
        return String.format("\n" +
                prefix + "public%s %s(%s) {\n" +
                generateDataBuilder(method.id, method.params, TAB + prefix, isMember) +
                prefix + TAB + returnPrefixString + "mTransceiver.%s(dataBuilder.getBytes());\n" +
                generateReturnString(prefix + TAB, method.returnType) +
                prefix + "}\n", 
                returnTypeString, 
                method.name, 
                generateParams(method.params),
                sendMethodName);
    }
    
    private String generateDataBuilder(int id, List<MiddleParam> params, String prefix, boolean isMember) {
        String result = prefix + "DataBuilder dataBuilder = new DataBuilder();\n";
        result += prefix + "dataBuilder.add(" + id + ");\n";
        if (isMember) {
            result += prefix + "dataBuilder.add(this.objectId);\n";
        }
        for (MiddleParam param : params) {
            result += prefix + "dataBuilder.add(" + param.name + ");\n";
        }
        return result;
    }
    
    private String generateParams(List<MiddleParam> params) {
        String result = "";
        for (int i = 0; i < params.size(); i++) {
            result += generateParam(params.get(i));
            if (i < params.size() - 1) {
                result += ", ";
            }
        }
        return result;
    }
    
    private String generateParam(MiddleParam param) {
        return generateType(param.type) + " " + param.name;
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
    
    private String generateReturnString(String prefix, MiddleParam param) {
        if (param == null) {
            return "";
        }
        switch (param.type) {
            case IntType:
                return prefix + "return DataParser.parseInt(bytes);\n";
        }
        return "";
    }
    
}