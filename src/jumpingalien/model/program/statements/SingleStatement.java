package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.part3.programs.SourceLocation;

public class SingleStatement extends Statement {
	
	public SingleStatement(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Iterator<Statement> iterator() {
		return new Iterator<Statement>(){

			@Override
			public boolean hasNext() {
				return !alreadyExecuted;
			}

			@Override
			public Statement next() throws NoSuchElementException{
				if(hasNext()){
					alreadyExecuted = true;
					return SingleStatement.this;
				}
				else
					throw new NoSuchElementException();
			}
			
			public void restart(){
				alreadyExecuted = false;
			}
			
			private boolean alreadyExecuted;
			
		};
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
