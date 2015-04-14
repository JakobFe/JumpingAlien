package jumpingalien.model.gameobjects;

import java.util.Random;

import jumpingalien.model.exceptions.IllegalTimeIntervalException;
import jumpingalien.model.exceptions.IllegalXPositionException;
import jumpingalien.model.exceptions.IllegalYPositionException;
import jumpingalien.model.other.Direction;
import jumpingalien.model.other.Position;
import jumpingalien.model.worldfeatures.Tile;
import jumpingalien.model.worldfeatures.World;
import jumpingalien.util.Sprite;

public class Shark extends Character {

	public Shark(int x, int y, double initHorVelocity, double maxHorVelocity,
			Sprite[] sprites, int hitPoints) throws IllegalXPositionException,
			IllegalYPositionException{
		super(x,y,initHorVelocity,maxHorVelocity,sprites,hitPoints);
		setRandomDirection();
	}
	
	public Shark(int x, int y, Sprite[] sprites) {
		this(x,y,0,4,sprites,100);
	}
	
	private void setRandomDirection() {
		Random rn = new Random();
		int startIndex = rn.nextInt(2);
		if(startIndex == 0)
			setHorDirection(Direction.LEFT);
		else
			setHorDirection(Direction.RIGHT);
	}
	
	@Override
	public final double getInitHorVelocity(){
		return 0;
	}
	
	@Override
	public final double getMaxHorVelocity() {
		return SHARK_MAX_VEL; 
	}
	
	private static final double SHARK_MAX_VEL = 4;
	
	@Override
	public final double getMaxHorAcceleration() {
		return SHARK_ACCEL;
	}
	
	private static final double SHARK_ACCEL = 1.5;
	
	@Override
	protected double getInitVertVelocity() {
		return INIT_VERT_VELOCITY;
	}
	
	private static final double INIT_VERT_VELOCITY = 2;
	
	@Override
	protected boolean isValidWorld(World world) {
		return world.hasAsShark(this);
	}
	
	public double getPeriodDuration() {
		return periodDuration;
	}
	
	private boolean isValidPeriod(double period){
		return (period <=4 && period >= 1);
	}

	public void setPeriodDuration(double periodDuration) {
		assert isValidPeriod(periodDuration);
		this.periodDuration = periodDuration;
	}

	private double periodDuration = 0;
	
	private void startMove(){
		setHorVelocity(getInitHorVelocity());
		setHorAcceleration(getMaxHorAcceleration());
		setPeriodDuration(2);
	}
	
	private void endMove(){
		setHorVelocity(0);
		setHorAcceleration(0);
	}
	
	@Override
	public void advanceTime(double timeDuration) throws IllegalXPositionException,
	IllegalYPositionException,IllegalTimeIntervalException{
		if (!isValidTimeInterval(timeDuration))
			throw new IllegalTimeIntervalException(this);
		if(getPeriodDuration() == 0){
			setTimeSum(0);
			setRandomDirection();
			startMove();
		}
		else if (getTimeSum() >= getPeriodDuration())
			endMove();
		updatePosition(timeDuration);
		updateHorVelocity(timeDuration);
		updateVertVelocity(timeDuration);
		counter(timeDuration);
		
	}	
	
	@Override
	protected void updatePosition(double timeDuration) {
		double newXPos = getPosition().getXPosition() + getHorDirection().getFactor()*
				(getHorVelocity()*timeDuration+ 0.5*getHorAcceleration()*Math.pow(timeDuration, 2))*100;
		double newYPos = getPosition().getYPosition() + 
				((getVertDirection().getFactor()*getVertVelocity()*timeDuration)+ 
				0.5*getVertAcceleration()*Math.pow(timeDuration, 2))*100;
		//assert ((Math.abs(newXPos - getPosition().getXPosition()) <= 1) &&
		//		(Math.abs(newYPos - getPosition().getYPosition()) <= 1));
		if(newXPos != getPosition().getXPosition())
			//System.out.println((Math.abs(newXPos - getPosition().getXPosition())));
		if (newYPos<0)
			newYPos = 0;
		for (Tile impassableTile: getWorld().getImpassableTiles()){
			if (this.isOverlappingWith(impassableTile)){
				if (isColliding(Direction.DOWN, impassableTile)){
					//System.out.println("Colliding down");
					if (isMoving(Direction.DOWN))
						newYPos = impassableTile.getYPosition()+getWorld().getTileSize()-1;
					endMovement(Direction.DOWN);
				}
				else if(isColliding(Direction.UP, impassableTile)){
					if (isMoving(Direction.UP))
						newYPos = impassableTile.getYPosition()-getHeight()+1;
					endMovement(Direction.UP);
					//System.out.println("Colliding up");
				}
				if(isColliding(Direction.LEFT, impassableTile)){
					if (isMoving(Direction.LEFT))
						newXPos = impassableTile.getXPosition()+getWorld().getTileSize()-1;
					endMovement(Direction.LEFT);
					//System.out.println("Colliding left");
				}
				else if(isColliding(Direction.RIGHT, impassableTile)){
					if (isMoving(Direction.RIGHT))
						newXPos = impassableTile.getXPosition()-getWidth()+1;
					endMovement(Direction.RIGHT);
					//System.out.println("Colliding right");
				}
			}
		}
		getPosition().terminate();
		setPosition(new Position(newXPos,newYPos,getWorld()));
	}

	@Override
	public void updateSpriteIndex() {
		setIndex((getIndex()+1)%2);
	}
	
}
