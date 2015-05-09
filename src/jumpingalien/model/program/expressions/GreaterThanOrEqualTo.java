package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public class GreaterThanOrEqualTo extends BinaryOperator {

	protected GreaterThanOrEqualTo(SourceLocation sourceLocation, Expression leftOperand,
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