package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.Basic;

public abstract class UnaryOperator extends Operator{
	
	protected UnaryOperator(SourceLocation sourceLocation, Expression operand){
		super(sourceLocation);
		this.operand = operand;
	}
	
	public Expression getOperand() {
		return operand;
	}

	private final Expression operand;
		
	@Override
	@Basic
	public final int getNbOperands() {
		return 1;
	}

}
