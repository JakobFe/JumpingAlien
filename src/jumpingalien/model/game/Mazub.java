package jumpingalien.model.game;


import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.exceptions.*;

/**
 * A class that implements the player character with the ability to jump, duck and
 * run to the left and to the right. This class is a subclass of Character.
 * 			
 * @author	Jakob Festraets, Vincent Kemps
 * 			| Course of studies: 2nd Bachelor of engineering
 * 			| Code repository: https://github.com/JakobFe/JumpingAlien
 * @version	2.0
 *
 */
public class Mazub extends Alien{
	
	/**
	 * Initialize this new Mazub with given position, 
	 * given initial horizontal velocity, given maximum horizontal velocity,
	 * given sprites and 100 hit points.
	 * 
	 * 
	 * @param 	position
	 * 			The position for this new Mazub.
	 * @param 	initHorVelocity
	 * 			Initial horizontal velocity for this Mazub.
	 * @param 	maxHorVelocity
	 * 			Maximum horizontal velocity while running for this Mazub.
	 * @param	sprites
	 * 			An array containing the different sprites for this Mazub.
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @pre		The maximum horizontal velocity must be valid.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity,initHorVelocity)
	 * @pre		The sprites must be an array with a valid number of sprites.
	 * 			| isValidArrayOfSprites(sprites)
	 * @effect	This Mazub is initialized as a new character with given position,
	 * 			given initial horizontal velocity, given maximum
	 * 			horizontal velocity, given sprites and 100 hit points.
	 * 			| super(position,initHorVelocity,maxHorVelocity,sprites)
	 */
	@Raw
	public Mazub(Position position, double initHorVelocity, double maxHorVelocity, Sprite[] sprites) 
			throws IllegalArgumentException{
		super(position,initHorVelocity,maxHorVelocity,sprites);
	}
	
	/**
	 * Initialize this new Mazub with given position, 
	 * 1 [m/s] as its initial horizontal velocity, 3 [m/s] as its maximum 
	 * horizontal velocity and with the given sprites.
	 * 
	 * @param 	position
	 * 		  	Initial position for this Mazub.
	 * @param	sprites
	 * 			An array containing the different sprites for this Mazub.
	 * @pre		The sprites must be an array with a valid number of sprites.
	 * 			| isValidArrayOfSprites(sprites)
	 * @effect	This Mazub is initialized with given position,
	 * 			given sprites, 1 as its initial horizontal 
	 * 			velocity and 3 as its maximum horizontal velocity.
	 * 			| this(x,y,1,3,sprites)
	 */
	@Raw
	public Mazub(Position position, Sprite[] sprites) 
			throws IllegalXPositionException, IllegalYPositionException{
		this(position,1,3,sprites);
	}
	
	/**
	 * A method to update the hit points of this Mazub.
	 * A game object can damage other objects and can be damaged by other game objects.
	 * 
	 * @effect	If this Mazub is not dead and it is not overlapping with "Water"
	 * 			or "Magma", the hit points timer is reset.
	 * 			| if (!isDead() && !isOverlappingWith(Terrain.WATER) && !isOverlappingWith(Terrain.MAGMA))
	 * 			|	then getHpTimer().reset()
	 * @effect	If this Mazub is not dead and it is overlapping with "Water", the hit points
	 * 			are updated in result of being in contact with "Water".
	 * 			| if(!isDead() && if(isOverlappingWith(Terrain.WATER)))
	 * 			|	then updateHitPointsTerrain(Terrain.WATER)
	 * @effect	If this Mazub is not dead and it is overlapping with "Magma", the hit points
	 * 			are updated in result of being in contact with "Magma".
	 * 			| if(!isDead() && if(isOverlappingWith(Terrain.MAGMA)))
	 * 			|	then updateHitPointsTerrain(Terrain.MAGMA)
	 * @effect	If this Mazub is dead and the time sum of the hit points timer
	 * 			is greater than 0.6 seconds, this Mazub is terminated.
	 * 			| if(isDead() && getHpTimer().getTimeSum()>0.6)
	 * 			|	 terminate()
	 */
	@Override
	protected void updateHitPoints(){
		if(!isDead()){
			if (!isOverlappingWith(Terrain.WATER) && !isOverlappingWith(Terrain.MAGMA))
				getHpTimer().reset();
			if(isOverlappingWith(Terrain.WATER))
				updateHitPointsTerrain(Terrain.WATER);
			if(isOverlappingWith(Terrain.MAGMA))
				updateHitPointsTerrain(Terrain.MAGMA);
		}
		if(isDead() && getHpTimer().getTimeSum()>0.6)
			terminate();
	}
	
