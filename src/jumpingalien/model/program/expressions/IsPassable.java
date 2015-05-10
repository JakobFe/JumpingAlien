package jumpingalien.model.program.expressions;

import jumpingalien.model.game.Terrain;
import jumpingalien.part3.programs.SourceLocation;

public class IsPassable extends UnaryOperator {

	public IsPassable(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "ispassable";
	}

	@Override
	public Object outcome() {
		return ((Terrain) getOperand().outcome()).isPassable();
	}

}
