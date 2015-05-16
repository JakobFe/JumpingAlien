package jumpingalien.model.program.expressions;


import jumpingalien.part3.programs.SourceLocation;

import java.lang.Double;

public class Subtraction extends BinaryOperator {

	public Subtraction(SourceLocation sourceLocation, 
			Expression leftOperand, 
			Expression rightOperand){
		super(sourceLocation,leftOperand,rightOperand);
	}
		
	@Override
	public String getOperatorSymbol() {
		return "-";
	}

	@Override
	public Number outcome() {
		//return ((Constant<Double>) getLeftOperand()).outcome() - 
		//		((Constant<Double>)getRightOperand()).outcome();
		return ((Double) getLeftOperand().outcome()) - 
				   ((Double) getRightOperand().outcome());
	}

}
