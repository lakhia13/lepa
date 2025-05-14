package ast;

import java.util.List;

/**
 * Represents a function call in LEPA, like f(x, y, z).
 */
public class FunctionCall extends Formula {
    private String functionName;
    private List<Formula> arguments;
    
    public FunctionCall(String functionName, List<Formula> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }
    
    public String getFunctionName() {
        return functionName;
    }
    
    public List<Formula> getArguments() {
        return arguments;
    }
    
    @Override
    public String generateJavaCode() {
        StringBuilder code = new StringBuilder();
        
        // For simplicity, we'll just call a function with the same name
        // In a real implementation, this would need more sophisticated handling
        code.append("runtime.LepaFunctions.").append(functionName).append("(");
        
        for (int i = 0; i < arguments.size(); i++) {
            if (i > 0) {
                code.append(", ");
            }
            code.append(arguments.get(i).generateJavaCode());
        }
        
        code.append(")");
        return code.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FunctionCall[");
        sb.append(functionName).append("(");
        for (int i = 0; i < arguments.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(arguments.get(i));
        }
        sb.append(")]");
        return sb.toString();
    }
}
