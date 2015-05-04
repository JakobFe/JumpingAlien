package jumpingalien.model;

public interface Uncontrollable {

	/**
	 * A method to set the horizontal direction of this uncontrollable object randomly
	 * to the left or to the right.
	 * 
	 * @post	...
	 * 			| new.getHorDirection() == Direction.LEFT ||
	 * 			| new.getHorDirection() == Direction.RIGHT
	 */
	void setRandomHorDirection();

	/**
	 * A method to get the duration of the current period.
	 */
	double getPeriodDuration();

	/**
	 * A method to check whether a given period duration is valid.
	 * 
	 * @param 	period
	 * 			The period duration to check.
	 * @return	...
	 * 			| result == (period == 0 || (period <=MAX_PERIOD && period >= MIN_PERIOD)) 
	 */
	boolean isValidPeriod(double period);

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
	void setPeriodDuration(double periodDuration);

	/**
	 * A method to return a random period duration between the minimum period
	 * duration for uncontrollable objects and the maximum period duration for uncontrollable objects.
	 * 
	 * @return	...
	 * 			| result <= MAX_PERIOD && result >= MIN_PERIOD
	 */
	double randomPeriodDuration();

}