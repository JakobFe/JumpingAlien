package jumpingalien.model.program.statements;

import jumpingalien.model.game.*;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.programs.Program;
import jumpingalien.part3.programs.SourceLocation;

public class StartMovement extends SingleStatement {

	public StartMovement(SourceLocation sourceLocation, 
			Expression dir){
		super(sourceLocation);
		this.direction = dir;
	}
	
	
	public Expression getDirection() {
		return direction;
	}


	private final Expression direction;
	
	@Override
	public void setProgram(Program program) {
		super.setProgram(program);
		getDirection().setProgram(program);
	}
	
	@Override
	public void execute() {
		if(getProgram() != null){
			GameObject self = getProgram().getGameObject();
			jumpingalien.model.game.Direction dir =
					jumpingalien.model.game.Direction.convertDirection(getDirection());
			if(dir  == jumpingalien.model.game.Direction.LEFT)
				self.startMove(dir);
			else if(dir == jumpingalien.model.game.Direction.RIGHT)
				self.startMove(dir);
			else if(dir == jumpingalien.model.game.Direction.UP &&
					(self instanceof Shark ||
					 self instanceof Alien))
				((JumpInterface) self).startJump();
			else if(dir == jumpingalien.model.game.Direction.DOWN &&
					(self instanceof Alien))
				((Alien) self).startDuck();
		}	
	}
	
	@Override
	public String toString() {
		return "Statement: Start movement in direction " + getDirection() + 
				"\n" + "at sourcelocation " + getSourceLocation().toString() + ".";
	}

}
