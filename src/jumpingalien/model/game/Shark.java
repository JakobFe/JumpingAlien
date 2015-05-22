package jumpingalien.model.game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import jumpingalien.model.program.programs.Program;
import jumpingalien.util.Sprite;
import static jumpingalien.tests.util.TestUtils.doubleArray;

/**
 * A class concerning sharks as a subclass of characters.
 * 
 * @author 	Jakob Festraets, Vincent Kemps
 * @version	1.0
 */
public class Shark extends Character implements JumpInterface{
	
	/**
	 * Initialize this new shark with a given position, given sprites the initial
	 * horizontal velocity for sharks, the maximum horizontal velocity for sharks
	 * and the starting amount of hit points for sharks.
	 * 
	 * @param 	position
	 * 			The new position for this shark.
	 * @param 	sprites
	 * 			The sprites for this shark.
	 * @effect	...
	 * 			super(position,SHARK_INIT_VEL,SHARK_MAX_VEL,sprites,SHARK_HP)
	 */
	public Shark(Position position,Sprite[] sprites) 
			throws IllegalArgumentException {
		super(position,SHARK_INIT_VEL,SHARK_MAX_VEL,sprites,SHARK_HP,null);
	}
	
	public Shark(Position position,Sprite[] sprites, Program program) 
			throws IllegalArgumentException {
		super(position,SHARK_INIT_VEL,SHARK_MAX_VEL,sprites,SHARK_HP,program);
	}
	
	/**
	 * A variable storing the initial amount of hit points for sharks.
	 */
	private static final int SHARK_HP = 100;
	
	/**
	 * A method to set the horizontal direction of this shark randomly
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
	 * A method to set the vertical direction of this shark randomly
	 * to up or down.
	 * 
	 * @post	...
	 * 			| new.getVertDirection() == Direction.UP||
	 * 			| new.getVertDirection() == Direction.DOWN
	 */
	private void setRandomVertDirection() {
		Random rn = new Random();
		int startIndex = rn.nextInt(2);
		if(startIndex == 1)
			setVertDirection(Direction.UP);
		else if (startIndex == 0)
			setVertDirection(Direction.DOWN);
	}
	
	/**
	 * A method to set the vertical acceleration of this shark randomly
	 * between a diving acceleration for sharks and a rising acceleration for sharks.
	 * 
	 * @post	...
	 * 			| new.getVertAcceleration() >= SHARK_DIVING_ACCEL &&
	 * 			| new.getVertAcceleration() <= SHARK_RISING_ACCEL 
	 */
	private void setRandomVertAcceleration(){
		Random rn = new Random();
		setVertAcceleration(SHARK_DIVING_ACCEL + 
				((SHARK_RISING_ACCEL-SHARK_DIVING_ACCEL)*rn.nextDouble()));
	}
	
	/**
	 * A variable storing the acceleration of a shark when diving.
	 */
	private static final double SHARK_DIVING_ACCEL = -0.2;
	
	/**
	 * A variable storing the acceleration of a shark when rising.
	 */
	private static final double SHARK_RISING_ACCEL = 0.2;
	
	/**
	 * Return the initial horizontal velocity of this shark.
	 */
	@Override
	protected final double getInitHorVelocity(){
		return SHARK_INIT_VEL;
	}
	
	/**
	 * A variable storing the initial horizontal velocity for sharks.
	 */
	private static final double SHARK_INIT_VEL = 0;
	
	/**
	 * Return the current maximum horizontal velocity for this shark.
	 */
	@Override
	public final double getMaxHorVelocity() {
		return SHARK_MAX_VEL; 
	}
	
	/**
	 * A variable storing the maximum horizontal velocity for sharks.
	 */
	private static final double SHARK_MAX_VEL = 4;
	
	/**
	 * Return the maximum horizontal acceleration of this shark.
	 */
	@Override
	public final double getMaxHorAcceleration() {
		return SHARK_MAX_HOR_ACCEL;
	}
	
	/**
	 * A variable storing the maximum horizontal acceleration for sharks.
	 */
	private static final double SHARK_MAX_HOR_ACCEL = 1.5;
	
