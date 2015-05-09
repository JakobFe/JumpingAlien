package jumpingalien.model.program.expressions;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetHeight extends UnaryOperator {

	protected GetHeight(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "getheight";
	}

	@Override
	public Integer outcome() {
		return ((GameObject) getOperand().outcome()).getHeight();
	}

}