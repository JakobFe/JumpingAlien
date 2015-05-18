package jumpingalien.model.program.expressions.binaryexpression;


import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

import java.lang.Double;

public class Addition extends BinaryOperator {

	public Addition(SourceLocation sourceLocation, 
			Expression leftOperand, 
			Expression rightOperand){
		super(sourceLocation,leftOperand,rightOperand);
	}
	
	@Override
	public String getOperatorSymbol() {
		return "+";
	}

	@Override
	public Number outcome() {
		//return ((Constant<Double>) getLeftOperand()).outcome() + 
		//		((Constant<Double>)getRightOperand()).outcome();
		return ((Double) getLeftOperand().outcome()) + 
			   ((Double) getRightOperand().outcome());
	}
}
