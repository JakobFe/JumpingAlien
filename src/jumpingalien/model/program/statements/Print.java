package jumpingalien.model.program.statements;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class Print extends SingleStatement {

	public Print(Expression value, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.value = value;
	}
	
	public Expression getValue() {
		return value;
	}

	private final Expression value;
	
	@Override
	public void executeSingleStatement() {
		System.out.println(getValue().outcome());
	}
	
	@Override
	public String toString() {
		return "Statement: Print of value " + "\n\t"+ getValue().outcome().toString() + "\n" + 
				"at source location " + getSourceLocation().toString() + ".";
	}
}
