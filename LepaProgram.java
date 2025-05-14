import java.util.*;

public class LepaProgram {
	public static void main(String[] args) {
		System.out.println("LEPA Program Execution");
		System.out.println("Verifying theorem: ExcludedMiddle");
		boolean result = verifyExcludedMiddle();
		System.out.println("Result: " + result);
	}

	public static boolean verifyExcludedMiddle() {
		// Declare variables for assumptions and intermediate results
		boolean result = false;
		// Proof step: BooleanLiteral[true]
		boolean step_225534817 = true;
		// Therefore: BinaryOperation[BooleanLiteral[true] OR UnaryOperation[NOT BooleanLiteral[false]]]
		assert (true || !(false)) : "Failed assertion: BinaryOperation[BooleanLiteral[true] OR UnaryOperation[NOT BooleanLiteral[false]]]";
		result = (true || !(false));
		return result;
	}


}
