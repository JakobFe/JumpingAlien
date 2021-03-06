package jumpingalien.model.program.statements;

import java.util.NoSuchElementException;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.programs.Program;
import jumpingalien.part3.programs.SourceLocation;

public class Wait extends SingleStatement {

	public Wait(SourceLocation sourceLocation, Expression duration) {
		super(sourceLocation);
		this.duration = duration;
	}
	
	public Expression getDuration() {
		return duration;
	}

	private final Expression duration;
	
	@Override
	public void setProgram(Program program) {
		assert (program!=null && program.hasAsStatement(this));
		super.setProgram(program);
		getDuration().setProgram(program);
	}
	
	public double getNbOfSkips() {
		return nbOfSkips;
	}

	public void setNbOfSkips(int nbOfSkips) {
		this.nbOfSkips = nbOfSkips;
	}

	private int nbOfSkips;
	
	@Override
	public void executeSingleStatement() {}
	
	@Override
	public StatementIterator<Statement> iterator() {
		return new StatementIterator<Statement>() {
			
			@Override
			public Statement next() throws NoSuchElementException{
				if(!hasNext())
					throw new NoSuchElementException();
				else{
					if(getIndex() == 0)
						setNbOfSkips((int) Math.floor(((Double)getDuration().outcome())/0.001));
					incrementIndex();
					return Wait.this;
				}
			}
			
			@Override
			public boolean hasNext() {
				return ((getNbOfSkips() == 0) || (getIndex() < getNbOfSkips()));
			}
			
			@Override
			public void setIndex(int index) {
				this.index = index;
			}
			
			public void incrementIndex(){
				setIndex(getIndex() + 1);
			}
			
			@Override
			public void restart() {
				setIndex(0);
			}
			
			@Override
			public int getIndex() {
				return index;
			}
			
			private int index;
		};
	}

	@Override
	public String toString() {
		return "Statement: Wait for " + getDuration().outcome() + 
				" seconds at source location " + getSourceLocation().toString() + ".";
	}
}
