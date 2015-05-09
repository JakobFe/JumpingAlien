package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class Multiplication extends BinaryOperator {

	protected Multiplication(SourceLocation sourceLocation,
			Double leftOperand, Double rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}
	
	public Double getLeftOperand() {
		return (Double) super.getLeftOperand();
	}
	
	public Double getRightOperand() {
		return (Double) super.getRightOperand();
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