	/**
	 * A method to check whether the given value is a 
	 * valid vertical acceleration.
	 * 
	 * @return	...
	 * 			| result == ((acceleration >= SHARK_DIVING_ACCEL && 
	 *			|			  acceleration<= SHARK_RISING_ACCEL) ||
	 *			|			 acceleration == 0 || acceleration == getMaxVertAcceleration) 
	 */ 
	@Override
	public boolean canHaveAsVertAcceleration(double acceleration){
		return ((acceleration >= SHARK_DIVING_ACCEL && 
				acceleration<= SHARK_RISING_ACCEL) ||
				super.canHaveAsVertAcceleration(acceleration));
	}
	
	/**
	 * Return the initial vertical velocity of this shark.
	 */
	@Override
	protected final double getInitVertVelocity() {
		return INIT_VERT_VELOCITY;
	}
	
	/**
	 * A variable storing the initial vertical velocity for sharks.
	 */
	private static final double INIT_VERT_VELOCITY = 2;
	
	/**
	 * Check whether this game object can be added to the given world.
	 * 
	 * @return	...
	 * 			| if (world == null)
	 * 			|	then result == false
	 * 			...
	 * 			| else 
	 * 			|	result == (world.canAddGameObjects()) &&
	 * 			|			  (world.hasAsShark(this))
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
	 * 			|	result == getWorld().hasAsShark(this)
	 */
	@Override
	protected boolean hasProperWorld() {
		return (getWorld() == null) || (getWorld().hasAsGameObject(this));
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
	 * A variable storing the minimum period duration for sharks.
	 */
	private static final double MIN_PERIOD = 1;
	
	/**
	 * A variable storing the maximum period duration for sharks.
	 */
	private static final double MAX_PERIOD = 4;
	
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
	 * duration for sharks and the maximum period duration for sharks.
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
	 * A method to return the amount of periods that have passed since the last
	 * jumping period.
	 */
	private int getPeriodCounter() {
		return periodCounter;
	}
	
	/**
	 * A method to set the period counter to a given value.
	 * 
	 * @param 	periodCounter
	 * 			The value to set.
	 * @post	...
	 * 			| new.getPeriodCounter() == periodCounter
	 */
	private void setPeriodCounter(int periodCounter) {
		this.periodCounter = periodCounter;
	}
	
	/**
	 * A method to add a period to the period counter.
	 * 
	 * @effect	...
	 * 			| setPeriodCounter(getPeriodCounter()+1)
	 */
	private void addPeriod(){
		setPeriodCounter(getPeriodCounter()+1);
	}
	
	/**
	 * A variable storing the amount of periods since the last jumping period.
	 */
	private int periodCounter;
	
	/**
	 * A method to check whether this shark can jump.
	 * 
	 * @return	...
	 * 			| result == ((getPeriodCounter() > MIN_NON_JUMPING_PERIOD) &&
	 *			| 			 (standsOnTile() || isSubmergedIn(Terrain.WATER)))
	 */
	private boolean canJump(){
		return ((getPeriodCounter() > MIN_NON_JUMPING_PERIOD)
				&& (!canFall() || isSubmergedIn(Terrain.WATER)));
	}
	
	/**
	 * A variable that stores the minimum amount of non jumping periods that should
	 * be in between to jumping periods.
	 */
	private static final int MIN_NON_JUMPING_PERIOD = 4;
	
	/**
	 * A method to start a movement period for this shark.
	 * 
	 * @effect	...
	 * 			| setHorVelocity(getInitHorVelocity())
	 * @effect	...
	 * 			| setHorAcceleration(getMaxHorAcceleration())
	 * @effect	...
	 *			| setPeriodDuration(randomPeriodDuration())
	 * @effect	...
	 * 			| if(canJump())
	 * 			|	setRandomVertDirection()
	 * 			|	if(new.getVertDirection() == Direction.UP)
	 * 			|		startJump()
	 * 			| if(new.getVertDirection() == Direction.UP)
	 * 			|		riseOrDive() 	 				
	 */
	private void startMove(){
		setHorVelocity(getInitHorVelocity());
		setHorAcceleration(getMaxHorAcceleration());
		setPeriodDuration(randomPeriodDuration());
		if (canJump()){
			setRandomVertDirection();
			if (isMoving(Direction.UP)){
				startJump();
			}
		}
		if(!canJump() || !isMoving(Direction.UP))
			riseOrDive();
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
	 * A method to start a rising or diving activity.
	 * 
	 * @effect	...
	 * 			| if (isSubmergedIn(Terrain.WATER))
	 * 			|	then setRandomVertAcceleration(),
	 * 			|	if (new.getVertAcceleration()<0)
	 * 			|		then setDiving(true),setVertDirection(Direction.DOWN)
	 * 			|	else
	 * 			|		setVertDirection(Direction.UP),setRising(true)
	 */
	private void riseOrDive() {
		if(isSubmergedIn(Terrain.WATER)){
			setRandomVertAcceleration();
			if(getVertAcceleration()<0){
				setVertDirection(Direction.DOWN);
				setDiving(true);
			}
			else{
				setVertDirection(Direction.UP);
				setRising(true);
			}
		}
	}
	
	/**
	 * Returns whether this shark is currently diving.
	 */
	private boolean isDiving() {
		return isDiving;
	}
	
	/**
	 * A method to change the diving state of this shark.
	 * 
	 * @param 	isDiving
	 * 			The new diving state for this shark.
	 * @post	...
	 * 			| new.isDiving() == isDiving
	 */
	private void setDiving(boolean isDiving) {
		this.isDiving = isDiving;
	}
	
	/**
	 * A variable storing the diving state of this shark.
	 * 
	 * If a shark is diving, it is swimming down.
	 */
	private boolean isDiving; 
	
	/**
	 * Returns whether this shark is currently rising.
	 */
	private boolean isRising() {
		return isRising;
	}
	
	/**
	 * A method to change the rising state of this shark.
	 * 
	 * @param 	isRising
	 * 			The new rising state for this shark.
	 * @post	...
	 * 			| new.isRising() == isRising
	 */
	private void setRising(boolean isRising) {
		this.isRising = isRising;
	}
	
	/**
	 * A variable storing the rising state of this shark.
	 * 
	 * If a shark is rising, it is swimming up.
	 */
	private boolean isRising;
	
	/**
	 * A method to start a jump.
	 * 
	 * @pre		...
	 * 			| canJump()
	 * @effect	...
	 * 			| setPeriodCounter(0),
	 * 			| setVertVelocity(INIT_VERT_VELOCITY),
	 * 			| setVertAcceleration(getMaxVertAcceleration())
	 */
	public void startJump() {
		assert canJump();
		setPeriodCounter(0);
		setVertVelocity(INIT_VERT_VELOCITY);
		setVertAcceleration(getMaxVertAcceleration());
	}
	
	/**
	 * Method to end the jumping movement of the Mazub.
	 * 
	 * @effect	if the Mazub is still moving up, the vertical velocity is set to zero.
	 * 			| if (getVertDirection() == Direction.UP)
	 *			|	setVertVelocity(0)
	 */
	public void endJump(){
		if ((getVertDirection() == Direction.UP) && !isRising())
			setVertVelocity(0);
	}

	/**
	 * A method to end a moving period of this shark.
	 * 
	 * @effect	...
	 * 			| setDiving(false), setRising(false), setHorVelocity(0),
	 * 			| setHorAcceleration(0)
	 * @effect	...
	 * 			| if(isMoving(Direction.UP))
	 * 			| 	then setVertVelocity(0), setVertDirection(Direction.NULL)
	 * @effect	...
	 * 			| if(isMoving(Direction.UP) && isRising())
	 * 			|	then setVertAcceleration(0),addPeriod()
	 * 			| else
	 * 			|	addPeriod()
	 */
	private void endMove(){
		if (isMoving(Direction.UP)){
			setVertVelocity(0);
			setVertDirection(Direction.NULL);
			if(isRising()){
				setVertAcceleration(0);
				addPeriod();
			}
		}
		else
			addPeriod();
		setDiving(false);
		setRising(false);
		setHorVelocity(0);
		setHorAcceleration(0);
	}	
	
	/**
	 * A method to update the movements of this shark.
	 * As an effect of this method, certain movements may be started
	 * 
	 * @post	...
	 * 			| if(isDead())
	 * 			|	then new.getHorAcceleration() == 0
	 * @post	...
	 * 			| if(isDead() && isMoving(Direction.UP))
	 * 			|	then new.getVertVelocity() == 0 &&
	 * 			|		 new.getVertDirection() == Direction.NULL
	 * @post	...
	 * 			| if(isDead() && isDiving() || isRising())
	 * 			| 	then new.getVertAcceleration() == 0
	 * @effect	...
	 * 			| if(getPeriodDuration() == 0)
	 * 			|	then getSpritesTimer().setTimeSum(0), setRandomHorDirection(),
	 *			|		 updateSpriteIndex(), startMove()
	 *			| else if(getSpritesTimer().getTimeSum() >= getPeriodDuration())
	 *			|	then endMove(),setPeriodDuration(0) 
	 */
	@Override
	protected void updateMovement(){
		super.updateMovement();
		if(isDead() && (isDiving() || isRising()))
			setVertAcceleration(0);
		if(getPeriodDuration() == 0){
			getSpritesTimer().setTimeSum(0);
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
	 * Method to update the position of this shark based on the current position,
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
		double[] newPos = doubleArray(newXPos,newYPos);
		
		if(getWorld() != null){
			newPos = updatePositionTileCollision(doubleArray(newXPos,newYPos));
			newPos = updatePositionObjectCollision(newPos);
		}
		
		if ((canFall() && !isSubmergedIn(Terrain.WATER)) && !isMoving(Direction.UP))
			startFall();
		else
			setCanFall(true);
		
		getPosition().terminate();
		setPosition(toPosition(newPos,getWorld()));
	}
	
	/**
	 * Returns all game objects that can block the movement of this shark.
	 * 
	 * @return	...
	 * 			| result.contains(getWorld().getAllCharacters())
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Set<GameObject> getBlockingObjects() {
		return (Set<GameObject>)getWorld().filterAllGameObjects(t-> t instanceof Character);	
	}
	
	/**
	 * A method to check whether this shark is fully submerged in a terrain type.
	 * 
	 * @param 	terrain
	 * 			The terrain type to check for.
	 * @return	If there exists a tile that overlaps with this game object but
	 * 			has as terrain type different from the given terrain, the result is false.
	 * 			Otherwise, the result is true. 
	 */
	private boolean isSubmergedIn(Terrain terrain){ 
		HashSet<Tile> affectedTiles = getWorld().getTilesIn(getPosition().getDisplayedXPosition()+1,
				getPosition().getDisplayedYPosition(), getPosition().getDisplayedXPosition()+getWidth()-2,
				getPosition().getDisplayedYPosition()+getHeight());
		for (Tile tile: affectedTiles){
			if (tile.getGeoFeature() != terrain)
				return false;
		}
		return true;
	}

	/**
	 * A method to update the vertical velocity over a given time interval.
	 *
	 * @effect	The new vertical velocity is set to a new value based on the 
	 * 			time interval and the current attributes of this character.
	 * @effect	...
	 * 			| if(getPosition().getYPosition() <= 0)
	 * 			|	then setVertVelocity(0), setVertDirection(Direction.NULL),
	 * 			|		 setVertAcceleration(0)
	 * 			...
	 * 			| if ((isSubmergedIn(Terrain.WATER) && isMoving(Direction.DOWN) && !isDiving()) ||
	 *			|     (!isSubmergedIn(Terrain.WATER) && isRising()))
	 *			|	then setVertVelocity(0), setVertAcceleration(0), setVertDirection(Direction.NULL)
	 * 
	 */
	@Override
	protected void updateVertVelocity(double timeDuration){
		super.updateVertVelocity(timeDuration);
		if ((isSubmergedIn(Terrain.WATER) && isMoving(Direction.DOWN) && !isDiving())
				|| (!isSubmergedIn(Terrain.WATER) && isRising())){
			setVertVelocity(0);
			setVertAcceleration(0);
			setVertDirection(Direction.NULL);
		}
	}
	
	/**
	 * A method to check whether this shark can be hurt by a certain terrain type.
	 *
	 * @return	...
	 * 			| result == (terrain == Terrain.AIR || terrain == Terrain.MAGMA)
	 */
	@Override
	protected boolean canBeHurtBy(Terrain terrain) {
		return (terrain == Terrain.AIR || terrain == Terrain.MAGMA);
	}
	
	/**
	 * A method to update the hit points of this shark.
	 * A shark can damage other objects and can be damaged
	 * by other game objects.
	 * 
	 * @effect	...
	 * 			| if (!isDead() && !isOverlappingWith(Terrain.AIR) 
	 *			|	  && !isOverlappingWith(Terrain.MAGMA))
	 *			|	then getHpTimer().reset()
	 * @effect	...	
	 * 			| if(!isDead())
	 * 			|	if(isOverlappingWith(Terrain.AIR))
	 * 			|		then updateHitPointsTerrain(Terrain.AIR)
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
	 * 			|		then getImmuneTimer().reset()
	 * 			|		if(new.isDead())
	 * 			|			then getHpTimer().reset()
	 * @effect	...
	 * 			| if(new.isDead() && new.getHpTimer().getTimeSum()>= 0.6)
	 * 			|	then terminate()
	 */
	@Override
	protected void updateHitPoints(){
		Mazub alien = getWorld().getMazub();
		boolean isHurt = false;
		if (!isDead() && !isOverlappingWith(Terrain.AIR) 
				&& !isOverlappingWith(Terrain.MAGMA))
			getHpTimer().reset();
		if(!isDead()){
			if(isOverlappingWith(Terrain.AIR))
				updateHitPointsTerrain(Terrain.AIR);
			if(isOverlappingWith(Terrain.MAGMA))
				updateHitPointsTerrain(Terrain.MAGMA);
			if (isOverlappingWith(alien) && !alien.isImmune() && !isDead()){
				if (!alien.standsOn(this)){
					alien.getHurtBy(this);
				}
				if(!isImmune()){
					getHurtBy(alien);
					isHurt = true;
				}
			}
		}
		if(isHurt)
			getImmuneTimer().reset();
		if (isHurt && isDead()){
			getHpTimer().reset();
		}
		else if (isDead() && getHpTimer().getTimeSum()>= 0.6)
			terminate();
	}
	
	/**
	 * A method to take damage from another object.
	 * 
	 * @effect	...
	 * 			| if(!isImmune() && other instanceof Mazub)
	 * 			|	then subtractHp(50)
	 * 			| else if(!(other instanceof Shark))
	 * 			|	other.hurt(this)
	 */ 
	@Override
	protected void getHurtBy(GameObject other){
		if(!isImmune()){
			if(other instanceof Mazub){
				subtractHp(50);
			}
			else if(!(other instanceof Shark))
				other.hurt(this);
		}
	}
	
	/**
	 * A method to damage another game object.
	 * 
	 * @effect	...
	 * 			| if(!other.isDead() && other instanceof Mazub && !((Mazub) other).isImmune() &&
	 *			|    !((Mazub) other).standsOn(this))
	 *			|	then ((Mazub) other).getImmuneTimer().reset(), other.subtractHp(50)
	 *			|	if(other.isDead())
	 *			|		then other.getHpTimer().reset()
	 *			| else if(!(other instanceof Shark) || !other.isDead())
	 *			|	other.getHurtBy(this)
	 */
	@Override
	protected void hurt(GameObject other){
		if(!other.isDead() && other instanceof Mazub && !((Mazub) other).isImmune() &&
				!((Mazub) other).standsOn(this)){
			((Mazub) other).getImmuneTimer().reset();
			other.subtractHp(50);
			if (other.isDead())
				other.getHpTimer().reset();
		}
		else if(!(other instanceof Shark) || !other.isDead())
			other.getHurtBy(this);
		
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
	 * 			| result.contains("Shark at")
	 * @return	...
	 * 			| result.contains(getPosition().toString())
	 * @return	...
	 * 			| result.contains("with" + String.valueOf(getHitPoints()+
	 * 			|				  "hit points.") 
	 */
	@Override
	public String toString(){
		//return "Shark at " + getPosition().toString() + " with" +
		//		String.valueOf(getHitPoints())  + "hit points.";
		//return getVertVelocity() + "";
		//return String.valueOf(getProgram() != null);
		return "Shark";
	}
	
	/**
	 * Terminate this shark.
	 * 
	 * @pre		...
	 * 			| isDead()
	 * @pre		...
	 * 			| getHpTimer().getTimeSum()>0.6
	 * @effect	...
	 * 			| getWorld().removeAsShark(this)
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
