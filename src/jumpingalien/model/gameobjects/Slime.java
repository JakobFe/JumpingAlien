package jumpingalien.model.gameobjects;

import java.util.Random;

import jumpingalien.model.exceptions.IllegalTimeIntervalException;
import jumpingalien.model.exceptions.IllegalXPositionException;
import jumpingalien.model.exceptions.IllegalYPositionException;
import jumpingalien.model.other.Direction;
import jumpingalien.model.worldfeatures.World;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;

public class Slime extends Character{
	
	public Slime(int x, int y, Sprite[] sprites, School school){
		super(x,y,0,SLIME_VELOCITY,sprites);
		setRandomDirection();
	}
	
	private void setRandomDirection() {
		Random rn = new Random();
		int startIndex = rn.nextInt(2);
		if(startIndex == 0)
			setHorDirection(Direction.LEFT);
		else
			setHorDirection(Direction.RIGHT);
	}
	
	@Override
	protected boolean isValidWorld(World world) {
		return (world == null || world.hasAsSlime(this));
	}
	
	/**
	 * Return the horizontal velocity of this slime.
	 */
	@Override
	public final double getHorVelocity(){
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

	
	@Override
	public void advanceTime(double timeDuration) throws IllegalXPositionException,
	IllegalYPositionException,IllegalTimeIntervalException{}
	
	@Override
	protected void updatePosition(double timeDuration) {}

	@Override
	public void updateSpriteIndex() {}
	
}
