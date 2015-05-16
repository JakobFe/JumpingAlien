package jumpingalien.model.program.statements;

import jumpingalien.part3.programs.SourceLocation;

public class Skip extends SingleStatement {

	public Skip(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public void execute() {}
	
	@Override
	public String toString() {
		return "Statement: Skip at source location " + 
				getSourceLocation().toString() + ".";
	}

}
