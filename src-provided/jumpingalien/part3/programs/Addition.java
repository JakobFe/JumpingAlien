package jumpingalien.part3.programs;

import jumpingalien.model.program.BinaryOperator;
import jumpingalien.model.program.Double;

public class Addition<T> extends BinaryOperator<T> {
	// T nodig omdat binary operator generisch is ?!?!? 
	// verklaring?
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
		return getLeftOperand().getValue() + 
			   getRightOperand().getValue(); 
	}

}
