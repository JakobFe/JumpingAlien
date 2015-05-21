package jumpingalien.model.program.statements;

import java.util.NoSuchElementException;

import jumpingalien.part3.programs.SourceLocation;

public abstract class SingleStatement extends Statement {
	
	public SingleStatement(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	public abstract void executeSingleStatement();
	
	@Override
	public boolean hasAsSubStatement(Class<? extends Statement> theClass) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public StatementIterator<Statement> iterator() {
		return new StatementIterator<Statement>(){

			@Override
			public boolean hasNext() {
				return getIndex() == 0;
			}

			@Override
			public SingleStatement next() throws NoSuchElementException{
				if(hasNext()){
					incrementIndex();
					SingleStatement.this.executeSingleStatement();
					return SingleStatement.this;
				}
				else
					throw new NoSuchElementException();
			}
			
			public void restart(){
				setIndex(0);
			}
			
			@Override
			public int getIndex() {
				return index;
			}
			
			public void incrementIndex() {
				setIndex(getIndex() + 1);
			}
			
			private int index;

			@Override
			public void setIndex(int index) {
				this.index = index;
			}
		};
	}
}
