package ast;

import java.util.List;

/**
 * Represents a quantified formula (forall, exists) in LEPA.
 */
public class Quantifier extends Formula {
    public enum Type {
        FORALL,
        EXISTS
    }
    
    private Type type;
    private List<Identifier> variables;
    private Formula body;
    
    public Quantifier(Type type, List<Identifier> variables, Formula body) {
        this.type = type;
        this.variables = variables;
        this.body = body;
    }
    
    public Type getType() {
        return type;
    }
    
    public List<Identifier> getVariables() {
        return variables;
    }
    
    public Formula getBody() {
        return body;
    }
    
    @Override
    public String generateJavaCode() {
        // Java doesn't have direct support for quantifiers, so we need to simulate them
        // This is a simplified implementation that will need refinement
        StringBuilder code = new StringBuilder();
        
        if (type == Type.FORALL) {
            // For "forall", we need to check if the formula holds for all elements
            code.append("LepaRuntime.forAll(");
        } else { // EXISTS
            // For "exists", we need to check if the formula holds for at least one element
            code.append("LepaRuntime.exists(");
        }
        
        // Build a lambda expression to represent the quantified formula
        code.append("(vars) -> {");
        
        // Extract variables from the vars array
        for (int i = 0; i < variables.size(); i++) {
            String varType = "Object"; // We'll need better type inference
            code.append(varType).append(" ").append(variables.get(i).getName())
                .append(" = vars[").append(i).append("];\n");
        }
        
        // Add the body of the formula
        code.append("return ").append(body.generateJavaCode()).append(";");
        code.append("})");
        
        return code.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Quantifier[");
        sb.append(type).append(" ");
        for (Identifier var : variables) {
            sb.append(var).append(", ");
        }
        if (!variables.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(": ").append(body).append("]");
        return sb.toString();
    }
}
