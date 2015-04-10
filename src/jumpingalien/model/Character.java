package jumpingalien.model;

import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

public abstract class Character extends GameObject{
	@Raw
	protected Character(int x, int y, double initHorVelocity, double maxHorVelocity, Sprite[] sprites, int hitPoints) 
			throws IllegalXPositionException,IllegalYPositionException{
		super(x,y,initHorVelocity,maxHorVelocity,sprites,hitPoints);
	}
	
	@Raw
	protected Character(int x, int y, double initHorVelocity, double maxHorVelocity, Sprite[] sprites) 
			throws IllegalXPositionException,IllegalYPositionException{
		this(x,y,initHorVelocity,maxHorVelocity,sprites,100);
	}
	
	/**
	 * Returns the current vertical direction of this Mazub.
	 */
	@Basic
	public Direction getVertDirection() {
		return vertDirection;
	}
	
	/**
	 * Set the vertical direction of this Mazub to the given direction.
	 * 
	 * @param	vertDirection
	 * 			Vertical direction to set.
	 * @pre		The given direction must be a valid direction.
	 * 			| isValidDirection(vertDirection)
	 * @post	The new vertical direction of this Mazub is set to the given direction.
	 * 			| new.getVertDirection() == vertDirection
	 */
	@Model
	protected void setVertDirection(Direction vertDirection) {
		this.vertDirection = vertDirection;
	}
	
	/**
	 * An variable storing the vertical direction.
	 */
	private Direction vertDirection = Direction.NULL;

	/**
	 * Checks whether this Mazub can have the given horizontal velocity as
	 * its horizontal velocity.
	 * 
	 * @param	horVelocity
	 * 			The horizontal velocity to check.
	 * @return	True if the given horizontal velocity is above or equal to
	 * 			the initial horizontal velocity of this Mazub and below or 
	 * 			equal to the maximum horizontal velocity of this Mazub, or
	 * 			if the given horizontal velocity is 0.
	 * 			| result == (horVelocity == 0) ||
	 * 			|			((horVelocity >= getInitHorVelocity()) &&
	 * 			|			(horVelocity <= getMaxHorVelocity())) 
	 */
	@Model@Override
	protected boolean canHaveAsHorVelocity(double horVelocity){
		return  (horVelocity == 0) ||
				((horVelocity >= this.getInitHorVelocity()) &&
				(horVelocity <= getMaxHorVelocityRunning()));
	}

	/**
	 * Return the maximum horizontal acceleration of the Mazub.
	 */
	@Basic @Model
	protected double getMaxHorAcceleration(){
		return maxHorAcceleration;
	}

	private double maxHorAcceleration;
	
	/**
	 * Return the current horizontal acceleration of this Mazub.
	 */
	@Basic
	public double getHorAcceleration() {
		return horAcceleration;
	}
	
	/**
	 * Checks whether the given horizontal acceleration is valid.
	 * 
	 * @param 	horAcceleration
	 * 			Horizontal acceleration to check.
	 * @return	True if and only if the given value lies between
	 * 			zero and the maximum horizontal acceleration.
	 * 			| result == 
	 * 			|	(horAcceleration >= 0) && (horAcceleration <= getMaxHorAcceleration())
	 */
	@Model
	protected boolean isValidHorAcceleration(double horAcceleration){
		return ((horAcceleration >= 0) && (horAcceleration <= getMaxHorAcceleration()));
	}

	/**
	 * Sets the horizontal acceleration to the given value.
	 * 
	 * @param 	horAcceleration 
	 * 			The horAcceleration to set.
	 * @post	If the given value is valid, the horizontal acceleration is set to the given
	 * 			value.
	 * 			| if (isValidHorAcceleration(horAcceleration))
	 * 			|	then new.getHorAcceleration() = horAcceleration
	 */
	@Model
	protected void setHorAcceleration(double horAcceleration) {
		if (isValidHorAcceleration(horAcceleration))
			this.horAcceleration = horAcceleration;			
	}

	/**
	 * A variable storing the current horizontal acceleration.
	 * This variable will always store a positive number of type double, 
	 * or it will store zero.
	 */
	private double horAcceleration = 0;

	/**
	 * Return the initial vertical velocity of this Mazub.
	 */
	@Basic @Model
	protected double getInitVertVelocity() {
		return initVertVelocity;
	}
	
