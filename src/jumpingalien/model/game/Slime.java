package jumpingalien.model.game;

import static jumpingalien.tests.util.TestUtils.doubleArray;

import java.util.HashSet;
import java.util.Random;

import jumpingalien.model.exceptions.IllegalTimeIntervalException;
import jumpingalien.model.exceptions.IllegalXPositionException;
import jumpingalien.model.exceptions.IllegalYPositionException;
import jumpingalien.model.program.programs.Program;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class concerning slimes as a subclass of characters.
 * 
 * @invar	...
 * 			| hasProperSchool()
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version 1.0
 */
public class Slime extends Character{
	
	/**
	 * Initialize this new slime with given position,
	 * zero as its initial horizontal velocity, the maximum
	 * horizontal velocity for slimes as its maximum horizontal velocity,
	 * given sprites, given school and no world.	
	 * 
	 * @param 	position
	 * 			The start position for this character.
	 * @param	sprites
	 * 			An array containing the different sprites for this character. 
	 * @pre		...
	 * 			| canHaveAsMaxHorVelocity(SLIME_VELOCITY,0)
	 * @effect	...
	 * 			| super(position,0,SLIME_VELOCITY,sprites)
	 * @effect	...
	 * 			| school.addSlime(this)
	 */
	public Slime(Position position, Sprite[] sprites, School school){
		super(position,0,SLIME_VELOCITY,sprites,null);
		school.addSlime(this);
	}

	public Slime(Position position, Sprite[] sprites, School school, Program program){
		super(position,0,SLIME_VELOCITY,sprites,program);
		school.addSlime(this);
	}

	/**
	 * A method to set the horizontal direction of this slime randomly
	 * to the left or to the right.
	 * 
	 * @post	...
	 * 			| new.getHorDirection() == Direction.LEFT ||
	 * 			| new.getHorDirection() == Direction.RIGHT
	 */
	public void setRandomHorDirection() {
		Random rn = new Random();
		int startIndex = rn.nextInt(2);
		if(startIndex == 0)
			setHorDirection(Direction.LEFT);
		else
			setHorDirection(Direction.RIGHT);
	}
	
	/**
	 * A method to get the duration of the current period.
	 */
	public double getPeriodDuration() {
		return periodDuration;
	}
	
	/**
	 * A method to check whether a given period duration is valid.
	 * 
	 * @param 	period
	 * 			The period duration to check.
	 * @return	...
	 * 			| result == (period == 0 || (period <=MAX_PERIOD && period >= MIN_PERIOD)) 
	 */
	public boolean isValidPeriod(double period){
		return (period == 0 || (period <=MAX_PERIOD && period >= MIN_PERIOD));
	}
	
	/**
	 * A variable storing the minimum period duration for slimes.
	 */
	private static final double MIN_PERIOD = 2;
	
	/**
	 * A variable storing the maximum period duration for slimes.
	 */
	private static final double MAX_PERIOD = 6;
	

	/**
	 * A method to set the period duration to a given value.
	 * 
	 * @param 	periodDuration
	 * 			The period duration to set.
	 * @pre		...
	 * 			| isValidPeriod(periodDuration)
	 * @post	...
	 * 			| new.getPeriodDuration() == periodDuration
	 */
	public void setPeriodDuration(double periodDuration) {
		assert isValidPeriod(periodDuration);
		this.periodDuration = periodDuration;
	}
	
	/**
	 * A method to return a random period duration between the minimum period
	 * duration for slimes and the maximum period duration for slimes.
	 * 
	 * @return	...
	 * 			| result <= MAX_PERIOD && result >= MIN_PERIOD
	 */
	public double randomPeriodDuration(){
		Random rn = new Random();
		return MIN_PERIOD + (MAX_PERIOD - MIN_PERIOD) * rn.nextDouble();
	}
	
	/**
	 * A variable to store the current period duration.
	 */
	private double periodDuration = 0;
	
	/**
	 * Check whether this game object can be added to the given world.
	 * 
	 * @return	...
	 * 			| if (world == null)
	 * 			|	then result == false
	 * 			...
	 * 			| else 
	 * 			|	result == (world.canAddGameObjects()) &&
	 * 			|			  (world.hasAsSlime(this))
	 */ 
	@Override
	protected boolean canBeAddedTo(World world) {
		return super.canBeAddedTo(world) && (world.hasAsGameObject(this));
	}
	
