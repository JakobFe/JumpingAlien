package jumpingalien.model.program;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class IsDead extends UnaryOperator {

	protected IsDead(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "isDead";
	}

	@Override
	public Boolean outcome() {
		return ((GameObject) getOperand()).isDead();
	}

}