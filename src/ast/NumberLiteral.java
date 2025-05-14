package ast;

/**
 * Represents a numeric literal in LEPA.
 */
public class NumberLiteral extends Formula {
    private int value;
    
    public NumberLiteral(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    @Override
    public String generateJavaCode() {
        return Integer.toString(value);
    }
    
    @Override
    public String toString() {
        return "NumberLiteral[" + value + "]";
    }
}
