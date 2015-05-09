package jumpingalien.model.program;

import jumpingalien.model.game.*;
import jumpingalien.part3.programs.SourceLocation;

public class IsTerrainOfType extends UnaryOperator {

	protected IsTerrainOfType(SourceLocation sourceLocation, 
			Expression terrainToCheck, Terrain terrainType) {
		super(sourceLocation, terrainToCheck); 
		this.type = terrainType;
	}

	public Terrain getType() {
		return type;
	}

	private final Terrain type;

	@Override
	public String getOperatorSymbol() {
		return "is" + getType().toString().toLowerCase();
	}

	@Override
	public Boolean outcome() {
		return getOperand().outcome() == getType();
	}

}
