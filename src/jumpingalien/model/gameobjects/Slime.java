package jumpingalien.model.gameobjects;

import static jumpingalien.tests.util.TestUtils.doubleArray;

import java.util.HashSet;
import java.util.Random;

import jumpingalien.model.exceptions.IllegalTimeIntervalException;
import jumpingalien.model.exceptions.IllegalXPositionException;
import jumpingalien.model.exceptions.IllegalYPositionException;
import jumpingalien.model.other.Direction;
import jumpingalien.model.other.Position;
import jumpingalien.model.worldfeatures.Terrain;
import jumpingalien.model.worldfeatures.World;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;

public class Slime extends Character{
	
	public Slime(int x, int y, Sprite[] sprites, School school){
		super(x,y,0,SLIME_VELOCITY,sprites);
		setRandomDirection();
		school.addSlime(this);
	}
	
	private void setRandomDirection() {
		Random rn = new Random();
		int startIndex = rn.nextInt(2);
		if(startIndex == 0)
			setHorDirection(Direction.LEFT);
		else
			setHorDirection(Direction.RIGHT);
	}
	
	public double getPeriodDuration() {
		return periodDuration;
	}
	
	private boolean isValidPeriod(double period){
		return (period == 0 || (period <=MAX_PERIOD && period >= MIN_PERIOD));
	}
	
	private static final double MIN_PERIOD = 2;
	
	private static final double MAX_PERIOD = 6;
	
	public void setPeriodDuration(double periodDuration) {
		assert isValidPeriod(periodDuration);
		this.periodDuration = periodDuration;
	}
	
	public double randomPeriodDuration(){
		Random rn = new Random();
		return MIN_PERIOD + (MAX_PERIOD - MIN_PERIOD) * rn.nextDouble();
	}
	
	private double periodDuration = 0;
	
	@Override
	protected boolean isValidWorld(World world) {
		return (world == null || world.hasAsSlime(this));
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
	 * Its value is always 2.5 m/s.
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



	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		assert school == null || school.hasAsSlime(this);
		this.school = school;
	}

	private School school;
	
	private void startMove(){
		setHorVelocity(getInitHorVelocity());
		setHorAcceleration(getMaxHorAcceleration());
		setPeriodDuration(randomPeriodDuration());
	}
	
	private void endMove(){
		setHorVelocity(0);
		setHorAcceleration(0);
	}
	
	@Override
	public void advanceTime(double timeDuration)throws IllegalXPositionException,
	IllegalYPositionException,IllegalTimeIntervalException{
		super.advanceTime(timeDuration);
		updateSchool();
	}
	
	@Override
	protected void updateMovement(){
		if(getPeriodDuration() == 0){
			getSpritesTimer().setTimeSum(0);
			setRandomDirection();
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
		if(standsOnTile() || standsOnObject())
			enableFall = false;
		
		if (enableFall && !isMoving(Direction.UP)){
			startFall();
		}
		getPosition().terminate();
		setPosition(new Position(newXPos,newYPos,getWorld()));
	}
	
	protected double[] updatePositionObjectCollision(double[] newPos){
		HashSet<GameObject> collection = new HashSet<GameObject>();
		collection.addAll(getWorld().getAllCharacters());
		return getPositionAfterCollision(newPos,collection);
	}

	@Override
	protected boolean isValidVertAcceleration(double vertAcceleration) {
		return vertAcceleration == getMaxVertAcceleration();
	}
	
	@Override
	protected void updateHitPoints(){
		Mazub alien = getWorld().getMazub();
		boolean isHurt = false;
		boolean isDamaged = false;
		if (getHitPoints() != 0 && !isOverlappingWith(Terrain.WATER) && !isOverlappingWith(Terrain.MAGMA))
			getHpTimer().reset();
		
		if (getHitPoints() != 0 && alien != null && !alien.isImmune() && isOverlappingWith(alien)){
			setHitPoints(getHitPoints()-50);
			isHurt = true;
			//System.out.println(alien.standsOn(this));
			if (!alien.standsOn(this)){
				alien.getImmuneTimer().reset();
				alien.setHitPoints(alien.getHitPoints()-50);
				assert alien.isImmune();
			}
		}
		
		if (getHitPoints() != 0){
			for (Shark shark: getWorld().getAllUnterminatedSharks()){
				if(isOverlappingWith(shark)){
					setHitPoints(getHitPoints()-50);
					isHurt = true;
					shark.setHitPoints(shark.getHitPoints()-50);
				}
			}
		}
		if (getHitPoints() != 0 && isOverlappingWith(Terrain.WATER)){
			if (getHpTimer().getTimeSum() > 0.2){
				isDamaged = true;
				setHitPoints(getHitPoints()-2);
				getHpTimer().setTimeSum(getHpTimer().getTimeSum()-0.2);
			}
		}
		if (getHitPoints() != 0 && isOverlappingWith(Terrain.MAGMA)){
			if (getHpTimer().getTimeSum() == 0){
				setHitPoints(getHitPoints()-50);
				isDamaged = true;
			}
			else if (getHpTimer().getTimeSum() > 0.2){
				setHitPoints(getHitPoints()-50);
				getHpTimer().setTimeSum(getHpTimer().getTimeSum()-0.2);
				isDamaged = true;
			}
		}
		
		if(isHurt || isDamaged)
			updateHpSchool();
		if (isHurt && getHitPoints() == 0)
			getHpTimer().reset();
		else if (getHitPoints() == 0 && getHpTimer().getTimeSum()>= 0.6){
			terminate();
		}
	}
	
	void updateHpSchool(){
		getSchool().reduceHpAll(this);
	}
	
	void updateSchool(){
		if (getWorld() != null && getWorld().getAllUnterminatedSlimes()!= null){
			for (Slime other: getWorld().getAllUnterminatedSlimes()){
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
	 * Sets the school of this slime to the school of the other slime and ...
	 * @param 	other 
	 */
	void changeSchool(Slime other){
		getSchool().addHpAll(this);
		setHitPoints(getHitPoints()-getSchool().getNbSlimes()+1);
		setHitPoints(getHitPoints()+other.getSchool().getNbSlimes());
		this.getSchool().removeSlime(this);
		other.getSchool().addSlime(this);
		//this.setSchool(other.getSchool());
		other.getSchool().reduceHpAll(this);
		
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
		return getHitPoints() + getSchool().toString();
		/*return "Slime at " + getPosition().getDisplayedXPosition() + "," +
							 getPosition().getDisplayedYPosition() + " with" +
							 getHitPoints() + "hit points.";*/
	}
	
	@Override
	protected void terminate(){
		assert getHitPoints() == 0;
		assert getHpTimer().getTimeSum() >= 0.6;
		super.terminate();
		getWorld().removeAsSlime(this);
		getWorld().getAllGameObjects().remove(this);
		setWorld(null);
	}
}
