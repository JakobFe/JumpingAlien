package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public class LessThan extends BinaryOperator {

	public LessThan(SourceLocation sourceLocation, Expression leftOperand,
			Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}

	@Override
	public Boolean outcome() {
		System.out.println(getLeftOperand());
		System.out.println(getRightOperand());
		return (Double)getLeftOperand().outcome() < (Double)getRightOperand().outcome();
	}

	@Override
	public String getOperatorSymbol() {
		return "<";
	}

}
