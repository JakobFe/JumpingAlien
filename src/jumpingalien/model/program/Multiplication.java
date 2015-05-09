package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class Multiplication extends DoubleBinaryOperator {

	protected Multiplication(SourceLocation sourceLocation,
			Double leftOperand, Double rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}
	
	@Override
	public String getOperatorSymbol() {
		return "*";
	}

	@Override
	public Number outcome() {
		return getLeftOperand().doubleValue() * getRightOperand().doubleValue();
	}

}
