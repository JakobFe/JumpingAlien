package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.expressions.Operator;
import jumpingalien.model.program.programs.Program;
import jumpingalien.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.Basic;

public abstract class UnaryOperator extends Operator{
	
	protected UnaryOperator(SourceLocation sourceLocation, Expression operand){
		super(sourceLocation);
		this.operand = operand;
	}
	
	public Expression getOperand() {
		return operand;
	}

	private final Expression operand;
		
	@Override
	@Basic
	public final int getNbOperands() {
		return 1;
	}
	
	@Override
	public void setProgram(Program program) {
		super.setProgram(program);
		getOperand().setProgram(program);
	}

	@Override
	public String toString() {
		return "Expression: Unary Operator: " + getOperatorSymbol() + " " +
				getOperand().toString() +
				" at source location " + getSourceLocation() + ".";
	}
	
}