	/**
	 * Check whether this game object has a proper world.
	 * 
	 * @return	...
	 * 			| if (getWorld() == null)
	 * 			|	then result == true
	 * 			...
	 * 			| else
	 * 			|	result == getWorld().hasAsSlime(this)
	 */
	@Override
	protected boolean hasProperWorld() {
		return (getWorld() == null) || (getWorld().hasAsGameObject(this));
	}
	
	/**
	 * Return the maximum horizontal velocity of this slime.
	 */
	@Override
	public final double getMaxHorVelocity(){
		return SLIME_VELOCITY;
	}
	
	/**
	 * A variable storing the velocity of a slime.
	 */
	private static final double SLIME_VELOCITY = 2.5;

	/**
	 * Return the maximum horizontal acceleration of the Slime.
	 */
	@Basic @Immutable @Model @Override
	protected double getMaxHorAcceleration(){
		return maxHorAcceleration;
	}
	
	/**
	 * A variable storing the maximum horizontal acceleration for this Slime.
	 * This variable must always store a positive number of type double or zero.
	 */
	private static final double maxHorAcceleration = 0.7;

	/**
	 * Return the school belonging to this slime.
	 */
	@Basic
	public School getSchool() {
		return school;
	}
	
	/**
	 * A method to check whether a given school is valid.
	 * 
	 * @param 	school
	 * 			The school to check.
	 * @return	...
	 * 			| school == null || school.hasAsSlime(this)
	 */
	protected boolean isValidSchool(School school){
		return school == null || school.hasAsSlime(this);
	}
	
	/**
	 * A method to check whether this slime has a proper school.
	 * 
	 * @return	...
	 * 			| result == isValidSchool(getSchool())
	 */
	protected boolean hasProperSchool(){
		return isValidSchool(getSchool());
	}
	
	/**
	 * A method to set the school of this slime to the given school.
	 * 
	 * @param 	school
	 * 			The school to set.
	 * @pre		...
	 * 			| isValidSchool(school)
	 * @post	...
	 * 			| new.getSchool() == school
	 */
	protected void setSchool(@Raw School school) {
		assert isValidSchool(school);
		this.school = school;
	}
	
	/**
	 * A variable storing the school where this slime is a part of.
	 */
	private School school;
	
	/**
	 * A method to start a movement period.
	 * 
	 * @effect	...
	 * 			| setHorVelocity(getInitHorVelocity()),
	 *			| setHorAcceleration(getMaxHorAcceleration())
	 *			| setPeriodDuration(randomPeriodDuration());
	 */
	private void startMove(){
		setHorVelocity(getInitHorVelocity());
		setHorAcceleration(getMaxHorAcceleration());
		setPeriodDuration(randomPeriodDuration());
	}
	
	@Override
	public void startMove(Direction direction) {
		assert ((direction == Direction.LEFT) || (direction == Direction.RIGHT));
		assert !isDead();
		setHorVelocity(getInitHorVelocity());
		setHorDirection(direction);
		setHorAcceleration(getMaxHorAcceleration());
		updateSpriteIndex();
	}
	
	/**
	 * A method to end a movement period.
	 * 
	 * @effect	...
	 * 			| setHorVelocity(0), setHorAcceleration(0),
	 * 			| setHorDirection(Direction.NULL)
	 */
	private void endMove(){
		setHorVelocity(0);
		setHorAcceleration(0);
		setHorDirection(Direction.NULL);
	}
	
	/**
	 * Method to update the position and velocity of the slime based on the current position,
	 * velocity and a given time duration in seconds.
	 * 
	 * @effect	...
	 * 			| updateSchool()
	 */
	@Override
	public void advanceTime(double timeDuration)throws IllegalXPositionException,
	IllegalYPositionException,IllegalTimeIntervalException{
		super.advanceTime(timeDuration);
		updateSchool();
	}
	
