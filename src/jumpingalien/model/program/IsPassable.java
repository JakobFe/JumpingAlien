package jumpingalien.model.program;

import jumpingalien.model.game.Terrain;
import jumpingalien.part3.programs.SourceLocation;

public class IsPassable extends UnaryOperator {

	protected IsPassable(SourceLocation sourceLocation, Expression operand) {
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
