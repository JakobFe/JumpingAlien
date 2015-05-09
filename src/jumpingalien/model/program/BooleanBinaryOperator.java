package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public abstract class BooleanBinaryOperator extends BinaryOperator {

	protected BooleanBinaryOperator(SourceLocation sourceLocation,
			Object leftOperand, Object rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}

	public abstract Boolean outcome();

}
