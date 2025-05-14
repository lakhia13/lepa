package ast;

import java.util.List;

/**
 * Represents a set literal in LEPA, like {1, 2, 3}.
 */
public class SetLiteral extends Formula {
    private List<Formula> elements;
    
    public SetLiteral(List<Formula> elements) {
        this.elements = elements;
    }
    
    public List<Formula> getElements() {
        return elements;
    }
    
    @Override
    public String generateJavaCode() {
        StringBuilder code = new StringBuilder();
        code.append("runtime.LepaRuntime.set(");
        
        for (int i = 0; i < elements.size(); i++) {
            if (i > 0) {
                code.append(", ");
            }
            code.append(elements.get(i).generateJavaCode());
        }
        
        code.append(")");
        return code.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SetLiteral[{");
        for (int i = 0; i < elements.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(elements.get(i));
        }
        sb.append("}]");
        return sb.toString();
    }
}
