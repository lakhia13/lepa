import java.io.FileReader;
import java.io.IOException;
import java_cup.runtime.Symbol;
import parser.LepaLexer;

public class LexerTest {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java LexerTest <file-to-test>");
            return;
        }
        
        try (FileReader reader = new FileReader(args[0])) {
            LepaLexer lexer = new LepaLexer(reader);
            Symbol token;
            
            while ((token = lexer.next_token()) != null) {
                System.out.println("Token type: " + token.sym + ", Value: " + token.value);
                if (token.sym == parser.sym.EOF) break;
            }
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error scanning tokens: " + e.getMessage());
            e.printStackTrace();
        }
    }
}