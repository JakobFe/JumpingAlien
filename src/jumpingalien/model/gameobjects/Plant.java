package jumpingalien.model.gameobjects;

import jumpingalien.model.exceptions.IllegalTimeIntervalException;
import jumpingalien.model.exceptions.IllegalXPositionException;
import jumpingalien.model.exceptions.IllegalYPositionException;
import jumpingalien.model.other.Direction;
import jumpingalien.model.other.Position;
import jumpingalien.model.worldfeatures.Tile;
import jumpingalien.model.worldfeatures.World;
import jumpingalien.util.Sprite;

import java.util.Random;

public class Plant extends GameObject {
	
	public Plant(int x, int y, Sprite[] sprites) 
			throws IllegalXPositionException,IllegalYPositionException{
		super(x,y,PLANT_VELOCITY,sprites,1);
		setHorVelocity(getInitHorVelocity());
		Random rn = new Random();
		int startIndex = rn.nextInt(2);
		if(startIndex == 0)
			setHorDirection(Direction.LEFT);
		else
			setHorDirection(Direction.RIGHT);
	}
	
	@Override
	public final double getHorVelocity(){
		return PLANT_VELOCITY;
	}
	
	private static final double PLANT_VELOCITY = 0.5;

	@Override
	protected boolean isValidWorld(World world) {
		return world.hasAsPlant(this);
	}
	
	@Override
	public void advanceTime(double timeDuration) throws IllegalXPositionException,
	IllegalYPositionException,IllegalTimeIntervalException{
		if (!isValidTimeInterval(timeDuration))
			throw new IllegalTimeIntervalException(this);
		if (getTimeSum()>0.5){
			alternateDirection();
			updateSpriteIndex();
			setTimeSum((getTimeSum()-0.5));
		}
		updatePosition(timeDuration);
		counter(timeDuration);
		
	}
	
	@Override
	protected void updatePosition(double timeDuration) {
		double newXPos = getPosition().getXPosition() + getHorDirection().getFactor()*
				(getHorVelocity()*timeDuration)*100;
		for (Tile impassableTile: getWorld().getImpassableTiles()){
			if (this.isOverlapping(impassableTile)){
				if(isColliding(Direction.LEFT, impassableTile)){
					if (isMoving(Direction.LEFT))
						newXPos = impassableTile.getXPosition()+getWorld().getTileSize()-1;
					setHorDirection(Direction.RIGHT);
					//System.out.println("Colliding left");
				}
				else if(isColliding(Direction.RIGHT, impassableTile)){
					if (isMoving(Direction.RIGHT))
						newXPos = impassableTile.getXPosition()-getWidth()+1;
					setHorDirection(Direction.LEFT);
					//System.out.println("Colliding right");
				}
			}
		}
		getPosition().terminate();
		setPosition(new Position(newXPos,getPosition().getYPosition(),getWorld()));
	}
	
	private void alternateDirection(){
		if(getHorDirection() == Direction.LEFT)
			setHorDirection(Direction.RIGHT);
		else if(getHorDirection() == Direction.RIGHT)
			setHorDirection(Direction.LEFT);
		else
			System.out.println("current dir null");
	}

	@Override
	public void updateSpriteIndex() {
		setIndex((getIndex()+1)%2);
	}
	

}
