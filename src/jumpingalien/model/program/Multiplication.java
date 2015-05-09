package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class Multiplication extends BinaryOperator {

	protected Multiplication(SourceLocation sourceLocation,
			Expression leftOperand, Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}
	
	@Override
	public String getOperatorSymbol() {
		return "*";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Number outcome() {
		return ((Constant<Double>) getLeftOperand()).outcome() * 
				((Constant<Double>)getRightOperand()).outcome();
	}


}
