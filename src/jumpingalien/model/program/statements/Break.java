package jumpingalien.model.program.statements;

import jumpingalien.model.exceptions.BreakException;
import jumpingalien.part3.programs.SourceLocation;

public class Break extends SingleStatement {

	public Break(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public void executeSingleStatement() throws BreakException {
		System.out.println("EXPECTED");
		throw new BreakException();
	}
	
}
