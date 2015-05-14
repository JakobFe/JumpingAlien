package jumpingalien.model.program.statements;

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
	
	// Niet vergeten rekening te houden met het tijdsaspect.
	public void execute(){
		while((Boolean)getCondition().outcome()){
			getBody().execute();
		}
	}
}
