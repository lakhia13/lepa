package ast;

/**
 * Represents a step in a proof in LEPA.
 */
public class ProofStep extends ASTNode {
    private Formula formula;
    private String justification; // Optional, can be null
    private boolean isAssumption;
    private boolean isTherefore;
    
    public ProofStep(Formula formula, String justification, boolean isAssumption, boolean isTherefore) {
        this.formula = formula;
        this.justification = justification;
        this.isAssumption = isAssumption;
        this.isTherefore = isTherefore;
    }
    
    public Formula getFormula() {
        return formula;
    }
    
    public String getJustification() {
        return justification;
    }
    
    public boolean isAssumption() {
        return isAssumption;
    }
    
    public boolean isTherefore() {
        return isTherefore;
    }
    
    @Override
    public String generateJavaCode() {
        StringBuilder code = new StringBuilder();
        
        if (isAssumption) {
            // For assumptions, we don't need to verify them; we just use them
            code.append("// Assuming: ").append(formula.toString()).append("\n");
            // Store the assumption value for later use
            String varName = "assumption_" + hashCode();
            code.append("boolean ").append(varName).append(" = ").append(formula.generateJavaCode()).append(";");
        } else if (isTherefore) {
            // 'Therefore' statements are assertions based on previous proof steps
            code.append("// Therefore: ").append(formula.toString()).append("\n");
            code.append("assert ").append(formula.generateJavaCode()).append(" : \"Failed assertion: ").append(formula.toString()).append("\";");
        } else {
            // Regular proof steps establish intermediate results
            code.append("// Proof step: ").append(formula.toString()).append("\n");
            String varName = "step_" + hashCode();
            code.append("boolean ").append(varName).append(" = ").append(formula.generateJavaCode()).append(";");
        }
        
        return code.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ProofStep[");
        if (isAssumption) {
            sb.append("ASSUME ");
        } else if (isTherefore) {
            sb.append("THEREFORE ");
        }
        sb.append(formula);
        if (justification != null) {
            sb.append(" BY ").append(justification);
        }
        sb.append("]");
        return sb.toString();
    }
}
