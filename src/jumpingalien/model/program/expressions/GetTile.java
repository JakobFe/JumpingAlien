package jumpingalien.model.program.expressions;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.game.Tile;
import jumpingalien.model.program.programs.Program;
import jumpingalien.part3.programs.SourceLocation;

public class GetTile extends BinaryOperator {

	public GetTile(SourceLocation sourceLocation, Expression leftOperand,
			Expression rightOperand) {
		super(sourceLocation, leftOperand, rightOperand);
	}
	
	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	private Program program;

	
	@Override
	public String getOperatorSymbol() {
		return "gettile";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Tile outcome() {
		GameObject thisObject = getProgram().getGameObject();
		int xPos = (int) Math.floor(((Constant<Double>) getLeftOperand()).outcome());
		int yPos = (int) Math.floor(((Constant<Double>) getRightOperand()).outcome());
		return thisObject.getWorld().getTileAtPos(xPos, yPos);
	}
	
}
