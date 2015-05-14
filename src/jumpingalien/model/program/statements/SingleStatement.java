package jumpingalien.model.program.statements;

import java.util.NoSuchElementException;

import jumpingalien.part3.programs.SourceLocation;

public class SingleStatement extends Statement {
	
	public SingleStatement(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public StatementIterator<Statement> iterator() {
		return new StatementIterator<Statement>(){

			@Override
			public boolean hasNext() {
				return getIndex() == 0;
			}

			@Override
			public Statement next() throws NoSuchElementException{
				if(hasNext()){
					incrementIndex();
					return SingleStatement.this;
				}
				else
					throw new NoSuchElementException();
			}
			
			public void restart(){
				this.index = 0;
			}
			
			@Override
			public int getIndex() {
				return index;
			}
			
			@Override
			public void incrementIndex() {
				this.index = getIndex() + 1;
			}
			
			private int index;
		};
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