	/**
	 * A method to get damage by another game object.
	 * 
	 * @effect	If this Mazub is not immune and the other game object is not a Mazub
	 * 			and is effective, than the other game object will hurt this Mazub.
	 * 			| if(!isImmune() && other != null  && !(other instanceof Mazub))
	 * 			|	then other.hurt(this)
	 */
	@Override
	protected void getHurtBy(GameObject other){
		if(!isImmune() && other != null && !(other instanceof Mazub))
			other.hurt(this);
	}
	
	/**
	 * A method to damage another game object.
	 * 
	 * @effect	If the other game object is effective and not a Mazub, 
	 * 			than the other game object will hurt this Mazub.
	 * 			| if(other != null && !(other instanceof Mazub))
	 * 			|	then other.getHurtBy(this)
	 */
	protected void hurt(GameObject other){
		if(other != null && !(other instanceof Mazub))
			other.getHurtBy(this);
	}
	
	/**
	 * Check whether this game object can be added to the given world.
	 * 
	 * @return 	True if this function in GameObject returns true and if 
	 * 			the world is null or this mazub is the Mazub of the world.
	 * 			| result == (super.canBeAddedTo(world) && (world == null || world.getMazub() == this))
	 */
	public boolean canBeAddedTo(World world){
		return super.canBeAddedTo(world) && (world == null || world.getMazub() == this);
	}
	
	/**
	 * Check whether this game object has a proper world.
	 * 
	 * @return	True if the world is not effective or if the world has 
	 * 			this Mazub as its Mazub.
	 * 			| result == (getWorld() == null || getWorld().getMazub() == this)
	 */
	@Override
	protected boolean hasProperWorld() {
		return (getWorld() == null || getWorld().getMazub() == this);
	}	
	
	/**
	 * Terminate this Mazub.
	 * 
	 * @pre		The game object may not have hit points anymore.
	 * 			| getHitPoints()==0
	 * @pre		The time sum belonging to the hit point timer of this
	 * 			game object must be greater than 0.6 seconds.
	 * 			| getHpTimer().getTimeSum()>0.6
	 * @effect	The world no longer refers to this Mazub.
	 * 			| getWorld().setMazub(null)
	 * @effect	This Mazub no longer refers a world.
	 * 			| setWorld(null) 
	 */ 
	@Override
	public void terminate(){
		assert (getHitPoints()==0);
		super.terminate();
		if(getWorld() != null){
			getWorld().setMazub(null);
			setWorld(null);
		}
	}
	
	/**
	 * Return a textual representation for this Mazub.
	 * 
	 * @return	The representation has a reference to the position and starts with
	 * 			"Mazub at position ".
	 * 			| result.contains("Mazub at position ")
	 * 			| result.contains(getPosition().toString())
	 */
	@Override
	public String toString(){
		//return "Mazub at position " + getPosition().toString();
		return "Mazub";
	}

	/**
	 * Returns whether Mazub can have a program or not.
	 * 
	 * @return	Mazub cannot have a program.
	 * 			| result == false
	 */
	@Override
	protected boolean canHaveProgram() {
		return false;
	}
}
