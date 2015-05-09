package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public abstract class DoubleBinaryOperator extends BinaryOperator {
	
	public DoubleBinaryOperator(SourceLocation sourceLocation, 
			Double leftOperand, 
			Double rightOperand){
		super(sourceLocation,leftOperand,rightOperand);
	}
	
	@Override
	public Double getLeftOperand() {
		return (Double)super.getLeftOperand();
	}
	
	@Override
	public Double getRightOperand() {
		return (Double)super.getRightOperand();
	}

}
