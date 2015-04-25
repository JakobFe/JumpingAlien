package jumpingalien.model;

import jumpingalien.model.exceptions.*;
import static jumpingalien.tests.util.TestUtils.doubleArray;
import jumpingalien.util.Sprite;

import java.util.HashSet;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;

/**
 * A class concerning plants as a subclass of game objects.
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version 1.0
 */
public class Plant extends GameObject {
	
	/**
	 * Create a new plant with given x position, given y position and given sprites.
	 * 
	 * @param	position
	 * 			The initial position for this plant.
	 * @param 	sprites
	 * 			The sprites for this plant.
	 * @effect	...
	 * 			| super(position,PLANT_VELOCITY,sprites,1)
	 * @effect	...
	 * 			| setHorVelocity(getInitHorVelocity())
	 * @effect	...
	 * 			| setRandomDirection()
	 */
	public Plant(Position position, Sprite[] sprites) 
			throws IllegalXPositionException,IllegalYPositionException{
		super(position,PLANT_VELOCITY,sprites,1);
		setHorVelocity(PLANT_VELOCITY);
		setRandomDirection();
	}
	
	/**
	 * Sets the horizontal direction of this plant randomly to left or right.
	 * 
	 * @post	...
	 * 			| new.getHorDirection() == Direction.LEFT ||
	 * 			| new.getHorDirection() == Direction.RIGHT
	 * @effect	...
	 * 			| new.getLastDirection() == new.getHorDirection()
	 * @effect	...
	 * 			| if (new.getHorDirection() == Direction.LEFT)
	 * 			|	then new.getIndex() == 0
	 * 			| else
	 * 			|	new.getIndex() == 1	 
	 */
	private void setRandomDirection() {
		Random rn = new Random();
		int startIndex = rn.nextInt(2);
		if(startIndex == 0){
			setHorDirection(Direction.LEFT);
			setLastDirection(Direction.LEFT);
			setIndex(0);
		}
		else{
			setHorDirection(Direction.RIGHT);
			setLastDirection(Direction.RIGHT);
			setIndex(1);
		}
	}	
	
	/**
	 * A variable storing the velocity of a plant.
	 * Its value is always the same.
	 */
	private static final double PLANT_VELOCITY = 0.5;
	
	/**
	 * Return the last registered horizontal direction of this plant.
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
	 * @pre		...
	 * 			| lastDirection == Direction.LEFT || lastDirection == Direction.RIGHT
	 * @post	...
	 * 			| new.getLastDirection() == lastDirection
	 */
	@Model
	private void setLastDirection(Direction lastDirection) {
		assert lastDirection == Direction.LEFT || lastDirection == Direction.RIGHT;
		this.lastDirection = lastDirection;
	}
	
	/**
	 * A variable storing the last horizontal direction of movement of this plant.
	 */
	private Direction lastDirection;
	
	/**
	 * A method to check whether the given world is a valid world for this plant.
	 * 
	 * @return	...
	 * 			| if (world == null)
	 * 			|	then result == false
	 * 			| else
	 * 			|	result == (world.canAddGameObjects() && (world.hasAsPlant(this)))
	 */
	@Override
	protected boolean canBeAddedTo(World world) {
		return super.canBeAddedTo(world) && (world.hasAsPlant(this));
	}
	
	/**
	 * Check whether this game object has a proper world.
	 * 
	 * @return	...
	 * 			| result == (getWorld() == null) || (getWorld().hasAsPlant(this))
	 */
	@Override
	protected boolean hasProperWorld() {
		return (getWorld() == null) || (getWorld().hasAsPlant(this));
	}
	
	/**
	 * Method to update the position and velocity of this game object based on the current position,
	 * velocity and a given time duration in seconds.
	 */
	@Override
	public void advanceTime(double timeDuration) throws IllegalXPositionException,
	IllegalYPositionException,IllegalTimeIntervalException{
		if (!isValidTimeInterval(timeDuration))
			throw new IllegalTimeIntervalException(this);
		updateMovement();
		double td = getTimeToMoveOnePixel(timeDuration);
		for (int index = 0; index < timeDuration/td; index++)
			updatePosition(td);
		updateHitPoints();
		updateTimers(timeDuration);
	}
	
