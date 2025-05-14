package ast;

/**
 * Represents an identifier (variable name) in LEPA.
 */
public class Identifier extends Formula {
    private String name;
    
    public Identifier(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String generateJavaCode() {
        // In the generated Java code, we need to ensure the identifier
        // is a valid Java identifier
        return "_" + name;
    }
    
    @Override
    public String toString() {
        return "Identifier[" + name + "]";
    }
}
