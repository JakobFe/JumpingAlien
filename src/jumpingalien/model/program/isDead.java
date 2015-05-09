package jumpingalien.model.program;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class isDead extends UnaryOperator {

	protected isDead(SourceLocation sourceLocation, GameObject operand) {
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