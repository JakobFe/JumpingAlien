package jumpingalien.model.program.expressions.unaryexpression;

import java.util.Random;

import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.expressions.UnaryOperator;
import jumpingalien.part3.programs.SourceLocation;

public class RandomDouble extends UnaryOperator {

	public RandomDouble(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "random";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double outcome() {
		Random rn = new Random();
		return rn.nextDouble()*((Constant<Double>) getOperand()).outcome();
	}

}
