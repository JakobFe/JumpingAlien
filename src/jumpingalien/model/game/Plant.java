package jumpingalien.model.game;

import jumpingalien.model.exceptions.*;
import jumpingalien.model.program.programs.Program;
import static jumpingalien.tests.util.TestUtils.doubleArray;
import jumpingalien.util.Sprite;

import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class concerning plants as a subclass of game objects.
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version 2.0
 */
public class Plant extends GameObject {
	
	/**
	 * Create a new plant with given position, given sprites and given program.
	 * 
	 * @param	position
	 * 			The initial position for this plant.
	 * @param 	sprites
	 * 			The sprites for this plant.
	 * @param	program
	 * 			The program for this plant.
	 * @effect	...
	 * 			| super(position,PLANT_VELOCITY,sprites,1,program)
	 * @effect	...
	 * 			| setHorVelocity(PLANT_VELOCITY)
	 * @effect	...
	 * 			| setRandomDirection()
	 */
	@Raw
	public Plant(Position position, Sprite[] sprites, Program program){
		super(position,PLANT_VELOCITY,sprites,1,program);
		setHorVelocity(PLANT_VELOCITY);
		setRandomDirection();
	}
	
	/**
	 * Create a new plant with given position, given sprites adn without a program.
	 * 
	 * @param	position
	 * 			The initial position for this plant.
	 * @param 	sprites
	 * 			The sprites for this plant.
	 * @effect	...
	 * 			| this(position,sprites,null)
	 */
	@Raw
	public Plant(Position position, Sprite[] sprites) 
			throws IllegalXPositionException,IllegalYPositionException{
		this(position,sprites,null);
	}
	

	/**
	 * Check whether a program can be attached to this game object.
	 * 
	 * @return	...
	 * 			| result == true
	 */
	@Override@Model
	protected boolean canHaveProgram() {
		return true;
	}
	
	/**
	 * Sets the horizontal direction of this plant randomly to left or right.
	 * 
	 * @post	...
	 * 			| new.getHorDirection() == Direction.LEFT ||
	 * 			| new.getHorDirection() == Direction.RIGHT
	 * @post	...
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
	private Direction lastDirection = Direction.NULL;
	
	/**
	 * Checks whether the given horizontal acceleration is valid.
	 * 
	 * @param 	horAcceleration
	 * 			Horizontal acceleration to check.
	 * @return	True if and only if the given value lies between
	 * 			zero and the maximum horizontal acceleration.
	 * 			| result == (horAcceleration == 0)
	 */
	@Override@Model
	protected boolean canHaveAsHorAcceleration(double horAcceleration){
		return (horAcceleration == 0);
	}
	
	/**
	 * A method to check whether the given world is a valid world for this plant.
	 * 
	 * @return	...
	 * 			| if (world == null)
	 * 			|	then result == false
	 * 			| else
	 * 			|	result == (world.canAddGameObjects() && (world.hasAsGameObject(this)))
	 */
	@Override@Model
	protected boolean canBeAddedTo(World world) {
		return super.canBeAddedTo(world) && (world.hasAsGameObject(this));
	}
	
	/**
	 * Check whether this game object has a proper world.
	 * 
	 * @return	...
	 * 			| result == (getWorld() == null) || (getWorld().hasAsGameObject(this))
	 */
	@Override@Model
	protected boolean hasProperWorld() {
		return (getWorld() == null) || (getWorld().hasAsGameObject(this));
	}
	
	/**
	 * Method to update the position and velocity of this game object based on the current position,
	 * velocity and a given time duration in seconds.
	 * 
	 * @effect	...
	 * 			| if(hasProgram())
	 * 			|	then getProgram().execute(timeDuration)
	 * 			| else
	 * 			|	updateMovement()
	 * @effect	...
	 * 			| updateHitPoints(), updateTimers(timeDuration)
	 * @effect	...
	 * 			| new.getPosition() = f(getPosition(),getHorVelocity(),timeDuration,
	 * 			|						getHorDirection())
	 */
	@Override
	public void advanceTime(double timeDuration) throws IllegalXPositionException,
	IllegalYPositionException,IllegalTimeIntervalException{
		if (!isValidTimeInterval(timeDuration))
			throw new IllegalTimeIntervalException(this);
		if(hasProgram())
			getProgram().execute(timeDuration);
		else
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
	@Override@Model
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
	@Override@Model
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
	@Override@Model
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
	 * @return	A set of all game objects that can block the movement of this game object.
	 * 			...
	 * 			| result.contains(getWorld().getAllPlants())
	 * 			| result.contains(getWorld().getAllAliens())
	 * 			
	 */
	@SuppressWarnings("unchecked")
	@Override@Model
	protected Set<GameObject> getBlockingObjects() {
		return (Set<GameObject>)getWorld().filterAllGameObjects(t->
		((t instanceof Plant) || t instanceof Alien));
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
	@Override@Model
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
	@Override@Model
	protected void hurt(GameObject other){
		if(!(other instanceof Mazub))
			other.getHurtBy(this);
	}
	
	/**
	 * A method to take damage from another game object.
	 * 
	 * @effect	...
	 * 			| if(other instanceof Mazub)
	 * 			|	then setHitPoints(0),getHpTimer().reset(),
	 * 			|		 getWorld().getMazub().consumePlant()
	 * 			...
	 * 			| else
	 * 			|	other.hurt(this)
	 */
	@Override@Model
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
	 * 			| if(getHorDirection() == Direction.LEFT)
	 *			|	then setIndex(0)
	 *			| else
	 *			|	setIndex(1)
	 */
	@Override@Model
	protected void updateSpriteIndex() {
		if(getHorDirection() == Direction.LEFT)
			setIndex(0);
		else
			setIndex(1);
	}
	
	/**
	 * Terminate this plant.
	 * 
	 * @pre		...
	 * 			| isDead()
	 * @pre		...
	 * 			| getHpTimer().getTimeSum()>0.6
	 * @effect	...
	 * 			| super.terminate()
	 * @effect	...
	 * 			| if(getWorld() != null)
	 * 			| 	getWorld().removeAsGameObject(this)
	 * @effect	...
	 * 			| setWorld(null);
	 */
	@Override@Model
	protected void terminate(){
		assert isDead();
		assert getHpTimer().getTimeSum() >= 0.6;
		super.terminate();
		if(getWorld() != null)
			getWorld().removeAsGameObject(this);
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
	 * 			|				  "hit points") 
	 * @return	...
	 * 			| if(getProgram() != null)
	 * 			|	then result.contains(" and controlled by a program.")
	 * 			| else
	 * 			|	result.contains(".")
	 */
	@Override
	public String toString(){
		String message;
		if(getProgram() != null)
			message = " and controlled by a program.";
		else
			message = ".";
		return "Plant at " + getPosition().toString() + " with" +
				String.valueOf(getHitPoints())  + "hit points" + message;
		//return String.valueOf(getProgram() != null);
		//return "Plant";
	}
}
