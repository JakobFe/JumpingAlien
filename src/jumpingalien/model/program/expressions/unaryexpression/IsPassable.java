package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.game.Tile;
import jumpingalien.model.program.expressions.Expression;
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
		return ((Tile) getOperand().outcome()).getGeoFeature().isPassable();
	}

}
