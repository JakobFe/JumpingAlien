package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class LessThan extends BinaryOperator {

	protected LessThan(SourceLocation sourceLocation, Expression leftOperand,
			Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}

	@Override
	public Boolean outcome() {
		return (Double)getLeftOperand().outcome() < (Double)getRightOperand().outcome();
	}

	@Override
	public String getOperatorSymbol() {
		return "<";
	}

}
