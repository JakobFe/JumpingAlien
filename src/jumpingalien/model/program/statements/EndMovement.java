package jumpingalien.model.program.statements;

import jumpingalien.model.game.*;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class EndMovement extends SingleStatement {

	public EndMovement(SourceLocation sourceLocation, 
			Expression dir){
		super(sourceLocation);
		this.direction = dir;
	}

	public Expression getDirection() {
		return direction;
	}

	private final Expression direction;
	
	@Override
	public void execute() {
		if(getProgram() != null){
			GameObject self = getProgram().getGameObject();
			jumpingalien.model.game.Direction dir =
					jumpingalien.model.game.Direction.convertDirection(getDirection());
			if(self != null){
				if(dir == jumpingalien.model.game.Direction.LEFT)
					self.endMovement(dir);
				else if(dir == jumpingalien.model.game.Direction.RIGHT)
					self.endMovement(dir);
				else if(dir == jumpingalien.model.game.Direction.UP &&
						(self instanceof Shark ||
						 self instanceof Alien))
					((JumpInterface) self).endJump();
				else if(dir == jumpingalien.model.game.Direction.DOWN &&
						(self instanceof Alien))
					((Alien) self).endDuck();
			}
		}	
	}
	
	@Override
	public String toString() {
		return "Statement: End movement in direction " + getDirection() + 
				"\n" + "at sourcelocation " + getSourceLocation().toString() + ".";
	}
}

