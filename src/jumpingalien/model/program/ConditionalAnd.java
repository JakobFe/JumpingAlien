package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class ConditionalAnd extends BooleanBinaryOperator {

	protected ConditionalAnd(SourceLocation sourceLocation, Boolean leftOperand,
			Boolean rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}
	

	@Override
	public Boolean outcome() {
		return (Boolean)getLeftOperand() && (Boolean)getRightOperand();
	}

	@Override
	public String getOperatorSymbol() {
		return "&&";
	}

}
