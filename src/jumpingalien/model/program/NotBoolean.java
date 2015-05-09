package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class NotBoolean extends UnaryOperator {

	protected NotBoolean(SourceLocation sourceLocation, Boolean operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "!";
	}

	@Override
	public Boolean outcome() {
		return !((Boolean)getOperand());
	}
	
}
