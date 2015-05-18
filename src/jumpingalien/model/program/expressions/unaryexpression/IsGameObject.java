package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.expressions.UnaryOperator;
import jumpingalien.part3.programs.SourceLocation;

public class IsGameObject<T extends GameObject> extends UnaryOperator {

	public IsGameObject(SourceLocation sourceLocation, Expression operand, Class<T> type) {
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
