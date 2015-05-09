package jumpingalien.model.program;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class isGameObject<T extends GameObject> extends UnaryOperator {

	protected isGameObject(SourceLocation sourceLocation, GameObject operand, Class<T> type) {
		super(sourceLocation, operand); 
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}

	private final Class<T> type;

	@Override
	public String getOperatorSymbol() {
		return "ismazub";
	}

	@Override
	public Boolean outcome() {
		return getOperand().getClass() == getType();
	}

}
