package jumpingalien.model.program;


import jumpingalien.part3.programs.SourceLocation;
import java.lang.Double;

public class Addition extends BinaryOperator {

	public Addition(SourceLocation sourceLocation, 
			Double leftOperand, 
			Double rightOperand){
		super(sourceLocation,leftOperand,rightOperand);
	}
	
	@Override
	public Double getLeftOperand() {
		return (Double) super.getLeftOperand();
	}
	
	@Override
	public Double getRightOperand() {
		return (Double) super.getRightOperand();
	}
	
	@Override
	public String getOperatorSymbol() {
		return "+";
	}

	@Override
	public Number outcome() {
		return getLeftOperand().doubleValue() + 
			   getRightOperand().doubleValue(); 
	}

}
