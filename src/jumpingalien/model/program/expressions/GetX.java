package jumpingalien.model.program.expressions;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetX extends UnaryOperator {

	protected GetX(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "getx";
	}

	@Override
	public Double outcome() {
		return ((GameObject) getOperand().outcome()).getPosition().getXPosition();
	}

}
