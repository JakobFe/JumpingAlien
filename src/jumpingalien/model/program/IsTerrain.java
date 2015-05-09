package jumpingalien.model.program;

import jumpingalien.model.game.*;
import jumpingalien.part3.programs.SourceLocation;

public class IsTerrain extends UnaryOperator {

	protected IsTerrain(SourceLocation sourceLocation, Expression terrain) {
		super(sourceLocation, terrain); 
	}

	@Override
	public String getOperatorSymbol() {
		return "isterrain";
	}

	@Override
	public Boolean outcome() {
		return getOperand() instanceof Terrain;
	}

}