	/**
	 * A method to update the movements of this slime.
	 * As an effect of this method, certain movements may be started.
	 * 
	 * @effect	...
	 * 			| if(getPeriodDuration() == 0)
	 * 			|	then getSpritesTimer().setTimeSum(0), setRandomDirection(),
	 *			|		 updateSpriteIndex(), startMove()
	 *			| else if(getSpritesTimer().getTimeSum() >= getPeriodDuration())
	 *			|	then endMove(),setPeriodDuration(0) 
	 */
	@Override
	protected void updateMovement(){
		super.updateMovement();
		if(getPeriodDuration() == 0){
			getSpritesTimer().reset();
			setRandomHorDirection();
			updateSpriteIndex();
			startMove();
		}
		else if (getSpritesTimer().getTimeSum() >= getPeriodDuration()){
			endMove();
			setPeriodDuration(0);
		}
	}
	
	/** 
	 * Method to update the position of this slime based on the current position,
	 * velocity and a given time duration in seconds.
	 * 
	 * @post	...
	 * 			| let
	 * 			|	oldPos = getPosition(),
	 * 			|	newPos = f(getPosition(),getHorDirection(),getHorVelocity(),
	 * 			|			   getHorAcceleration(),getVertDirection(),
	 * 			|			   getVertVelocity(),getVertAcceleration(),timeDuration),
	 * 			|	newPos = updatePositionTileCollision(newPos.toDoubleArray()),
	 * 			|	newPos = updatePositionObjectCollision(newPos)
	 * 			| in
	 * 			|	setPosition(toPosition(newPos,getWorld())),
	 * 			|	oldPosition.terminate()
	 */
	@Override
	protected void updatePosition(double timeDuration) {
		double newXPos = getPosition().getXPosition() + getHorDirection().getFactor()*
				(getHorVelocity()*timeDuration+ 0.5*getHorAcceleration()*Math.pow(timeDuration, 2))*100;
		double newYPos = getPosition().getYPosition() + 
				((getVertDirection().getFactor()*getVertVelocity()*timeDuration)+ 
				0.5*getVertAcceleration()*Math.pow(timeDuration, 2))*100;
		if (newYPos<0)
			newYPos = 0;
		
		double[] newPos = updatePositionTileCollision(doubleArray(newXPos,newYPos));
		newPos = updatePositionObjectCollision(newPos);

		if (canFall() && !isMoving(Direction.UP)){
			startFall();
		}
		else
			setCanFall(true);
		getPosition().terminate();
		setPosition(toPosition(newPos, getWorld()));
	}
	
	/**
	 * Returns all game objects that can block the movement of this slime.
	 * 
	 * @return	...
	 * 			| result.contains(getWorld().getAllCharacters())
	 */
	@Override
	protected HashSet<GameObject> getBlockingObjects() {
		HashSet<GameObject> collection = new HashSet<GameObject>();
		collection.addAll(getWorld().getAllCharacters());
		return collection;
	}	
	
	/**
	 * A method to check whether this slime can be hurt by a certain terrain type.
	 *
	 * @return	...
	 * 			| result == (terrain == Terrain.WATER || terrain == Terrain.MAGMA)
	 */
	@Override
	protected boolean canBeHurtBy(Terrain terrain) {
		return (terrain == Terrain.WATER || terrain == Terrain.MAGMA);
	}
	
