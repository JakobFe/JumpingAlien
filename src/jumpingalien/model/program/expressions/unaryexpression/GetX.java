package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class GetX extends UnaryOperator {

	public GetX(SourceLocation sourceLocation, Expression operand) {
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
