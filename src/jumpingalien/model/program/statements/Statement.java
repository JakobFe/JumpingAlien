package jumpingalien.model.program.statements;

import jumpingalien.model.program.programs.Program;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Statement {
	
	protected Statement(SourceLocation sourceLocation){
		System.out.println("STATEMENT CONSTRUCTOR");
		this.sourceLocation = sourceLocation;
	}
	
	protected Statement(){
		sourceLocation = null;
	}
	
	// opm: doc: je moet een niet deterministische postconditie gebruiken (LISKOV)
	public boolean hasAsSubStatement(Statement other){
		return other == this;
	}
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}

	private final SourceLocation sourceLocation;
	
	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		assert program.hasAsStatement(this);
		this.program = program;
	}

	private Program program = null;
	
	public abstract StatementIterator<Statement> iterator();
	
	public abstract void execute();
	
	public StatementIterator<Statement> getThisIterator() {
		if(thisIterator == null){
			thisIterator = iterator();
			return thisIterator;
		}
		else
			return thisIterator;
	}

	private StatementIterator<Statement> thisIterator = null;


}
