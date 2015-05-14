package jumpingalien.model.program.statements;

import jumpingalien.model.program.expressions.Constant;
import jumpingalien.part3.programs.SourceLocation;

public class StartMovement extends Statement {

	public StartMovement(SourceLocation sourceLocation, 
			Constant<jumpingalien.part3.programs.IProgramFactory.Direction> dir){
		super(sourceLocation);
		this.direction = jumpingalien.model.game.Direction.convertDirection(dir);
	}
	
	
	public jumpingalien.model.game.Direction getDirection() {
		return direction;
	}


	private final jumpingalien.model.game.Direction direction;
	
	@Override
	public void execute() {
		if(getProgram() != null){
			if(getDirection() == jumpingalien.model.game.Direction.LEFT)
				getProgram().getGameObject().startMove(getDirection());
			else if(getDirection() == jumpingalien.model.game.Direction.RIGHT)
				getProgram().getGameObject().startMove(getDirection());
			
		}	
	}

}
