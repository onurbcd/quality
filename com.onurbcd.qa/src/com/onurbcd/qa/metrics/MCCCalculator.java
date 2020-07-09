package com.onurbcd.qa.metrics;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.BodyDeclaration;

import com.onurbcd.qa.util.StringUtil;

/**
 * I'm not sure if this will answer your question, but for calculating McCabe's
 * Cyclomatic Complexity (McCC) metric, you don't need to care about if-else-if
 * nesting levels. You simply need to count the number of "branching"
 * instructions and add 1 in the end. See the definition in the User's Guide of
 * our SourceMeter tool:
 * 
 * McCabe's Cyclomatic Complexity (McCC) Method: complexity of the method
 * expressed as the number of independent control flow paths in it. It
 * represents a lower bound for the number of possible execution paths in the
 * source code and at the same time it is an upper bound for the minimum number
 * of test cases needed for achieving full branch test coverage. The value of
 * the metric is calculated as the number of the following instructions plus 1:
 * if, for, foreach, while, do-while, case label (which belongs to a switch
 * instruction), catch, conditional statement (?:). Moreover, logical â€œandâ€� (&&)
 * and logical â€œorâ€� (||) expressions also add 1 to the value because their
 * short-circuit evaluation can cause branching depending on the first operand.
 * The following instructions are not included: else, switch, default label
 * (which belongs to a switch instruction), try, finally.
 * 
 * @author bmiguellei
 *
 * @see https://stackoverflow.com/questions/29039524/implementing-a-metric-suite-using-astparser-in-java
 */
public class MCCCalculator {
	
	private static final String AND = "&&";
	
	private static final String OR = "||";
	
	private static final String LAMBDA_FOR_EACH = ".forEach(";

	private MCCCalculator() {
	}

	@SuppressWarnings("deprecation")
	public static int calculate(String methodSource, String sourceCode) {
		if (methodSource == null || methodSource.trim().isEmpty() || sourceCode == null || sourceCode.trim().isEmpty()) {
			return 0;
		}

		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(methodSource.toCharArray());
		parser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
		BodyDeclaration bodyDeclaration = (BodyDeclaration) parser.createAST(null);
		MCCVisitor mccVisitor = new MCCVisitor();
		bodyDeclaration.accept(mccVisitor);
		int mcc = mccVisitor.getMcc();
		int and = StringUtil.countOccurrences(sourceCode, AND);
		int or = StringUtil.countOccurrences(sourceCode, OR);
		int lambdaForEach = StringUtil.countOccurrences(sourceCode, LAMBDA_FOR_EACH);
		return mcc + and + or + lambdaForEach + 1;
	}
}
