package jumpingalien.model.program.statements;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class IfStatement extends ComposedStatement {

	public IfStatement(Expression condition, Statement ifBody,
			Statement elseBody, SourceLocation sourceLocation) {
		super(sourceLocation, ifBody, elseBody);
		this.condition = condition;
	}
	
	public Expression getCondition() {
		return condition;
	}

	private final Expression condition;

	@Override
	public void execute() {
		if((Boolean)getCondition().outcome()){
			getSubStatementAt(0).execute();
		}
		else{
			getSubStatementAt(1).execute();
		}
	}

}
