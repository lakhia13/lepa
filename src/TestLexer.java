import java.io.FileReader;
import java.io.StringReader;
import parser.LepaLexer;
import java_cup.runtime.Symbol;
import parser.sym;

public class TestLexer {
    public static void main(String[] args) {
        try {
            String filename = "minimal.lepa";
            LepaLexer lexer = new LepaLexer(new FileReader(filename));
            Symbol token;
            
            System.out.println("Tokens from " + filename + ":");
            while ((token = lexer.next_token()).sym != sym.EOF) {
                System.out.println("Token: " + token.sym + " (" + symbolName(token.sym) + ") Value: " + token.value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String symbolName(int sym) {
        switch (sym) {
            case sym.THEOREM: return "THEOREM";
            case sym.PROOF: return "PROOF";
            case sym.QED: return "QED";
            case sym.ASSUME: return "ASSUME";
            case sym.END: return "END";
            case sym.TRUE: return "TRUE";
            case sym.FALSE: return "FALSE";
            case sym.BY: return "BY";
            case sym.FROM: return "FROM";
            case sym.THEREFORE: return "THEREFORE";
            case sym.AND: return "AND";
            case sym.OR: return "OR";
            case sym.NOT: return "NOT";
            case sym.IMPLIES: return "IMPLIES";
            case sym.IFF: return "IFF";
            case sym.LPAREN: return "LPAREN";
            case sym.RPAREN: return "RPAREN";
            case sym.LBRACE: return "LBRACE";
            case sym.RBRACE: return "RBRACE";
            case sym.COMMA: return "COMMA";
            case sym.DOT: return "DOT";
            case sym.COLON: return "COLON";
            case sym.SETDIFF: return "SETDIFF";
            case sym.NUMBER: return "NUMBER";
            case sym.IDENTIFIER: return "IDENTIFIER";
            case sym.IN: return "IN";
            case sym.NOTIN: return "NOTIN";
            case sym.SUBSET: return "SUBSET";
            case sym.UNION: return "UNION";
            case sym.INTERSECT: return "INTERSECT";
            case sym.FORALL: return "FORALL";
            case sym.EXISTS: return "EXISTS";
            default: return "UNKNOWN";
        }
    }
}