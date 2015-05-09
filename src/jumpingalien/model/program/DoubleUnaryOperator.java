package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public abstract class DoubleUnaryOperator extends UnaryOperator{
	
	protected DoubleUnaryOperator(SourceLocation sourceLocation, Double operand){
		super(sourceLocation, operand);
	}
	
	public Object getOperand() {
		return (Double)super.getOperand();
	}
}
