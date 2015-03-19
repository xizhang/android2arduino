import java.util.List; 

public class AndroidGenerator {
    
    private MiddleCode mCode;
    public static final String TAB = "    ";
    
    public AndroidGenerator(MiddleCode code) {
        mCode = code;
    }
    
    public void run() {
        // Generate code
        String arduinoTemplate = FileUtils.loadTemplateAsString("android/ArduinoTemplate.java");
        String arduinoString = arduinoTemplate.replace("// _PLACE_HOLDER_", generate());
        FileUtils.writeStringToGen("android/Arduino.java", arduinoString);
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
    
    
    private String generateObject(MiddleObject o, String prefix) {
        String memberString = prefix + TAB + "public final int objectId = nextObjectId();\n";
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
                generateByteBuffer(method.id, method.params, TAB + prefix, isMember) +
                prefix + TAB + returnPrefixString + "mTransceiver.%s(byteBuffer.array());\n" +
                generateReturnString(prefix + TAB, method.returnType) +
                prefix + "}\n", 
                returnTypeString, 
                method.name, 
                generateParams(method.params),
                sendMethodName);
    }
    
    private String generateByteBuffer(int id, List<MiddleParam> params, String prefix, boolean isMember) {
        int byteSize = getByteSize(params, isMember);
        String result = prefix + "ByteBuffer byteBuffer = ByteBuffer.allocate(" + byteSize + ");\n";
        result += prefix + "byteBuffer.putInt(" + id + ");\n";
        if (isMember) {
            result += prefix + "byteBuffer.putInt(this.objectId);\n";
        }
        for (MiddleParam param : params) {
            result += prefix + "byteBuffer." + getGetByte(param.type) + "(" + param.name + ");\n";
        }
        return result;
    }
    
    private int getByteSize(List<MiddleParam> params, boolean isMember) {
        int result = 0;
        for (int i = 0; i < params.size(); i++) {
            result += getTypeSize(params.get(i).type);
        }
        if (isMember) {
            result += getTypeSize(MiddleType.IntType);
        }
        return result + getTypeSize(MiddleType.IntType);
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
    
    private int getTypeSize(MiddleType type) {
        switch (type) {
            case IntType:
                return 4;
        }
        throw new RuntimeException("unknown type " + type);
    }
    
        private String getGetByte(MiddleType type) {
        switch (type) {
            case IntType:
                return "getInt";
        }
        throw new RuntimeException("unknown type " + type);
    }
    
    private String generateReturnString(String prefix, MiddleParam param) {
        if (param == null) {
            return "";
        }
        switch (param.type) {
            case IntType:
                return prefix + "return ByteBuffer.wrap(bytes).getInt();\n";
        }
        return "";
    }
    
}