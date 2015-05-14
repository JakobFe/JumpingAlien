package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public class LessThanOrEqualTo extends BinaryOperator {

	public LessThanOrEqualTo(SourceLocation sourceLocation, Expression leftOperand,
			Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}

	@Override
	public Boolean outcome() {
		return (Double)getLeftOperand().outcome() <= (Double)getRightOperand().outcome();
	}

	@Override
	public String getOperatorSymbol() {
		return "<=";
	}

}