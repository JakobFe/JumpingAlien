package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.Basic;

public abstract class UnaryOperator extends Operator{
	
	protected UnaryOperator(SourceLocation sourceLocation, Object operand){
		super(sourceLocation);
		this.operand = operand;
	}
	
	public Object getOperand() {
		return operand;
	}

	private final Object operand;
		
	@Override
	@Basic
	public final int getNbOperands() {
		return 1;
	}

}
