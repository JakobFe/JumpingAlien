package jumpingalien.model.program.expressions;

import jumpingalien.model.game.*;
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