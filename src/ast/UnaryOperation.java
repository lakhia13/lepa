package ast;

/**
 * Represents a unary operation on a formula in LEPA.
 */
public class UnaryOperation extends Formula {
    public enum Operator {
        NOT("!");
        
        private final String javaOperator;
        
        Operator(String javaOperator) {
            this.javaOperator = javaOperator;
        }
        
        public String getJavaOperator() {
            return javaOperator;
        }
    }
    
    private Formula operand;
    private Operator operator;
    
    public UnaryOperation(Operator operator, Formula operand) {
        this.operator = operator;
        this.operand = operand;
    }
    
    public Formula getOperand() {
        return operand;
    }
    
    public Operator getOperator() {
        return operator;
    }
    
    @Override
    public String generateJavaCode() {
        return operator.getJavaOperator() + "(" + operand.generateJavaCode() + ")";
    }
    
    @Override
    public String toString() {
        return "UnaryOperation[" + operator + " " + operand + "]";
    }
}
