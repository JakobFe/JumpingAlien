package jumpingalien.model.program.statements;

import jumpingalien.model.game.*;
import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class EndMovement extends Statement {

	@SuppressWarnings("unchecked")
	public EndMovement(SourceLocation sourceLocation, 
			Expression dir){
		super(sourceLocation);
		this.direction = jumpingalien.model.game.Direction.convertDirection(
				(Constant<jumpingalien.part3.programs.IProgramFactory.Direction>)dir);
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
				self.endMovement(getDirection());
			else if(getDirection() == jumpingalien.model.game.Direction.RIGHT)
				self.endMovement(getDirection());
			else if(getDirection() == jumpingalien.model.game.Direction.UP &&
					(self instanceof Shark ||
					 self instanceof Alien))
				((JumpInterface) self).endJump();
			else if(getDirection() == jumpingalien.model.game.Direction.DOWN &&
					(self instanceof Alien))
				((Alien) self).endDuck();
		}	
	}

}
