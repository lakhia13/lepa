import java.io.FileReader;
import parser.LepaLexer;
import java_cup.runtime.Symbol;
import parser.sym;

public class SimpleLexerTest {
    public static void main(String[] args) {
        try {
            String filename = "minimal.lepa";
            System.out.println("Testing file: " + filename);
            
            LepaLexer lexer = new LepaLexer(new FileReader(filename));
            Symbol token;
            
            while (true) {
                token = lexer.next_token();
                if (token.sym == sym.EOF) break;
                
                System.out.println("Token ID: " + token.sym + ", Value: " + token.value);
            }
            
            System.out.println("Lexical analysis complete");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}