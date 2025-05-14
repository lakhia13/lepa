package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a complete LEPA program, which consists of a list of theorems.
 */
public class Program extends ASTNode {
    private List<TheoremDecl> theorems;
    
    public Program() {
        this.theorems = new ArrayList<>();
    }
    
    public void addTheorem(TheoremDecl theorem) {
        theorems.add(theorem);
    }
    
    public List<TheoremDecl> getTheorems() {
        return theorems;
    }
    
    @Override
    public String generateJavaCode() {
        StringBuilder code = new StringBuilder();
        
        // Add imports and package declaration
        code.append("import java.util.*;");
        code.append("\n\n");
        
        // Create a class to hold all theorems
        code.append("public class LepaProgram {\n");
        
        // Add a main method
        code.append("\tpublic static void main(String[] args) {\n");
        code.append("\t\tSystem.out.println(\"LEPA Program Execution\");\n");
        
        // Add calls to verify each theorem
        for (TheoremDecl theorem : theorems) {
            code.append("\t\tSystem.out.println(\"Verifying theorem: ").append(theorem.getName()).append("\");");
            code.append("\n");
            code.append("\t\tboolean result = verify").append(theorem.getName()).append("();\n");
            code.append("\t\tSystem.out.println(\"Result: \" + result);\n");
        }
        
        code.append("\t}\n\n");
        
        // Add each theorem as a separate method
        for (TheoremDecl theorem : theorems) {
            code.append(theorem.generateJavaCode());
            code.append("\n\n");
        }
        
        code.append("}\n");
        
        return code.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Program[\n");
        for (TheoremDecl theorem : theorems) {
            sb.append("  ").append(theorem.toString().replace("\n", "\n  ")).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
