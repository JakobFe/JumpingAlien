package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class GetHp extends UnaryOperator {

	public GetHp(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "gethp";
	}

	@Override
	public Object outcome() {
		return ((GameObject) getOperand().outcome()).getHitPoints();
	}

}
