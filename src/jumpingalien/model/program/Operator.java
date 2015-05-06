package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;


public abstract class Operator<T> extends Expression {
	
	protected Operator(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public abstract int getNbOperands();
	
	public abstract String getOperatorSymbol();

}
