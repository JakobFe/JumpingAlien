package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.game.*;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.expressions.UnaryOperator;
import jumpingalien.part3.programs.SourceLocation;

public class IsTerrain extends UnaryOperator {

	public IsTerrain(SourceLocation sourceLocation, Expression terrain) {
		super(sourceLocation, terrain); 
	}

	@Override
	public String getOperatorSymbol() {
		return "isterrain";
	}

	@Override
	public Boolean outcome() {
		return getOperand().outcome() instanceof Terrain;
	}

}
