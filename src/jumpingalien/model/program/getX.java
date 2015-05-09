package jumpingalien.model.program;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class getX extends UnaryOperator {

	protected getX(SourceLocation sourceLocation, GameObject operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "getx";
	}

	@Override
	public Double outcome() {
		return ((GameObject) getOperand()).getPosition().getXPosition();
	}

}
