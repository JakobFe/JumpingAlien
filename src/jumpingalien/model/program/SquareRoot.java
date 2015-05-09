package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class SquareRoot extends UnaryOperator{
	
	protected SquareRoot(SourceLocation sourceLocation, Double operand){
		super(sourceLocation, operand);
	}
	
	@Override
	public String getOperatorSymbol() {
		return "sqrt";
	}

	@Override
	public Number outcome() {
		return Math.sqrt(((Double)getOperand()).doubleValue()); 
	}

}
