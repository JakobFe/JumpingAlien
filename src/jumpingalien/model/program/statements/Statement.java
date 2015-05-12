package jumpingalien.model.program.statements;

import jumpingalien.part3.programs.SourceLocation;

public abstract class Statement {
	
	protected Statement(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	protected Statement(){
		sourceLocation = null;
	}
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}

	private final SourceLocation sourceLocation;

}
