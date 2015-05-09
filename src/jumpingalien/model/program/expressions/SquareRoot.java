package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public class SquareRoot extends UnaryOperator{
	
	protected SquareRoot(SourceLocation sourceLocation, Expression operand){
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
