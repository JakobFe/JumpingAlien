package jumpingalien.model.program.expressions.binaryexpression;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class GreaterThanOrEqualTo extends BinaryOperator {

	public GreaterThanOrEqualTo(SourceLocation sourceLocation, Expression leftOperand,
			Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}

	@Override
	public Boolean outcome() {
		return (Double)getLeftOperand().outcome() >= (Double)getRightOperand().outcome();
	}

	@Override
	public String getOperatorSymbol() {
		return ">=";
	}

}