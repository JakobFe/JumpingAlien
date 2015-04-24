package jumpingalien.model;

import java.util.HashSet;
import java.util.Random;

import jumpingalien.model.exceptions.*;
import jumpingalien.util.Sprite;
import static jumpingalien.tests.util.TestUtils.doubleArray;

public class Shark extends Character {
	
	public Shark(int x, int y,Sprite[] sprites) 
			throws IllegalXPositionException,IllegalYPositionException{
		super(x,y,SHARK_INIT_VEL,SHARK_MAX_VEL,sprites,SHARK_HP);
	}
	
	private static final int SHARK_HP = 100;
	
	private void setRandomHorDirection() {
		Random rn = new Random();
		int startIndex = rn.nextInt(2);
		if(startIndex == 0)
			setHorDirection(Direction.LEFT);
		else
			setHorDirection(Direction.RIGHT);
	}
	
	private void setRandomVertDirection() {
		Random rn = new Random();
		int startIndex = rn.nextInt(2);
		if(startIndex == 1)
			setVertDirection(Direction.UP);
		else if (startIndex == 0)
			setVertDirection(Direction.DOWN);
	}
	
	private void setRandomVertAcceleration(){
		Random rn = new Random();
		setVertAcceleration(SHARK_DIVING_ACCEL + 
				((SHARK_RISING_ACCEL-SHARK_DIVING_ACCEL)*rn.nextDouble()));
	}
	
	private static final double SHARK_DIVING_ACCEL = -0.2;
	
	private static final double SHARK_RISING_ACCEL = 0.2;
	
	@Override
	public final double getInitHorVelocity(){
		return SHARK_INIT_VEL;
	}
	
	private static final double SHARK_INIT_VEL = 0;
	
	@Override
	public final double getMaxHorVelocity() {
		return SHARK_MAX_VEL; 
	}
	
	private static final double SHARK_MAX_VEL = 4;
	
	@Override
	public final double getMaxHorAcceleration() {
		return SHARK_MAX_HOR_ACCEL;
	}
	
	private static final double SHARK_MAX_HOR_ACCEL = 1.5;
	
	@Override
	public boolean isValidVertAcceleration(double acceleration){
		return ((acceleration >= SHARK_DIVING_ACCEL && 
				acceleration<= SHARK_RISING_ACCEL) ||
				acceleration == getMaxVertAcceleration());
	}
	
	@Override
	protected double getInitVertVelocity() {
		return INIT_VERT_VELOCITY;
	}
	
	private static final double INIT_VERT_VELOCITY = 2;
	
	@Override
	protected boolean canBeAddedTo(World world) {
		return super.canBeAddedTo(world) && (world == null || world.hasAsShark(this));
	}
	
	@Override
	protected boolean hasProperWorld() {
		return true;
	}
	
	public double getPeriodDuration() {
		return periodDuration;
	}
	
	private boolean isValidPeriod(double period){
		return (period == 0 || (period <=MAX_PERIOD && period >= MIN_PERIOD));
	}
	
	private static final double MIN_PERIOD = 1;
	
	private static final double MAX_PERIOD = 4;
	
	public void setPeriodDuration(double periodDuration) {
		assert isValidPeriod(periodDuration);
		this.periodDuration = periodDuration;
	}
	
	public double randomPeriodDuration(){
		Random rn = new Random();
		return MIN_PERIOD + (MAX_PERIOD - MIN_PERIOD) * rn.nextDouble();
	}
	
	private double periodDuration = 0;
	
	public int getPeriodCounter() {
		return periodCounter;
	}

	public void setPeriodCounter(int periodCounter) {
		this.periodCounter = periodCounter;
	}
	
	public void addPeriod(){
		setPeriodCounter(getPeriodCounter()+1);
	}

	private int periodCounter;
	
	public boolean canJump(){
		return ((getPeriodCounter() > MIN_NON_JUMPING_PERIOD)
				&& (standsOnTile() || isSubmergedIn(Terrain.WATER)));
	}
	
