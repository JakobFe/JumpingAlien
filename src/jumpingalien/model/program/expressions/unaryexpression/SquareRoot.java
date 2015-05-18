package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class SquareRoot extends UnaryOperator{
	
	public SquareRoot(SourceLocation sourceLocation, Expression operand){
		super(sourceLocation, operand);
	}
	
	@Override
	public String getOperatorSymbol() {
		return "sqrt";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Number outcome() {
		return Math.sqrt(((Constant<Double>) getOperand()).outcome());
	}

}
