package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a theorem declaration in LEPA, which includes a name, a formula (the theorem statement),
 * and a list of proof steps.
 */
public class TheoremDecl extends ASTNode {
    private String name;
    private Formula theorem;
    private List<ProofStep> proofSteps;
    
    public TheoremDecl(String name, Formula theorem) {
        this.name = name;
        this.theorem = theorem;
        this.proofSteps = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public Formula getTheorem() {
        return theorem;
    }
    
    public void addProofStep(ProofStep step) {
        proofSteps.add(step);
    }
    
    public List<ProofStep> getProofSteps() {
        return proofSteps;
    }
    
    @Override
    public String generateJavaCode() {
        StringBuilder code = new StringBuilder();
        
        // Create a method that verifies this theorem
        code.append("\tpublic static boolean verify").append(name).append("() {\n");
        
        // Add local variables for assumptions and intermediate results
        code.append("\t\t// Declare variables for assumptions and intermediate results\n");
        code.append("\t\tboolean result = false;\n");
        
        // Generate code for each proof step
        for (ProofStep step : proofSteps) {
            code.append("\t\t").append(step.generateJavaCode().replace("\n", "\n\t\t"));
            code.append("\n");
        }
        
        // The final proof step should establish the theorem
        if (!proofSteps.isEmpty()) {
            code.append("\t\tresult = ").append(proofSteps.get(proofSteps.size() - 1).getFormula().generateJavaCode()).append(";\n");
        } else {
            // No proof steps, just verify the theorem directly
            code.append("\t\tresult = ").append(theorem.generateJavaCode()).append(";\n");
        }
        
        code.append("\t\treturn result;\n");
        code.append("\t}\n");
        
        return code.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TheoremDecl[");
        sb.append("name=").append(name);
        sb.append(", theorem=").append(theorem);
        sb.append(", proof=\n");
        for (ProofStep step : proofSteps) {
            sb.append("  ").append(step.toString().replace("\n", "\n  ")).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
