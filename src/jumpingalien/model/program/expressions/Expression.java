package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public abstract class Expression {
	
	protected Expression(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	protected Expression(){
		sourceLocation = null;
	}
	
	public abstract Object outcome();
	
	public boolean hasSameOutcomeAs(Object other) {
		if(!(other instanceof Expression))
			return false;
		else
			return this.outcome() == ((Expression) other).outcome();
	}
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}

	private final SourceLocation sourceLocation;

}
