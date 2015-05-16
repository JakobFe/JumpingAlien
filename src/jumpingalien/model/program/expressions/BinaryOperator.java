package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.Basic;

public abstract class BinaryOperator extends Operator{
	
	protected BinaryOperator(SourceLocation sourceLocation, 
			Expression leftOperand, Expression rightOperand){
		super(sourceLocation);
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}
	
	public Expression getLeftOperand() {
		return leftOperand;
	}

	private final Expression leftOperand;
	
	public Expression getRightOperand() {
		return rightOperand;
	}
	
	private final Expression rightOperand;
	
	@Override
	@Basic
	public final int getNbOperands() {
		return 2;
	}
	
	@Override
	public String toString() {
		return "Expression: Binary Operator: " + getLeftOperand().toString() + 
				" " + getOperatorSymbol() + " " + getRightOperand().toString() + 
				" at source location " + getSourceLocation() + ".";
	}

}
