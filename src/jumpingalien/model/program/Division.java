package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class Division extends BinaryOperator {

	protected Division(SourceLocation sourceLocation,
			Expression leftOperand, Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}
	
	@Override
	public String getOperatorSymbol() {
		return "/";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Number outcome() {
		return ((Constant<Double>) getLeftOperand()).outcome() / 
				((Constant<Double>)getRightOperand()).outcome();
	}

}