	/**
	 * A method to update the hit points of this slime.
	 * A slime can damage other objects and can be damaged
	 * by other game objects.
	 * 
	 * @effect	...
	 * 			| if (!isDead() && !isOverlappingWith(Terrain.WATER) 
	 *			|	  && !isOverlappingWith(Terrain.MAGMA))
	 *			|	then getHpTimer().reset()
	 * @effect	...	
	 * 			| if(!isDead())
	 * 			|	if(isOverlappingWith(Terrain.WATER))
	 * 			|		then updateHitPointsTerrain(Terrain.WATER)
	 * 			|	if(isOverlappingWith(Terrain.MAGMA))
	 * 			|		then updateHitPointsTerrain(Terrain.MAGMA)
	 * 			|	if (isOverlappingWith(alien) && !alien.isImmune() && !isDead())
	 * 			|		if (!alien.standsOn(this))
	 * 			|			then alien.getHurtBy(this)
	 * 			|		if(!isImmune())
	 * 			|			then getHurtBy(alien)
	 * @effect	...
	 * 			| let 
	 * 			|	oldHp = getHitPoints(), newHp = new.getHitPoints()
	 * 			| in
	 * 			|	if(oldHp>newHp)
	 * 			|		then getImmuneTimer().reset(),
	 * 			|		getSchool().reduceHpAll(this)
	 * 			|		if(new.isDead())
	 * 			|			then getHpTimer().reset()
	 * @effect	...
	 * 			| if(new.isDead() && new.getHpTimer().getTimeSum()>= 0.6)
	 * 			|	then terminate()
	 * @effect	...
	 * 			| for shark in getWorld().getAllSharks()
	 * 			|	if(!isDead() && isOverlappingWith(shark)
	 * 			|		if(!isImmune())
	 * 			|			getHurtBy(shark)
	 * 			|		if(!shark.isImmune())
	 * 			|			shark.getHurtBy(this);
	 */
	@Override
	protected void updateHitPoints(){
		Mazub alien = getWorld().getMazub();
		boolean isHurt = false;
		boolean isDamaged = false;
		if(!isDead()){
			if (!isOverlappingWith(Terrain.WATER) && !isOverlappingWith(Terrain.MAGMA))
				getHpTimer().reset();
			int oldHitPoints = getHitPoints();
			if(isOverlappingWith(Terrain.WATER)){
				updateHitPointsTerrain(Terrain.WATER);
			}
			if(isOverlappingWith(Terrain.MAGMA)){
				updateHitPointsTerrain(Terrain.MAGMA);
			}		
			if(getHitPoints()<oldHitPoints)
				isDamaged = true;
			if (!isDead() && alien != null && !alien.isImmune() && isOverlappingWith(alien)){
				if (!alien.standsOn(this)){
					alien.getHurtBy(this);
				}
				if(!isImmune()){
					getHurtBy(alien);
					isHurt = true;
				}
			}
			for (Shark shark: getWorld().getAllSharks()){
				if(!isDead() && isOverlappingWith(shark)){
					if(!isImmune()){
						getHurtBy(shark);
						isHurt = true;
					}
					if(!shark.isImmune())
						shark.getHurtBy(this);
				}
			}
		}
		if(isHurt || isDamaged)
			getSchool().reduceHpAll(this);
		if(isHurt)
			getImmuneTimer().reset();
		if (isHurt && isDead())
			getHpTimer().reset();
		else if (isDead() && getHpTimer().getTimeSum()>= 0.6){
			terminate();
		}
	}
	
	/**
	 * A method to take damage from another object.
	 * 
	 * @effect	...
	 * 			| if(!isImmune() && other instanceof Mazub)
	 * 			|	then subtractHp(50)
	 * 			| else if(other instanceof Shark)
	 *			|	then subtractHp(50)
	 * 			| else if(!(other instanceof Slime))
	 * 			|	then other.hurt(this)
	 */ 
	@Override
	protected void getHurtBy(GameObject other){
		if(!isImmune()){
			if(other instanceof Mazub)
				subtractHp(50);
			else if(other instanceof Shark)
				subtractHp(50);
			else if(!(other instanceof Slime))
				other.hurt(this);
		}
	}
	
	/**
	 * A method to get damage by another game object.
	 * 
	 * @effect	...
	 * 			| if(!other.isDead() && other instanceof Mazub && !((Mazub) other).isImmune() &&
	 *			|    !((Mazub) other).standsOn(this))
	 *			|	then ((Mazub) other).getImmuneTimer().reset(), other.subtractHp(50)
	 *			|	if(other.isDead())
	 *			|		then other.getHpTimer().reset()
	 *			| else if(other instanceof Shark && !((Shark) other).isImmune())
	 *			|	then other.subtractHp(50)
	 *			|	if(other.isDead())
	 *			|		then other.getHpTimer().reset()
	 *			| else if(!(other instanceof Slime))
	 *			|	other.getHurtBy(this)
	 */
	protected void hurt(GameObject other){
		if(other instanceof Mazub && !((Mazub) other).isImmune() &&
				!((Mazub) other).standsOn(this)){
			((Mazub) other).getImmuneTimer().reset();
			other.subtractHp(50);
			if (other.isDead())
				other.getHpTimer().reset();
		}	
		else if(other instanceof Shark && !((Shark) other).isImmune()){
			other.subtractHp(50);
			((Shark)other).getImmuneTimer().reset();
			if (other.isDead())
				other.getHpTimer().reset();
		}
		else if(!(other instanceof Slime))
			other.getHurtBy(this);
	}
	
