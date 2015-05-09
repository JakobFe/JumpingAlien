package jumpingalien.model.program;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetY extends UnaryOperator {

	protected GetY(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "gety";
	}

	@Override
	public Double outcome() {
		return ((GameObject) getOperand()).getPosition().getYPosition();
	}

}