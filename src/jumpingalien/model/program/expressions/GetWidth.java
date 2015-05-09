package jumpingalien.model.program.expressions;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetWidth extends UnaryOperator {

	protected GetWidth(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "getwidth";
	}

	@Override
	public Integer outcome() {
		return ((GameObject) getOperand().outcome()).getWidth();
	}

}
