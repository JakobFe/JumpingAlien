package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.expressions.UnaryOperator;
import jumpingalien.part3.programs.SourceLocation;

public class GetWidth extends UnaryOperator {

	public GetWidth(SourceLocation sourceLocation, Expression operand) {
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
