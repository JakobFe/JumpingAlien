package jumpingalien.model.program.expressions;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class IsGameObject<T extends GameObject> extends UnaryOperator {

	protected IsGameObject(SourceLocation sourceLocation, Expression operand, Class<T> type) {
		super(sourceLocation, operand); 
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}

	private final Class<T> type;

	@Override
	public String getOperatorSymbol() {
		return "is" + getType().toString().substring(30,getType().toString().length()).toLowerCase();
	}

	@Override
	public Boolean outcome() {
		return getOperand().outcome().getClass() == getType();
	}

}