	/**
	 * A variable storing the initial vertical velocity. 
	 * This variable will always have 8 m/s as its value.
	 */
	private double initVertVelocity;

	/**
	 * Return the current vertical velocity of this Mazub.
	 */
	@Basic
	public double getVertVelocity() {
		return vertVelocity;
	}
	
	/**
	 * A method to check whether the given vertical velocity is valid.
	 * 
	 * @param 	vertVelocity
	 * 			The vertical velocity to check.
	 * @return	Returns true if and only if the given value is positive or zero.
	 * 			| result == (vertVelocity >= 0)
	 */
	@Model
	protected static boolean isValidVertVelocity(double vertVelocity){
		return (vertVelocity >= 0); 
	}

	/**
	 * Set the vertical velocity to the given value.
	 * 
	 * @param 	vertVelocity 
	 * 			The vertVelocity to set
	 * @post	If the given vertical is valid, the vertical velocity
	 * 			is set to the given value.
	 * 			| if (isValidVertVelocity(vertVelocity))
				| 	then new.vertVelocity = vertVelocity
	 */
	@Model
	protected void setVertVelocity(double vertVelocity) {
		if (isValidVertVelocity(vertVelocity))
			this.vertVelocity = vertVelocity;
	}
	
	/**
	 * A variable storing the current vertical velocity.
	 *  This value will always be a positive number of type double.
	 */
	private double vertVelocity = 0;
	
	/**
	 * Return the current vertical acceleration of this Mazub.
	 */
	@Basic
	public double getVertAcceleration() {
		return vertAcceleration;
	}
	
	/**
	 * A method to check whether the given value is a 
	 * valid vertical acceleration.
	 * 
	 * @param 	vertAcceleration
	 * 			Vertical acceleration to check.
	 * @return	True if and only is the given value equals zero
	 * 			or the maximum vertical acceleration.
	 * 			| result = (vertAcceleration == 0 || 
	 * 			|			vertAcceleration == getMaxVertAcceleration());
	 */
	@Model
	protected static boolean isValidVertAcceleration(double vertAcceleration){
		return (vertAcceleration == 0 || vertAcceleration == getMaxVertAcceleration());
	}
	
	/**
	 * Sets the vertical acceleration to the given value.
	 * 
	 * @param 	vertAcceleration 
	 * 			The vertical acceleration to set.
	 * @post	If the given value is a valid vertical acceleration,
	 * 			the vertical acceleration is set to the given value.
	 * 			| if (isValidVertAcceleration(vertAcceleration))
				| 	then new.vertAcceleration = vertAcceleration
	 */
	@Model
	protected void setVertAcceleration(double vertAcceleration) {
		if (isValidVertAcceleration(vertAcceleration))
			this.vertAcceleration = vertAcceleration;
	}
	
	/**
	 * A variable storing the current vertical acceleration.
	 * This variable will always be a negative number of type double, or zero.
	 */
	private double vertAcceleration = 0;
	
	/**
	 * Return the maximum vertical acceleration.
	 */
	@Basic @Model
	protected static double getMaxVertAcceleration() {
		return MAX_VERT_ACCELERATION;
	}
	
	/**
	 * A variable storing the maximum vertical acceleration,
	 * the gravitational constant. This variable will always have
	 * (-10 m/s^2) as its value.
	 */
	private static final double MAX_VERT_ACCELERATION = -10;

	protected void startFall(){
		setVertDirection(Direction.DOWN);
		setVertAcceleration(getMaxVertAcceleration());
	}
	
	/**
	 * A method to update the vertical velocity over a given time interval.
	 * 
	 * @param 	timeDuration
	 * 			The time interval needed to calculate the new vertical velocity.
	 * @effect	The new vertical velocity is set to a new value based on the 
	 * 			time interval and the current attributes of this Mazub.
	 */
	protected void updateVertVelocity(double timeDuration){
		double newVel = getVertDirection().getFactor()*getVertVelocity() + getVertAcceleration() * timeDuration;
		if (newVel<0){
			newVel = -newVel;
			setVertDirection(Direction.DOWN);
		}
		if (getPosition().getYPosition() <= 0){
			getPosition().setYPosition(0);
			setVertVelocity(0);
			setVertDirection(Direction.NULL);
			setVertAcceleration(0);
		}
		setVertVelocity(newVel);
	}

}
