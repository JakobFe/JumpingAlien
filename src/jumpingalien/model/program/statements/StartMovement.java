package jumpingalien.model.program.statements;

import jumpingalien.model.game.*;
import jumpingalien.model.program.expressions.Variable;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class StartMovement extends SingleStatement {

	public StartMovement(SourceLocation sourceLocation, 
			Expression dir){
		super(sourceLocation);
		this.direction = jumpingalien.model.game.Direction.convertDirection(
				((Variable)dir));
	}
	
	
	public jumpingalien.model.game.Direction getDirection() {
		return direction;
	}


	private final jumpingalien.model.game.Direction direction;
	
	@Override
	public void execute() {
		if(getProgram() != null){
			GameObject self = getProgram().getGameObject();
			if(getDirection() == jumpingalien.model.game.Direction.LEFT)
				self.startMove(getDirection());
			else if(getDirection() == jumpingalien.model.game.Direction.RIGHT)
				self.startMove(getDirection());
			else if(getDirection() == jumpingalien.model.game.Direction.UP &&
					(self instanceof Shark ||
					 self instanceof Alien))
				((JumpInterface) self).startJump();
			else if(getDirection() == jumpingalien.model.game.Direction.DOWN &&
					(self instanceof Alien))
				((Alien) self).startDuck();
		}	
	}

}
