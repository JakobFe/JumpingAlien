package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class LessThanOrEqualTo extends BinaryOperator {

	protected LessThanOrEqualTo(SourceLocation sourceLocation, Double leftOperand,
			Double rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}

	@Override
	public Boolean outcome() {
		return (Double)getLeftOperand() <= (Double)getRightOperand();
	}

	@Override
	public String getOperatorSymbol() {
		return "<=";
	}

}
