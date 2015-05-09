package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;


public abstract class Operator extends Expression {
	
	protected Operator(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public abstract int getNbOperands();
	
	public abstract String getOperatorSymbol();

}
