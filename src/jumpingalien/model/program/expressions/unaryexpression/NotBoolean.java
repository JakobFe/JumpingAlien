package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class NotBoolean extends UnaryOperator {

	public NotBoolean(SourceLocation sourceLocation, Expression operand) {
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
