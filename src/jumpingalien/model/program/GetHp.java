package jumpingalien.model.program;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetHp extends UnaryOperator {

	protected GetHp(SourceLocation sourceLocation, GameObject operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "gethp";
	}

	@Override
	public Object outcome() {
		return ((GameObject) getOperand()).getHitPoints();
	}

}
