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
		getCondition().setProgram(program);
	}
	
	
	public void executeSingleStatement(){}
	
	
	@Override
	public StatementIterator<Statement> iterator() {
		return new StatementIterator<Statement>(){
			
			@Override
			public boolean hasNext() {
				return (!subIteratorsInitialized || getIndex() < 2);
			}
			
			@Override
			public Statement next() throws NoSuchElementException{
				if(!subIteratorsInitialized)
					initialiseSubIterators();
				if(getIndex() == 0){
					if((Boolean)getCondition().outcome()){
						setIndex(1);
					}
					else{
						setIndex(2);
					}
					return While.this;
				}
				else if(getIndex() == 1 && (bodyIterator.hasNext()))
						return bodyIterator.next();
				else{
					restart();
					return null;
				}
			}
			
			@Override
			public void restart() {
				setIndex(0);
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
