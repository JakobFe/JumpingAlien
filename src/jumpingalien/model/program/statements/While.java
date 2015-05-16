package jumpingalien.model.program.statements;

import java.util.NoSuchElementException;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.programs.Program;
import jumpingalien.part3.programs.SourceLocation;

public class While extends SingleStatement {
	
	public While(Expression condition, Statement body,
			SourceLocation sourceLocation){
		super(sourceLocation);
		this.condition = condition;
		this.body = body;
	}
	
	public Expression getCondition() {
		return condition;
	}

	private final Expression condition;
	
	public Statement getBody() {
		return body;
	}

	private final Statement body;
	
	@Override
	public void setProgram(Program program) {
		super.setProgram(program);
		getBody().setProgram(program);
	}
	
	public void execute(){
		if((Boolean)getCondition().outcome()){
			getThisIterator().setIndex(1);
		}
		else{
			getThisIterator().setIndex(2);
		}
	}
	
	@Override
	public StatementIterator<Statement> iterator() {
		return new StatementIterator<Statement>(){
			
			@Override
			public boolean hasNext() {
				return (!subIteratorsInitialized || getThisIterator().getIndex() < 2);
			}
			
			@Override
			public Statement next() throws NoSuchElementException{
				if(!subIteratorsInitialized)
					initialiseSubIterators();
				if(getThisIterator().getIndex() == 0)
					return While.this;
				else if(getThisIterator().getIndex() == 1){
					if(bodyIterator.hasNext())
						return bodyIterator.next();
					else{
						restart();
						return this.next();
					}
				}
				return null;
			}
			
			@Override
			public void restart() {
				getThisIterator().setIndex(0);
				bodyIterator.restart();
			}
			
			@Override
			public void setIndex(int index) {
				this.index = index;
			}
			
			@Override
			public int getIndex() {
				return this.index;
			}
			
			private int index = 0;
			
			private void initialiseSubIterators(){
				subIteratorsInitialized = true;
				bodyIterator = getBody().iterator();

			}
			
			private StatementIterator<Statement> bodyIterator;
			
			private boolean subIteratorsInitialized = false;
		};
	}

	@Override
	public String toString() {
		return "Statement: while (" + getCondition().toString() + "do" + "\n\t" +
				getBody().toString() + "\n" + "at source location " +
				getSourceLocation().toString() + ".";
	}
	
}
