package jumpingalien.model.program.types;

import jumpingalien.model.game.Direction;

public class GameDirection extends Type {

	public GameDirection(Direction dir){
		this.value = dir;
	}
	
	public GameDirection(){
		this(null);
	}
	
	public Direction getValue(){
		return value;
	}
	
	private final Direction value;
	
	@Override
	public String toString() {
		return "Types.GameDirection";
	}
	
}