	/**
	 * A method to update the school of this slime.
	 * 
	 * @effect	...
	 * 			| if (getWorld() != null && getWorld().getAllUnterminatedSlimes()!= null)
	 * 			|	then for each slime in getWorld().getAllUnterminatedSlimes()
	 * 			|		if(isOverlappingWith(other))
	 * 			|			if(other.getSchool().getNbSlimes() > this.getSchool().getNbSlimes())
	 * 			|				then this.changeSchool(other)
	 * 			|			else if (other.getSchool().getNbSlimes() < this.getSchool().getNbSlimes())
	 * 			|				then other.changeSchool(this)
	 */
	void updateSchool(){
		if (getWorld() != null && getWorld().getAllSlimes()!= null){
			for (Slime other: getWorld().getAllSlimes()){
				if(isOverlappingWith(other)){
					if(other.getSchool().getNbSlimes() > this.getSchool().getNbSlimes())
						this.changeSchool(other);
					else if (other.getSchool().getNbSlimes() < this.getSchool().getNbSlimes())
						other.changeSchool(this);
				}
			}
		}
	}
	
	/**
	 * Sets the school of this slime to the school of the other slime and handles 
	 * the transfers of hit points.
	 * 
	 * @param 	other
	 * 			The new school for this slime.
	 * @pre		...
	 * 			| getSchool() != null
	 * @effect	...
	 * 			| getSchool().addHpAll(this),
	 * 			| setHitPoints(getHitPoints()-getSchool().getNbSlimes()+1
	 * 			|              +other.getSchool().getNbSlimes())
	 * @effect	...
	 * 			| this.getSchool().removeSlime(this),
	 * 			| other.getSchool().addSlime(this),
	 * 			| other.getSchool().reduceHpAll(this)
	 */
	void changeSchool(Slime other){
		assert getSchool() != null;
		assert other.getSchool() != null;
		getSchool().addHpAll(this);
		setHitPoints(getHitPoints()-getSchool().getNbSlimes()+1+other.getSchool().getNbSlimes());
		getWorld().decrementValueOfSchool(this.getSchool());
		this.getSchool().removeSlime(this);
		other.getSchool().addSlime(this);
		getWorld().incrementValueOfSchool(other.getSchool());
		other.getSchool().reduceHpAll(this);
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
	@Override
	public void updateSpriteIndex() {
		if(getHorDirection() == Direction.LEFT)
			setIndex(0);
		else
			setIndex(1);
	}
	
	/**
	 * Return a textual representation of this shark.
	 *
	 * @return	...
	 * 			| result.contains(String.valueOf(getHitPoints()))
	 * @return	...
	 * 			| result.contains(getSchool().toString()) 
	 */
	@Override
	public String toString(){
		//return String.valueOf(getHitPoints()) + " - " + getSchool().toString();
		//return "Slime at " + getPosition().toString() + " with" +
		//String.valueOf(getHitPoints())  + "hit points.";
		return String.valueOf(getProgram() != null);
		//return "Slime";
	}
	
	/**
	 * Terminate this slime.
	 * 
	 * @pre		...
	 * 			| isDead()
	 * @pre		...
	 * 			| getHpTimer().getTimeSum()>0.6
	 * @effect	...
	 * 			| getWorld().removeAsSlime(this)
	 * @effect	...
	 * 			| setWorld(null);
	 */
	@Override
	protected void terminate(){
		assert getHitPoints() == 0;
		assert getHpTimer().getTimeSum() >= 0.6;
		super.terminate();
		getWorld().removeAsGameObject(this);
		setWorld(null);
	}

	@Override
	protected boolean canHaveProgram() {
		return true;
	}
}
