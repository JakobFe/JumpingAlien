package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class LessThanOrEqualTo extends BinaryOperator {

	protected LessThanOrEqualTo(SourceLocation sourceLocation, Expression leftOperand,
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
