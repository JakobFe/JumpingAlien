package jumpingalien.model.program;

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

}
