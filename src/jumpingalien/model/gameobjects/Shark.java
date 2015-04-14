package jumpingalien.model.gameobjects;

import java.util.HashSet;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Model;
import jumpingalien.model.exceptions.*;
import jumpingalien.model.other.*;
import jumpingalien.model.worldfeatures.*;
import jumpingalien.util.Sprite;
import static jumpingalien.tests.util.TestUtils.doubleArray;

public class Shark extends Character {

	public Shark(int x, int y, double initHorVelocity, double maxHorVelocity,
			Sprite[] sprites, int hitPoints) throws IllegalXPositionException,
			IllegalYPositionException{
		super(x,y,initHorVelocity,maxHorVelocity,sprites,hitPoints);
	}
	
	public Shark(int x, int y, Sprite[] sprites) {
		this(x,y,0,4,sprites,100);
	}
	
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
		setVertAcceleration(-0.2 + (0.4*rn.nextDouble()));
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
	public boolean isValidVertAcceleration(double acceleration){
		return ((acceleration >= -0.2 && acceleration<= 0.2) ||
				acceleration == getMaxVertAcceleration());
	}
	
	@Override
	protected double getInitVertVelocity() {
		return INIT_VERT_VELOCITY;
	}
	
	private static final double INIT_VERT_VELOCITY = 5;
	
	@Override
	protected boolean isValidWorld(World world) {
		return world.hasAsShark(this);
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
		return (getPeriodCounter() >= MIN_NON_JUMPING_PERIOD);
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
			else{
				riseOrDive();
			}
		}
		else
			riseOrDive();
	}

	private void riseOrDive() {
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
		System.out.println("jump!");
		setPeriodCounter(-1);
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
	public void advanceTime(double timeDuration) throws IllegalXPositionException,
	IllegalYPositionException,IllegalTimeIntervalException{
		if (!isValidTimeInterval(timeDuration))
			throw new IllegalTimeIntervalException(this);
		if(getPeriodDuration() == 0){
			setTimeSum(0);
			setRandomHorDirection();
			updateSpriteIndex();
			startMove();
		}
		else if (getTimeSum() >= getPeriodDuration()){
			endMove();
			setPeriodDuration(0);
		}
		double tdHor = 0.01/(Math.abs(getHorVelocity())
				+Math.abs(getHorAcceleration())*timeDuration);
		double tdVert = 0.01/(Math.abs(getVertVelocity())
				+Math.abs(getVertAcceleration())*timeDuration);
		double td = Math.min(tdHor, tdVert);
		if (td > timeDuration)
			td = timeDuration;
		for (int index = 0; index < timeDuration/td; index++){
			try {
				updatePosition(td);
				updateHorVelocity(td);
				updateVertVelocity(td);
			} catch (NullPointerException e) {
			}
		}
		updateHitPoints();
		counter(timeDuration);
		
		
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
		double[] newPos = updatePositionTileCollision(doubleArray(newXPos,newYPos));
		newPos = updatePositionObjectCollision(newPos);
		newXPos = newPos[0];
		newYPos = newPos[1];
		getPosition().terminate();
		setPosition(new Position(newXPos,newYPos,getWorld()));
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
	
	@Model @Override
	protected void updateHorVelocity(double timeDuration){
		double newVel = getHorVelocity() + getHorAcceleration() * timeDuration;
		if (newVel > getMaxHorVelocity()){
			setHorVelocity(getMaxHorVelocity());
			setHorAcceleration(0);
		}
		else
			setHorVelocity(newVel);
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
		if (isOverlappingWith(alien)){
			setHitPoints(getHitPoints()-50);
			if (!alien.isImmune() && !alien.standsOn(this)){
				alien.setImmuneTimer(0);
				//alien.setHitPoints(getHitPoints()-50);
			}
		}	
	}

	@Override
	public void updateSpriteIndex() {
		if(getHorDirection() == Direction.LEFT)
			setIndex(0);
		else
			setIndex(1);
	}
	
}
