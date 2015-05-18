package jumpingalien.model.program.expressions.binaryexpression;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.game.Tile;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class GetTile extends BinaryOperator {

	public GetTile(SourceLocation sourceLocation, Expression leftOperand,
			Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}
	
	@Override
	public String getOperatorSymbol() {
		return "gettile";
	}

	@Override
	public Tile outcome() {
		GameObject thisObject = getProgram().getGameObject();
		int xPos = (int) Math.floor((Double)(getLeftOperand()).outcome());
		int yPos = (int) Math.floor((Double)(getRightOperand()).outcome());
		return thisObject.getWorld().getTileAtPos(xPos, yPos);
	}
	
}
