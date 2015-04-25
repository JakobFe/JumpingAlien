package jumpingalien.model;

import static jumpingalien.tests.util.TestUtils.doubleArray;

import java.util.HashSet;
import java.util.Random;

import jumpingalien.model.exceptions.IllegalTimeIntervalException;
import jumpingalien.model.exceptions.IllegalXPositionException;
import jumpingalien.model.exceptions.IllegalYPositionException;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;

/**
 * A class concerning slimes as a subclass of characters.
 * 
 * @invar	
 * 			| hasProperSchool()
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version 1.0
 */
public class Slime extends Character{
	
	public Slime(int x, int y, Sprite[] sprites, School school){
		super(new Position(x,y),0,SLIME_VELOCITY,sprites);
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
	protected boolean canBeAddedTo(World world) {
		return super.canBeAddedTo(world) && (world == null || world.hasAsSlime(this));
	}
	
	@Override
	protected boolean hasProperWorld() {
		return true;
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
	
	boolean isValidSchool(School school){
		return school == null || school.hasAsSlime(this);
	}
	
	boolean hasProperSchool(){
		return isValidSchool(getSchool());
	}
	
	public void setSchool(School school) {
		assert isValidSchool(school);
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
		super.updateMovement();
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
	protected boolean canHaveAsVertAcceleration(double vertAcceleration) {
		return vertAcceleration == getMaxVertAcceleration();
	}
	
	@Override
	protected boolean canBeHurtBy(Terrain terrain) {
		return (terrain == Terrain.WATER || terrain == Terrain.MAGMA);
	}
	
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
			if (alien != null && !alien.isImmune() && isOverlappingWith(alien)){
				if (!alien.standsOn(this)){
					alien.getHurtBy(this);
				}
				if(!isImmune()){
					getHurtBy(alien);
					isHurt = true;
				}
			}
			for (Shark shark: getWorld().getAllUnterminatedSharks()){
				if(isOverlappingWith(shark)){
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
			updateHpSchool();
		if(isHurt)
			getImmuneTimer().reset();
		if (isHurt && getHitPoints() == 0)
			getHpTimer().reset();
		else if (getHitPoints() == 0 && getHpTimer().getTimeSum()>= 0.6){
			terminate();
		}
	}
	
	@Override
	protected void getHurtBy(GameObject other){
		if(!isImmune()){
			if(other instanceof Mazub)
				setHitPoints(getHitPoints()-50);
			else if(other instanceof Shark)
				setHitPoints(getHitPoints()-50);
			else
				other.hurt(this);
		}
	}
	
	protected void hurt(GameObject other){
		if(!isDead()){
			if(other instanceof Mazub && !((Mazub) other).isImmune() &&
					!((Mazub) other).standsOn(this)){
				((Mazub) other).getImmuneTimer().reset();
				((Mazub) other).setHitPoints(((Mazub) other).getHitPoints()-50);
				if (other.isDead())
					other.getHpTimer().reset();
			}	
			else if(other instanceof Shark && !((Shark) other).isImmune()){
				other.setHitPoints(other.getHitPoints()-50);
				((Shark)other).getImmuneTimer().reset();
				if (other.isDead())
					other.getHpTimer().reset();
			}
			else
				other.getHurtBy(this);
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
		//return getPosition().toString();
	}
	
	
	@Override
	protected void terminate(){
		assert getHitPoints() == 0;
		assert getHpTimer().getTimeSum() >= 0.6;
		super.terminate();
		getWorld().removeAsSlime(this);
		setWorld(null);
	}
}
