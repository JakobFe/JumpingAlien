package jumpingalien.model.program.statements;

import java.util.List;
import java.util.NoSuchElementException;

import jumpingalien.part3.programs.SourceLocation;

public class SequenceStatement extends ComposedStatement {

	public SequenceStatement(SourceLocation sourceLocation,
			List<Statement> subStatements) {
		super(sourceLocation, subStatements);
	}

	@Override
	public StatementIterator<Statement> iterator() {
		return new StatementIterator<Statement>(){

			@Override
			public boolean hasNext() {
				return (getSubStatementAt(getNbOfSubStatements()-1).iterator().hasNext());
			}

			@Override
			public SingleStatement next() throws NoSuchElementException{
				if(!hasNext())
					throw new NoSuchElementException();
				else if(getSubStatementAt(index).iterator().hasNext()){			
					return (SingleStatement)getSubStatementAt(index).iterator().next();
				}
				else{
					incrementIndex();
					return (SingleStatement)getSubStatementAt(index).iterator().next();
				}
			}
			
			public void restart(){
				setIndex(0);
				for(Statement substat: getSubStatements()){
					substat.iterator().restart();
				}
			}
			
			public int getIndex() {
				return index;
			}

			public void incrementIndex() {
				setIndex(getIndex()+1);
			}

			private int index = 0;

			@Override
			public void setIndex(int index) {
				this.index = index;
			}
			
		};
	}

	@Override
	public void execute() {}

}
