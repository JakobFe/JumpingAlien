package jumpingalien.model;

public class Timer {
	
	/**
	 * Initialize this timer with a given time sum.
	 * 
	 * @param	timeSum
	 * 			The initial time sum for this timer.
	 * @effect	The time sum of this timer is set to the given time sum.
	 * 			| this.setTimeSum(timeSum)
	 */
	public Timer(double timeSum){
		setTimeSum(timeSum);
	}
	
	/**
	 * Initialize this timer with a time sum zero.
	 * 
	 * @effect	Initialize this timer with a time sum zero.
	 * 			| this(0)
	 */
	public Timer(){
		this(0);
	}
	
	/**
	 * Return the current time sum belonging to this timer.
	 */
	public double getTimeSum() {
		return timeSum;
	}
	
	/**
	 * Sets the time sum of this timer to a given sum.
	 * 
	 * @param 	timeSum
	 * 			The timeSum to set.
	 * @post	The new time sum is equal to the given timeSum.
	 * 			| new.getTimeSum() = timeSum 
	 */
	public void setTimeSum(double timeSum) {
		this.timeSum = timeSum;
	}
	
	/**
	 * Reset the current timer.
	 * 
	 * @post	The time sum belonging to this timer is set to zero.
	 * 			| new.getTimeSum() == 0
	 */
	public void reset(){
		setTimeSum(0);
	}
	
	/**
	 * Check whether the given time duration is a valid time duration to add to 
	 * the time sum of this timer.
	 * 
	 * @param 	timeDuration
	 * 			The time duration to check.
	 * @return	True if the given time duration is strictly positive.
	 * 			result == timeDuration > 0
	 */
	private boolean isValidTimeDuration(double timeDuration){
		return (timeDuration > 0);
	}
	
	/**
	 * A method to increment the time sum of this timer with the given time duration.
	 * 
	 * @param 	timeDuration
	 * 			The time duration to add to time sum.
	 * @pre		The given time duration must be valid.
	 * 			| isValidTimeDuration(timeDuration)
	 * @post	The new time sum is incremented with the given time duration.
	 * 			| new.getTimeSum() == this.getTimeSum() + timeDuration
	 */
	public void increment(double timeDuration){
		assert isValidTimeDuration(timeDuration);
		setTimeSum(getTimeSum()+timeDuration);
	}
	
	/**
	 * A method to decrement the time sum of this timer with the given time duration.
	 * 
	 * @param 	timeDuration
	 * 			The time duration to subtract from the time sum.
	 * @pre		The given time duration must be valid.
	 * 			| isValidTimeDuration(timeDuration)
	 * @post	The new time sum is decremented with the given time duration.
	 * 			| new.getTimeSum() == this.getTimeSum() - timeDuration
	 */
	public void decrement(double timeDuration){
		assert isValidTimeDuration(timeDuration);
		setTimeSum(getTimeSum()-timeDuration);
	}
	
	/**
	 * A variable storing the time sum belonging to this timer.
	 */
	private double timeSum;
	
	
}
