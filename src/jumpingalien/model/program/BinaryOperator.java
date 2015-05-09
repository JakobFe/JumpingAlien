package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.Basic;

public abstract class BinaryOperator extends Operator{
	
	protected BinaryOperator(SourceLocation sourceLocation, 
			Object leftOperand, Object rightOperand){
		super(sourceLocation);
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}
	
	public Object getLeftOperand() {
		return leftOperand;
	}

	private final Object leftOperand;
	
	public Object getRightOperand() {
		return rightOperand;
	}
	
	private final Object rightOperand;
	
	@Override
	@Basic
	public final int getNbOperands() {
		return 2;
	}

}
