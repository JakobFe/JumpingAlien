package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public class GreaterThan extends BinaryOperator {

	public GreaterThan(SourceLocation sourceLocation, Expression leftOperand,
			Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}

	@Override
	public Boolean outcome() {
		return (Double)getLeftOperand().outcome() > (Double)getRightOperand().outcome();
	}

	@Override
	public String getOperatorSymbol() {
		return ">";
	}

}
