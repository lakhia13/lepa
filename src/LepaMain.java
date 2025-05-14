package lepa;

import java.io.*;
import lepa.parser.LepaLexer;
import lepa.parser.LepaParser;
import java_cup.runtime.Symbol;

public class LepaMain {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java lepa.LepaMain <source-file>");
            return;
        }
        File file = new File(args[0]);
        try (Reader reader = new BufferedReader(new FileReader(file))) {
            // Create a lexer that reads from the specified file
            LepaLexer lexer = new LepaLexer(reader);
            // Create a parser with the lexer
            LepaParser parser = new LepaParser(lexer);
            // Parse the input file
            Symbol result = parser.parse();
            System.out.println("Parsing completed successfully.\nParse result: " + result.value);
        } catch (Exception e) {
            System.err.println("Parsing failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
