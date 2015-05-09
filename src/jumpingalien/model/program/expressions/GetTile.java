package jumpingalien.model.program.expressions;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.game.Tile;
import jumpingalien.part3.programs.SourceLocation;

public class GetTile extends BinaryOperator {

	protected GetTile(SourceLocation sourceLocation, Expression leftOperand,
			Expression rightOperand,Expression self) {
		super(sourceLocation, leftOperand, rightOperand);
		this.self = self;
	}
	
	public Expression getSelf() {
		return self;
	}

	private final Expression self;

	@Override
	public String getOperatorSymbol() {
		return "gettile";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Tile outcome() {
		GameObject thisObject = ((Constant<GameObject>)getSelf()).outcome();
		int xPos = (int) Math.floor(((Constant<Double>) getLeftOperand()).outcome());
		int yPos = (int) Math.floor(((Constant<Double>) getRightOperand()).outcome());
		return thisObject.getWorld().getTileAtPos(xPos, yPos);
	}
	
}
