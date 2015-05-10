package jumpingalien.model.program.expressions;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetY extends UnaryOperator {

	public GetY(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "gety";
	}

	@Override
	public Double outcome() {
		return ((GameObject) getOperand().outcome()).getPosition().getYPosition();
	}

}