	/**
	 * A method to update the movements of this game object.
	 * As an effect of this method, certain movements may be started.
	 * 
	 * @effect	...
	 * 			| if (!isDead() && getSpritesTimer().getTimeSum()>0.5)
	 * 			|	then  alternateDirection(), setLastDirection(getHorDirection()),
	 *			|		  setHorVelocity(PLANT_VELOCITY), getSpritesTimer().decrement(0.5)
	 */
	protected void updateMovement() {
		super.updateMovement();
		if (!isDead() && getSpritesTimer().getTimeSum()>0.5){
			alternateDirection();
			setLastDirection(getHorDirection());
			setHorVelocity(PLANT_VELOCITY);
			getSpritesTimer().decrement(0.5);
		}
	}
	
	/**
	 * A method to estimate the time duration needed to travel 0.01 meters,
	 * given a certain time duration.
	 *  
	 * @return	...
	 * 			| if(getHorVelocity() == 0)
	 * 			|	then result == timeDuration
	 * 			| else
	 * 			|	result == 0.01/(Math.abs(getHorVelocity()))
	 */
	protected double getTimeToMoveOnePixel(double timeDuration){
		if(getHorVelocity()!=0)
			return 0.01/(Math.abs(getHorVelocity()));
		else
			return timeDuration;
	}
	
	/** 
	 * Method to update the position of this game object based on the current position,
	 * velocity and a given time duration in seconds.
	 * 
	 * @post	...
	 * 			| let
	 * 			|	oldPos = getPosition(),
	 * 			|	newPos = f(getPosition(),getHorDirection(),getHorVelocity(),timeDuration),
	 * 			|	newPos = updatePositionTileCollision(newPos.toDoubleArray()),
	 * 			|	newPos = updatePositionObjectCollision(newPos)
	 * 			| in
	 * 			|	setPosition(toPosition(newPos,getWorld())),
	 * 			|	oldPosition.terminate()
	 */ 
	@Override
	protected void updatePosition(double timeDuration) {
		double newXPos = getPosition().getXPosition() + getHorDirection().getFactor()*
				(getHorVelocity()*timeDuration)*100;
		double[] newPos = updatePositionTileCollision(doubleArray(newXPos,getPosition().getYPosition()));
		newPos = updatePositionObjectCollision(doubleArray(newXPos,this.getPosition().getYPosition()));
		getPosition().terminate();
		setPosition(toPosition(newPos, getWorld()));
	}
	
	/**
	 *  A method that receives a position in the form of a double array 
	 * and returns the corrected position, after the given position has been checked 
	 * for whether or not this game object would collide with impassable tiles
	 * if the given position would be assigned to this game object.
	 */
	@Override
	protected double[] updatePositionTileCollision(double[] newPos) {
		double newXPos = newPos[0];
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
		return doubleArray(newXPos,newPos[1]);
	}
	
	/**
	 * Returns all game objects that can block the movement of this game object.
	 * 
	 * @return	A hash set of all game objects that can block the movement of this game object.
	 * 			...
	 * 			| result.contains(getWorld().getAllPlants())
	 * 			| result.contains(getWorld().getMazub())
	 * 			
	 */
	protected HashSet<GameObject> getBlockingObjects() {
		HashSet<GameObject> collection = new HashSet<GameObject>();
		collection.addAll(getWorld().getAllPlants());
		collection.add(getWorld().getMazub());
		return collection;
	}
	
	/**
	 * A method to update the hit points of this game object.
	 * A game object can damage other objects and can be damaged
	 * by other game objects.
	 * 
	 * @effect	...
	 * 			| if(!isDead() && isOverlappingWith(getWorld().getMazub()) &&
	 *	   		| 	  getWorld().getMazub().canConsumePlant())
	 *			|	then 
	 * 			
	 */
	protected void updateHitPoints(){
		if(!isDead() && isOverlappingWith(getWorld().getMazub()) &&
		   getWorld().getMazub().canConsumePlant()){
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
	
	protected void hurt(GameObject other){
		if(!(other instanceof Mazub))
			other.getHurtBy(this);
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
