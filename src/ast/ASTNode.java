package ast;

/**
 * Base class for all AST nodes in the LEPA compiler.
 */
public abstract class ASTNode {
    
    /**
     * Generates Java code for this AST node.
     * 
     * @return A string containing the generated Java code.
     */
    public abstract String generateJavaCode();
    
    /**
     * Returns a string representation of the AST node, useful for debugging.
     */
    @Override
    public abstract String toString();
}