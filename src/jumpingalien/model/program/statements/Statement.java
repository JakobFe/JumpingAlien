package jumpingalien.model.program.statements;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.program.programs.Program;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Statement {
	
	protected Statement(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	protected Statement(){
		sourceLocation = null;
	}
	
	public abstract boolean hasAsSubStatement(Statement other);
	
	public abstract boolean hasActionStatAsSubStat();
	
	public static boolean isActionStatement(Statement other){
		return (other instanceof Wait || other instanceof Skip ||
				other instanceof StartMovement ||
				other instanceof EndMovement);
	}
	
	@Basic
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}

	private final SourceLocation sourceLocation;
	
	@Basic
	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		assert (program == null || program.hasAsStatement(this));
		this.program = program;
	}

	private Program program = null;
	
	public abstract StatementIterator<Statement> iterator();

}
