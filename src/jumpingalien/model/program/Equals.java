package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class Equals extends BinaryOperator {

	protected Equals(SourceLocation sourceLocation, Expression leftOperand,
			Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}

	@Override
	public Boolean outcome() {
		return getLeftOperand().equals(getRightOperand());
	}

	@Override
	public String getOperatorSymbol() {
		return "==";
	}

}
