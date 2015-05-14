package jumpingalien.model.game;

import jumpingalien.model.exceptions.*;
import static jumpingalien.tests.util.TestUtils.doubleArray;
import jumpingalien.util.Sprite;

import java.util.HashSet;
import java.util.Random;

import be.kuleuven.cs.som.annotate.*;

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
	@Raw
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
	 */
	@Model
	private void setRandomDirection() {
		Random rn = new Random();
		int startIndex = rn.nextInt(2);
		if(startIndex == 0){
			setHorDirection(Direction.LEFT);
			setLastDirection(Direction.LEFT);
		}
		else{
			setHorDirection(Direction.RIGHT);
			setLastDirection(Direction.RIGHT);
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
	@Basic@Model
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
	@Override@Model
	protected boolean canBeAddedTo(World world) {
		return super.canBeAddedTo(world) && (world.hasAsPlant(this));
	}
	
	/**
	 * Check whether this game object has a proper world.
	 * 
	 * @return	...
	 * 			| result == (getWorld() == null) || (getWorld().hasAsPlant(this))
	 */
	@Override@Model
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
	
	@Override
	public void startMove(Direction direction) {
		
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
	@Model
	protected void updateMovement() {
		super.updateMovement();
		if (!isDead() && getSpritesTimer().getTimeSum()>0.5){
			alternateDirection();
			setLastDirection(getHorDirection());
			updateSpriteIndex();
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
	@Model
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
	@Override@Model
	protected void updatePosition(double timeDuration) {
		double newXPos = getPosition().getXPosition() + getHorDirection().getFactor()*
				(getHorVelocity()*timeDuration)*100;
		updatePositionTileCollision(doubleArray(newXPos,getPosition().getYPosition()));
		updatePositionObjectCollision(getPosition().toDoubleArray());
	}
	
	/**
	 *  A method that receives a position in the form of a double array 
	 * and returns the corrected position, after the given position has been checked 
	 * for whether or not this game object would collide with impassable tiles
	 * if the given position would be assigned to this game object.
	 * 
	 * @note	In the current state, this method violates several rules connected
	 * 			to good programming. It changes the state of an object and returns a value.
	 * 			We are aware of this problem and we will solve it by defensive programming
	 * 			before we hand in the final solution. 
	 */
	@Override@Model
	protected void updatePositionTileCollision(double[] newPos) {
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
		getPosition().terminate();
		setPosition(new Position(newXPos,newPos[1],getWorld()));
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
	@Override@Model
	protected HashSet<GameObject> getBlockingObjects() {
		HashSet<GameObject> collection = new HashSet<GameObject>();
		collection.addAll(getWorld().getAllPlants());
		collection.add(getWorld().getMazub());
		return collection;
	}
	
	/**
	 * A method to alternate the horizontal direction of this plant.
	 * 
	 * @effect	...
	 * 			| if(getLastDirection() == Direction.LEFT)
	 *			|	then setHorDirection(Direction.RIGHT)
	 * @effect	...
	 * 			| if(getLastDirection() == Direction.RIGHT)
	 *			|	then setHorDirection(Direction.LEFT)
	 */
	@Model
	private void alternateDirection(){
		if(getLastDirection() == Direction.LEFT)
			setHorDirection(Direction.RIGHT);
		else if(getLastDirection() == Direction.RIGHT)
			setHorDirection(Direction.LEFT);
	}
	
	/**
	 * A method to update the hit points of this game object.
	 * A game object can damage other objects and can be damaged
	 * by other game objects.
	 * 
	 * @effect	...
	 * 			| if(!isDead() && isOverlappingWith(getWorld().getMazub()) &&
	 *	   		| 	  getWorld().getMazub().canConsumePlant())
	 *			|	then getHurtBy(getWorld().getMazub())
	 * @effect	...
	 * 			| if(isDead() && getHpTimer().getTimeSum() > 0.6)
	 * 			|	then terminate()
	 * 			
	 */
	@Model
	protected void updateHitPoints(){
		if(!isDead() && isOverlappingWith(getWorld().getMazub()) &&
		   getWorld().getMazub().canConsumePlant()){
			getHurtBy(getWorld().getMazub());
		}
		if (isDead() && getHpTimer().getTimeSum() > 0.6){
			terminate();
		}
	}
	
	/**
	 * A method to damage another game object.
	 * 
	 * @effect	...
	 * 			| if(!(other instanceof Mazub))
	 * 			|	then other.getHurtBy(this)
	 */
	@Model
	protected void hurt(GameObject other){
		if(!(other instanceof Mazub))
			other.getHurtBy(this);
	}
	
	/**
	 * A method to get damage by another game object.
	 * 
	 * @effect	...
	 * 			| if(other instanceof Mazub)
	 * 			|	then setHitPoints(0),getHpTimer().reset(),
	 * 			|		 getWorld().getMazub().consumePlant()
	 * 			...
	 * 			| else
	 * 			|	other.hurt(this)
	 */
	@Model
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
	 * A method to update the sprite index.
	 * 
	 * @effect	...
	 * 			| setIndex((getIndex()+1)%2)
	 */
	@Override@Model
	protected void updateSpriteIndex(){
		setIndex((getIndex()+1)%2);
	}
	
	/**
	 * Terminate this game object.
	 * 
	 * @pre		.
	 * 			| isDead()
	 * @pre		...
	 * 			| getHpTimer().getTimeSum()>0.6
	 * @effect	...
	 * 			| getWorld().removeAsPlant(this)
	 * @effect	...
	 * 			| setWorld(null)
	 */ 
	@Override@Model
	protected void terminate(){
		super.terminate();
		getWorld().removeAsPlant(this);
		setWorld(null);
	}
	
	/**
	 * Return a textual representation of this plant.
	 * 
	 * @return	...
	 * 			| result.contains("Plant at")
	 * @return	...
	 * 			| result.contains(getPosition().toString())
	 * @return	...
	 * 			| result.contains("with" + String.valueOf(getHitPoints()+
	 * 			|				  "hit points.") 
	 */
	@Override
	public String toString(){
		return "Plant at " + getPosition().toString() +  " with" +
							String.valueOf(getHitPoints()) + "hit points.";
	}
}
