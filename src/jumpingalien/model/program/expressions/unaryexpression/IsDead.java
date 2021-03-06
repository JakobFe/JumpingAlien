package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class IsDead extends UnaryOperator {

	public IsDead(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "isDead";
	}

	@Override
	public Boolean outcome() {
		return ((GameObject) getOperand().outcome()).isDead();
	}

}