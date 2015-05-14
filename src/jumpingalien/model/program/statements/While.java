package jumpingalien.model.program.statements;

import java.util.NoSuchElementException;

import jumpingalien.model.program.expressions.Expression;
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
	
	public void execute(){
		if((Boolean)getCondition().outcome()){
			iterator().setIndex(1);
		}
		else
			iterator().setIndex(2);
	}
	
	@Override
	public StatementIterator<Statement> iterator() {
		return new StatementIterator<Statement>(){
			
			@Override
			public Statement next() throws NoSuchElementException{
				if(getIndex() == 0)
					return While.this;
				else if(getIndex() == 1){
					if(getBody().iterator().hasNext())
						return getBody().iterator().next();
					else
						setIndex(0);
				}
				return null;
			}
			
			@Override
			public boolean hasNext() {
				return getIndex() < 2;
			}
			
			@Override
			public void restart() {
				setIndex(0);
				getBody().iterator().restart();
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
		};
	}

}
