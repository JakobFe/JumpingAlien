package jumpingalien.model.program;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class getWidth extends UnaryOperator {

	protected getWidth(SourceLocation sourceLocation, GameObject operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "getwidth";
	}

	@Override
	public Integer outcome() {
		return ((GameObject) getOperand()).getWidth();
	}

}
