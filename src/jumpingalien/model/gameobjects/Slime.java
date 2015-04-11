package jumpingalien.model.gameobjects;

import jumpingalien.model.worldfeatures.World;
import jumpingalien.util.Sprite;

public class Slime extends Character{
	
	public Slime(int x, int y, Sprite[] sprites, School school){
		super(x,y,0,2.5,sprites);
	}
	
	@Override
	protected boolean isValidWorld(World world) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void updatePosition(double timeDuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSpriteIndex() {
		// TODO Auto-generated method stub
		
	}
	
}
