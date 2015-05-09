package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public class NotBoolean extends UnaryOperator {

	protected NotBoolean(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "!";
	}

	@Override
	public Boolean outcome() {
		return !((Boolean)getOperand().outcome());
	}
	
}
