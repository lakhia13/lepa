package ast;

/**
 * Represents a binary operation between two formulas in LEPA.
 */
public class BinaryOperation extends Formula {
    public enum Operator {
        AND("&&"),
        OR("||"),
        IMPLIES("->"), // Special handling required
        IFF("==="), // Special handling required
        EQ("=="),
        NEQ("!="),
        IN("contains"), // Special handling required
        NOTIN("!contains"), // Special handling required
        SUBSET("isSubsetOf"); // Special handling required
        
        private final String javaOperator;
        
        Operator(String javaOperator) {
            this.javaOperator = javaOperator;
        }
        
        public String getJavaOperator() {
            return javaOperator;
        }
    }
    
    private Formula left;
    private Formula right;
    private Operator operator;
    
    public BinaryOperation(Formula left, Operator operator, Formula right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public Formula getLeft() {
        return left;
    }
    
    public Formula getRight() {
        return right;
    }
    
    public Operator getOperator() {
        return operator;
    }
    
    @Override
    public String generateJavaCode() {
        switch (operator) {
            case IMPLIES:
                // A -> B is equivalent to !A || B
                return "(!" + left.generateJavaCode() + " || " + right.generateJavaCode() + ")";
            case IFF:
                // A <-> B is equivalent to A == B
                return "(" + left.generateJavaCode() + " == " + right.generateJavaCode() + ")";
            case IN:
                // Special handling for set containment
                return "LepaRuntime.contains(" + right.generateJavaCode() + ", " + left.generateJavaCode() + ")";
            case NOTIN:
                // Special handling for set non-containment
                return "!LepaRuntime.contains(" + right.generateJavaCode() + ", " + left.generateJavaCode() + ")";
            case SUBSET:
                // Special handling for subset relationship
                return "LepaRuntime.isSubset(" + left.generateJavaCode() + ", " + right.generateJavaCode() + ")";
            default:
                return "(" + left.generateJavaCode() + " " + operator.getJavaOperator() + " " + right.generateJavaCode() + ")";
        }
    }
    
    @Override
    public String toString() {
        return "BinaryOperation[" + left + " " + operator + " " + right + "]";
    }
}