	private static final int MIN_NON_JUMPING_PERIOD = 4;
	
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
		if(!isMoving(Direction.UP))
			riseOrDive();
	}

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

	public boolean isDiving() {
		return isDiving;
	}

	public void setDiving(boolean isDiving) {
		this.isDiving = isDiving;
	}

	private boolean isDiving; 
	
	public boolean isRising() {
		return isRising;
	}

	public void setRising(boolean isRising) {
		this.isRising = isRising;
	}

	private boolean isRising;

	private void startJump() {
		//System.out.println("jump!");
		setPeriodCounter(0);
		setVertVelocity(INIT_VERT_VELOCITY);
		setVertAcceleration(getMaxVertAcceleration());
	}
	
	private void endMove(){
		setDiving(false);
		setRising(false);
		setHorVelocity(0);
		setHorAcceleration(0);
		if (isMoving(Direction.UP)){
			setVertVelocity(0);
			setVertDirection(Direction.NULL);
		}
		addPeriod();
	}	
	
	@Override
	protected void updateMovement(){
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
	
	@Override
	protected void updatePosition(double timeDuration) {
		double newXPos = getPosition().getXPosition() + getHorDirection().getFactor()*
				(getHorVelocity()*timeDuration+ 0.5*getHorAcceleration()*Math.pow(timeDuration, 2))*100;
		double newYPos = getPosition().getYPosition() + 
				((getVertDirection().getFactor()*getVertVelocity()*timeDuration)+ 
				0.5*getVertAcceleration()*Math.pow(timeDuration, 2))*100;
		if (newYPos<0)
			newYPos = 0;
		boolean enableFall = true;
		double[] newPos = updatePositionTileCollision(doubleArray(newXPos,newYPos));
		newPos = updatePositionObjectCollision(newPos);
		newXPos = newPos[0];
		newYPos = newPos[1];
		if(standsOnTile() || standsOnObject() || isSubmergedIn(Terrain.WATER))
			enableFall = false;
		if (enableFall && !isMoving(Direction.UP))
			startFall();
		getPosition().terminate();
		setPosition(new Position(newXPos,newYPos,getWorld()));
	}
	
	protected double[] updatePositionObjectCollision(double[] newPos){
		HashSet<GameObject> collection = new HashSet<GameObject>();
		collection.addAll(getWorld().getAllCharacters());
		return getPositionAfterCollision(newPos,collection);
	}

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
	
	@Override
	protected void getHurtBy(GameObject other){
		if(!isImmune()){
			if(other instanceof Mazub){
				setHitPoints(getHitPoints()-50);
			}
			else
				other.hurt(this);
		}
	}
	
	@Override
	protected void hurt(GameObject other){
		if(!other.isDead() && other instanceof Mazub && !((Mazub) other).isImmune() &&
				!((Mazub) other).standsOn(this)){
			((Mazub) other).getImmuneTimer().reset();
			((Mazub) other).setHitPoints(((Mazub) other).getHitPoints()-50);
			if (other.isDead())
				other.getHpTimer().reset();
		}
		else
			other.getHurtBy(this);
		
	}
	
	@Override
	public void updateSpriteIndex() {
		if(getHorDirection() == Direction.LEFT)
			setIndex(0);
		else
			setIndex(1);
	}
	
	@Override
	public String toString(){
		return "Shark at " + getPosition().getDisplayedXPosition() + "," +
							 getPosition().getDisplayedYPosition() + " with" +
							 getHitPoints() + "hit points.";
	}
	
	@Override
	protected void terminate(){
		assert getHitPoints() == 0;
		assert getHpTimer().getTimeSum() >= 0.6;
		super.terminate();
		getWorld().removeAsShark(this);
		getWorld().getAllGameObjects().remove(this);
		setWorld(null);
	}
	
}
