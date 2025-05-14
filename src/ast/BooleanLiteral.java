package ast;

/**
 * Represents a boolean literal (true/false) in LEPA.
 */
public class BooleanLiteral extends Formula {
    private boolean value;
    
    public BooleanLiteral(boolean value) {
        this.value = value;
    }
    
    public boolean getValue() {
        return value;
    }
    
    @Override
    public String generateJavaCode() {
        return String.valueOf(value);
    }
    
    @Override
    public String toString() {
        return "BooleanLiteral[" + value + "]"; 
    }
}
