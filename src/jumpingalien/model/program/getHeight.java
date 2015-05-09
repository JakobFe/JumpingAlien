package jumpingalien.model.program;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class getHeight extends UnaryOperator {

	protected getHeight(SourceLocation sourceLocation, GameObject operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "getheight";
	}

	@Override
	public Integer outcome() {
		return ((GameObject) getOperand()).getHeight();
	}

}