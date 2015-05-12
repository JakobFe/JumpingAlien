package jumpingalien.model.program.statements;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class Print extends Statement {

	public Print(Expression value, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.value = value;
	}
	
	public Expression getValue() {
		return value;
	}

	private final Expression value;
	
	@Override
	public void execute() {
		System.out.println(getValue());
	}

}
