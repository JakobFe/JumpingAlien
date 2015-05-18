package jumpingalien.model.program.expressions.binaryexpression;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class NotEquals extends BinaryOperator {

	public NotEquals(SourceLocation sourceLocation, Expression leftOperand,
			Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}

	@Override
	public Boolean outcome() {
		return !(getLeftOperand().equals(getRightOperand()));
	}

	@Override
	public String getOperatorSymbol() {
		return "!=";
	}

}