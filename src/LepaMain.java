// No package needed

import java.io.*;
import java.nio.file.*;
import javax.tools.*;
import java.util.*;

import parser.LepaLexer;
import parser.LepaParser;
import ast.Program;
//import java_cup.runtime.Symbol;
//
public class LepaMain {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java LepaMain <source-file>");
            return;
        }
        File file = new File(args[0]);
        try (Reader reader = new BufferedReader(new FileReader(file))) {
            // Step 1: Parse the input file
            System.out.println("Parsing LEPA source file: " + file.getName());
            LepaLexer lexer = new LepaLexer(reader);
            LepaParser parser = new LepaParser(lexer);
            // Comment out debug mode and use regular parse
            // parser.debug_parse();
            Symbol result = parser.parse();
            
            if (result == null || result.value == null) {
                throw new RuntimeException("Parser returned null result");
            }
            
            Program program = (Program) result.value;
            System.out.println("Parsing completed successfully.");
            
            // Step 2: Generate Java code
            String javaCode = program.generateJavaCode();
            String outputFilename = getOutputFilename(file.getName());
            
            System.out.println("Generating Java code: " + outputFilename);
            Files.write(Paths.get(outputFilename), javaCode.getBytes());
            
            // Step 3: Compile the generated Java code
            System.out.println("Compiling generated Java code...");
            boolean compiled = compileJavaCode(outputFilename);
            
            if (!compiled) {
                System.err.println("Compilation failed.");
                return;
            }
            
            // Step 4: Run the compiled code
            System.out.println("Running the compiled program...");
            runCompiledCode(getClassname(outputFilename));
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Derives the output Java filename from the LEPA filename.
     */
    private static String getOutputFilename(String lepaFilename) {
        // Always output to LepaProgram.java regardless of input filename
        return "LepaProgram.java";
    }
    
    /**
     * Gets the class name from a Java filename.
     */
    private static String getClassname(String javaFilename) {
        // Always use LepaProgram as the class name
        return "LepaProgram";
    }
    
    /**
     * Compiles a Java source file using the Java Compiler API.
     */
    private static boolean compileJavaCode(String javaFilename) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("No Java compiler found. Make sure you're running with JDK, not JRE.");
            return false;
        }
        
        // Add runtime classes to classpath
        String classpath = System.getProperty("java.class.path") + File.pathSeparator + ".";;
        
        List<String> options = Arrays.asList(
            "-classpath", classpath
        );
        
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits = 
            fileManager.getJavaFileObjectsFromStrings(Arrays.asList(javaFilename));
            
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaCompiler.CompilationTask task = compiler.getTask(
            null, fileManager, diagnostics, options, null, compilationUnits);
            
        boolean success = task.call();
        
        if (!success) {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.err.format("Error on line %d in %s: %s%n",
                    diagnostic.getLineNumber(),
                    diagnostic.getSource().toUri(),
                    diagnostic.getMessage(null));
            }
        }
        
        try {
            fileManager.close();
        } catch (IOException e) {
            System.err.println("Error closing file manager: " + e.getMessage());
        }
        
        return success;
    }
    
    /**
     * Runs a compiled Java class using ProcessBuilder.
     */
    private static void runCompiledCode(String className) throws IOException, InterruptedException {
        String javaExe = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path") + File.pathSeparator + ".";
        
        ProcessBuilder pb = new ProcessBuilder(javaExe, "-cp", classpath, className);
        pb.inheritIO();
        Process process = pb.start();
        int exitCode = process.waitFor();
        
        if (exitCode != 0) {
            System.err.println("Program execution failed with exit code: " + exitCode);
        }
    }
}
