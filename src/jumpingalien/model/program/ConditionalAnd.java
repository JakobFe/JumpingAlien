package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class ConditionalAnd extends BinaryOperator {

	protected ConditionalAnd(SourceLocation sourceLocation, Expression leftOperand,
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
