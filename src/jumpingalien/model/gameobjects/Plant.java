package jumpingalien.model.gameobjects;

import jumpingalien.model.exceptions.*;
import static jumpingalien.tests.util.TestUtils.doubleArray;
import jumpingalien.model.other.*;
import jumpingalien.model.worldfeatures.*;
import jumpingalien.util.Sprite;

import java.util.HashSet;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;

/**
 * A class concerning plants as a subclass of game objects.
 * 
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version 1.0
 */
public class Plant extends GameObject {
	
	/**
	 * Create a new plant with given x position, given y position and given sprites.
	 * 
	 * @param 	x
	 * 			The x position for this plant.
	 * @param 	y
	 * 			The x position for this plant.
	 * @param 	sprites
	 * 			The sprites for this plant.
	 * @effect	This plant is created as a new game object with given x position,
	 * 			given y position, an initial horizontal velocity of 0.5 m/s,
	 * 			given sprites and one hit point.
	 * 			| super(x,y,PLANT_VELOCITY,sprites,1)
	 * @effect	The horizontal velocity is set to 0.5 m/s.
	 * 			| setHorVelocity(getInitHorVelocity())
	 * @post	The direction is randomly set to left or right.
	 * 			| new.getHorDirection() == Direction.LEFT ||
	 * 			| new.getHorDirection() == Direction.RIGHT
	 */
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
		setLastDirection(getHorDirection());
	}
	
	/**
	 * Return the horizontal velocity of this plant.
	 */
	@Override
	public final double getHorVelocity(){
		return PLANT_VELOCITY;
	}
	
	/**
	 * A variable storing the velocity of a plant.
	 * Its value is always 0.5 m/s.
	 */
	private static final double PLANT_VELOCITY = 0.5;
	
	/**
	 * A method to check whether the given world is a valid world for this plant.
	 * 
	 * @return	The given world is effective and already has a reference to this plant.
	 * 			| result == (world != null) && (world.hasAsPlant(this)
	 */
	@Override
	protected boolean isValidWorld(World world) {
		return (world == null || world.hasAsPlant(this));
	}
	
	/**
	 * Return the last registered horizontal direction of the Mazub.
	 */
	@Basic @Model
	private Direction getLastDirection() {
		return lastDirection;
	}

	/**
	 * Sets the last registered horizontal direction to the given last direction.
	 * 
	 * @param 	lastDirection
	 * 			the lastDirection to set.
	 * @post	The new last direction equals the given lastDirection.
	 * 			| new.getLastDirection == lastDirection
	 */
	@Model
	private void setLastDirection(Direction lastDirection) {
		this.lastDirection = lastDirection;
	}
	
	/**
	 * A variable storing the last horizontal direction of movement of this Mazub
	 * within the last second of in-game-time.
	 */
	private Direction lastDirection;

	
	/**
	 * Method to update the position and velocity of this game object based on the current position,
	 * velocity and a given time duration in seconds.
	 * 
	 * @effect	If the current time sum is greater than 0.5, the horizontal direction is alternated.
	 * 			| if(getTimeSum() > 0.5)
	 * 			|	then alternateDirection()
	 * @effect	If the current time sum is greater than 0.5, the sprite index is updated.
	 * 			| if(getTimeSum() > 0.5)
	 * 			|	then updateSpriteIndex()
	 * 			... 
	 */
	@Override
	public void advanceTime(double timeDuration) throws IllegalXPositionException,
	IllegalYPositionException,IllegalTimeIntervalException{
		if (!isValidTimeInterval(timeDuration))
			throw new IllegalTimeIntervalException(this);
		if(!isDead()){
			if (getSpritesTimer().getTimeSum()>0.5){
				alternateDirection();
				setLastDirection(getHorDirection());
				setHorVelocity(PLANT_VELOCITY);
				getSpritesTimer().setTimeSum((getSpritesTimer().getTimeSum()-0.5));
			}
			double td = getTimeToMoveOnePixel(timeDuration);
			for (int index = 0; index < timeDuration/td; index++)
					updatePosition(td);
		}
		updateHitPoints();
		updateTimers(timeDuration);
	}
	
	
	protected double getTimeToMoveOnePixel(double timeDuration){
		if(getHorVelocity()!=0)
			return 0.01/(Math.abs(getHorVelocity()));
		else
			return timeDuration;
	}
	
	/**
	 * A method to update the position of this plant.
	 * ...
	 */
	@Override
	protected void updatePosition(double timeDuration) {
		double newXPos = getPosition().getXPosition() + getHorDirection().getFactor()*
				(getHorVelocity()*timeDuration)*100;
		for (Tile impassableTile: getWorld().getImpassableTiles()){
			if (this.isOverlappingWith(impassableTile)){
				if(isColliding(Direction.LEFT, impassableTile)){
					if (isMoving(Direction.LEFT))
						newXPos = this.getPosition().getXPosition();
					endMovement(Direction.LEFT);
					//System.out.println("Colliding left");
				}
				else if(isColliding(Direction.RIGHT, impassableTile)){
					if (isMoving(Direction.RIGHT))
						newXPos = this.getPosition().getXPosition();
					//System.out.println("Colliding right");
					endMovement(Direction.RIGHT);
				}
			}
		}
		double[] newPos = updatePositionObjectCollision(doubleArray(newXPos,this.getPosition().getYPosition()));
		newXPos = newPos[0];
		getPosition().terminate();
		setPosition(new Position(newXPos,getPosition().getYPosition(),getWorld()));
	}
	
	@Override
	protected double[] updatePositionObjectCollision(double[] newPos){
		HashSet<GameObject> collection = new HashSet<GameObject>();
		collection.addAll(getWorld().getAllPlants());
		collection.add(getWorld().getMazub());
		return getPositionAfterCollision(newPos,collection);
	}
	
	protected void updateHitPoints(){
		if(isOverlappingWith(getWorld().getMazub()) &&
		   getWorld().getMazub().canConsumePlant() && !isDead()){
			getHurtBy(getWorld().getMazub());
		}
		if (isDead() && getHpTimer().getTimeSum() > 0.6){
			terminate();
		}
	}
	
	protected void getHurtBy(GameObject other){
		if(other instanceof Mazub){
			setHitPoints(0);
			getHpTimer().reset();
			getWorld().getMazub().consumePlant();
		}
		else
			other.hurt(this);
	}
	
	/**
	 * A method to alternate the horizontal direction of this plant.
	 * 
	 * @effect	If the current direction is left, the direction is set to right.
	 * 			| if(getHorDirection() == Direction.LEFT)
	 *			|	then setHorDirection(Direction.RIGHT)
	 * @effect	If the current direction is right, the direction is set to left.
	 * 			| if(getHorDirection() == Direction.RIGHT)
	 *			|	then setHorDirection(Direction.LEFT)
	 */
	private void alternateDirection(){
		if(getLastDirection() == Direction.LEFT)
			setHorDirection(Direction.RIGHT);
		else if(getLastDirection() == Direction.RIGHT)
			setHorDirection(Direction.LEFT);
	}
	
	@Override
	public void updateSpriteIndex(){}
	
	@Override
	public void terminate(){
		assert getHpTimer().getTimeSum() >= 0.6;
		super.terminate();
		getWorld().removeAsPlant(this);
		setWorld(null);
	}
	
	@Override
	public String toString(){
		return "Plant at " + getPosition().getDisplayedXPosition() + "," +
							 getPosition().getDisplayedYPosition() + " with" +
							 getHitPoints() + "hit points.";
	}
}
