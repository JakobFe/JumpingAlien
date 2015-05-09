package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class Multiplication extends BinaryOperator {

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
		return ((Double)getLeftOperand()).doubleValue() * ((Double)getRightOperand()).doubleValue();
	}

}
