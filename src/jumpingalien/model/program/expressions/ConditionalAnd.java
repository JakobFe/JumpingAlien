package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public class ConditionalAnd extends BinaryOperator {

	public ConditionalAnd(SourceLocation sourceLocation, Expression leftOperand,
			Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}
	
	@Override
	public Boolean outcome() {
		return (Boolean)getLeftOperand().outcome() && 
			   (Boolean)getRightOperand().outcome();
	}

	@Override
	public String getOperatorSymbol() {
		return "&&";
	}

}