package jumpingalien.model.program.expressions.binaryexpression;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class Equals extends BinaryOperator {

	public Equals(SourceLocation sourceLocation, Expression leftOperand,
			Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}

	@Override
	public Boolean outcome() {
		return getLeftOperand().outcome() == getRightOperand().outcome();
	}

	@Override
	public String getOperatorSymbol() {
		return "==";
	}
	
}
