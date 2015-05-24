package jumpingalien.model.game;

import java.util.Set;

import jumpingalien.model.program.programs.Program;
import jumpingalien.util.Sprite;

public class Buzam extends Alien{

	/**
	 * Initialize this new Buzam with given position and with the given sprites.
	 * 
	 * @param 	position
	 * 		  	Initial position for this Buzam.
	 * @param	sprites
	 * 			An array containing the different sprites for this Buzam.
	 * @pre		The sprites must be an array with a valid number of sprites.
	 * 			| isValidArrayOfSprites(sprites)
	 * @effect	This Buzam is initialized as an Alien with given position, given sprites
	 * 			and with no program.
	 * 			| this(position,sprites,null)
	 * @effect	The hit points of this Buzam are set to 500.
	 * 			| new.getHitPoint() == 500;
	 */
	public Buzam(Position position, Sprite[] sprites) 
			throws IllegalArgumentException{
		super(position,sprites,null);
		setHitPoints(500);
	}

	/**
	 * Initialize this new Buzam with given position and with the given sprites.
	 * 
	 * @param 	position
	 * 		  	Initial position for this Buzam.
	 * @param	sprites
	 * 			An array containing the different sprites for this Buzam.
	 * @pre		The sprites must be an array with a valid number of sprites.
	 * 			| isValidArrayOfSprites(sprites)
	 * @effect	This Buzam is initialized as an Alien with given position, given sprites
	 * 			and with the given program.
	 * 			| this(position,sprites,program)
	 * @effect	The hit points of this Buzam are set to 500.
	 * 			| new.getHitPoint() == 500;
	 */
	public Buzam(Position position, Sprite[] sprites, Program program) 
			throws IllegalArgumentException{
		super(position,sprites,program);
		setHitPoints(500);
	}
	
	/**
	 * Method to end the jumping movement of the Buzam.
	 * 
	 * @effect	The method for ending the jumping movement of Alien is invoked.
	 * 			| super.endJump() 
	 */
	@Override
	public void endJump(){
		try{super.endJump();}
		catch(IllegalStateException e){}
	}
	
	/**
	 * Check whether this game object can be added to the given world.
	 * 
	 * @return 	True if this function in GameObject returns true and if 
	 * 			the world is null or this buzam is a Buzam of the world.
	 * 			| result == (super.canBeAddedTo(world) && (world == null || world.hasAsGameObject(this)))
	 */
	public boolean canBeAddedTo(World world){
		return super.canBeAddedTo(world) && (world == null || world.hasAsGameObject(this));
	}
		
	/**
	 * Check whether this game object has a proper world.
	 * 
	 * @return	True if the world is not effective or if the world has 
	 * 			this Buzam as its Buzam.
	 * 			| result == (getWorld() == null || getWorld().getMazub() == this)
	 */
	@Override
	protected boolean hasProperWorld() {
		return (getWorld() == null || getWorld().hasAsGameObject(this));
	}
	
	/**
	 * A method to update the hit points of this Buzam.
	 * A game object can damage other objects and can be damaged by other game objects.
	 * 
	 * @effect	...
	 * 			| if (!isDead() && !isOverlappingWith(Terrain.WATER) && !isOverlappingWith(Terrain.MAGMA))
	 * 			|	then getHpTimer().reset()
	 * @effect	...
	 * 			| if(!isDead() && if(isOverlappingWith(Terrain.WATER)))
	 * 			|	then updateHitPointsTerrain(Terrain.WATER)
	 * @effect	...
	 * 			| if(!isDead() && if(isOverlappingWith(Terrain.MAGMA)))
	 * 			|	then updateHitPointsTerrain(Terrain.MAGMA)
	 * @effect 	...
	 * 			| if(!isDead() && if(canConsumePlant()))
	 * 			| 	then for each plant in getWorld().getAllPlants():
	 * 			|		if(!isDead() && !plant.isDead())
	 * 			|			 && isOverlappingWith(plant) && canConsumePlant())
	 *	 		|			then this.hurt(plant)
	 * @effect	...
	 * 			| if(!isDead() && alien != null && !alien.isImmune()
	 * 			|			 && isOverlappingWith(alien) && !alien.standsOn(this))
	 *			|	then alien.getHurtBy(this)
	 * @effect	...
	 * 			| if(!isDead() && alien != null && !alien.isImmune()
	 * 			|			 && isOverlappingWith(alien) && !isImmune())
	 *			|	then getHurtBy(alien), isHurt = true
	 * @effect 	...
	 * 			| if(!isDead())
	 * 			| 	then for each object in allSharksAndSlimes:
	 * 			|		if(!isDead() && && isOverlappingWith(object)) && !isImmune())
	 *	 		|			then getHurtBy(alien), isHurt = true
	 * @effect 	...
	 * 			| if(!isDead())
	 * 			| 	then for each object in allSharksAndSlimes:
	 * 			|		if(!isDead() && && isOverlappingWith(object)) && !object.isImmune())
	 *	 		|			then object.getHurtBy(this)
	 * @effect	...
	 * 			| if(isHurt)
	 * 			|	 getImmuneTimer().reset()
	 * @effect	...
	 * 			| if(isHurt && isDead())
	 * 			|	 getHpTimer().reset()
	 * @effect	...
	 * 			| if(isDead() && getHpTimer().getTimeSum()>= 0.6)
	 * 			|	 terminate()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void updateHitPoints(){
		Mazub alien = getWorld().getMazub();
		boolean isHurt = false;
		if(!isDead()){
			if (!isOverlappingWith(Terrain.WATER) && !isOverlappingWith(Terrain.MAGMA))
				getHpTimer().reset();
			if(isOverlappingWith(Terrain.WATER)){
				updateHitPointsTerrain(Terrain.WATER);
			}
			if(isOverlappingWith(Terrain.MAGMA)){
				updateHitPointsTerrain(Terrain.MAGMA);
			}
			if(canConsumePlant()){
				for(Plant plant: getWorld().getAllPlants()){
					if(!isDead() && !plant.isDead() && isOverlappingWith(plant) && canConsumePlant()){
						this.hurt(plant);
					}
				}
			}
			if (!isDead() && alien != null && !alien.isImmune() && isOverlappingWith(alien)){
				if (!alien.standsOn(this)){
					alien.getHurtBy(this);
				}
				if(!isImmune()){
					getHurtBy(alien);
					isHurt = true;
				}
			}
			for (Character object: (Set<Character>)getWorld().filterAllGameObjects(
					t->((t instanceof Slime) || t instanceof Shark))){
				if(!isDead() && isOverlappingWith(object)){
					if(!isImmune()){
						getHurtBy(object);
						isHurt = true;
					}
					if(!object.isImmune())
						object.getHurtBy(this);
				}
			}
		}
		if(isHurt)
			getImmuneTimer().reset();
		if (isHurt && isDead())
			getHpTimer().reset();
		else if (isDead() && getHpTimer().getTimeSum()>= 0.6){
			terminate();
		}
	}
	
	/**
	 * A method to get damage by another game object.
	 * 
	 * @effect	...
	 * 			| if(!isImmune() && if((other instanceof Mazub) || (other instanceof Shark) || (other instanceof Slime))
	 * 			|		&& if(!this.standsOn(other)))
	 * 			|	then subtractHp(50)
	 * @effect	...
	 * 			| if(!isImmune() && !(other instanceof Buzam))
	 * 			|	then other.hurt(this)
	 */
	@Override
	protected void getHurtBy(GameObject other){
		if(!isImmune()){
			if((other instanceof Mazub) || (other instanceof Shark) || (other instanceof Slime)){
				if(!this.standsOn(other))
					subtractHp(50);
			}
			else if(!(other instanceof Buzam))
				other.hurt(this);
		}
	}

	/**
	 * A method to get damage by another game object.
	 * 
	 * @effect	...
	 * 			| if(!other.isDead() && other instanceof Mazub && !((Mazub) other).isImmune() &&
	 *			|    !((Mazub) other).standsOn(this))
	 *			|	then ((Mazub) other).getImmuneTimer().reset(), other.subtractHp(50)
	 *			|	if(other.isDead())
	 *			|		then other.getHpTimer().reset()
	 *			| else if(other instanceof Shark && !((Shark) other).isImmune())
	 *			|	then other.subtractHp(50)
	 *			|	if(other.isDead())
	 *			|		then other.getHpTimer().reset()
	 *			| else if(other instanceof Plant)
	 *			|	then other.setHitPoints(0), other.getHpTimer().reset(), this.consumePlant()
	 *			| else if(!(other instanceof Buzam))
	 *			|	other.getHurtBy(this)
	 */
	protected void hurt(GameObject other){
		if(other instanceof Mazub && !((Mazub) other).isImmune() &&
				!((Mazub) other).standsOn(this)){
			((Mazub) other).getImmuneTimer().reset();
			other.subtractHp(50);
			if (other.isDead())
				other.getHpTimer().reset();
		}	
		else if(((other instanceof Shark) || (other instanceof Slime)) && !((Character) other).isImmune()){
			other.subtractHp(50);
			if(other instanceof Slime)
				((Slime)other).getSchool().reduceHpAll((Slime)other);
			((Character)other).getImmuneTimer().reset();
			if (other.isDead())
				other.getHpTimer().reset();
		}
		else if(other instanceof Plant){
			other.setHitPoints(0);
			other.getHpTimer().reset();
			this.consumePlant();
		}
		else if(!(other instanceof Buzam))
			other.getHurtBy(this);
	}

	/**
	 * Terminate this Mazub.
	 * 
	 * @pre		The game object may not have hit points anymore.
	 * 			| getHitPoints()==0
	 * @pre		The time sum belonging to the hit point timer of this
	 * 			game object must be greater than 0.6 seconds.
	 * 			| getHpTimer().getTimeSum()>0.6
	 * @effect	The Buzam is terminated.
	 * 			| setIsTerminated()
	 * @effect	The world no longer refers to this Buzam.
	 * 			| getWorld().removeAsGameObject(this)
	 * @effect	This Buzam no longer refers a world.
	 * 			| setWorld(null) 
	 */ 
	@Override
	public void terminate(){
		assert (getHitPoints()==0);
		assert getHpTimer().getTimeSum() > 0.6;
		setIsTerminated();
		getWorld().removeAsGameObject(this);
		setWorld(null);
	}

	/**
	 * Return a textual representation for this Buzam.
	 * 
	 * @return	...
	 * 			| let
	 * 			|	if(getProgram()!= null)
	 * 			|		then message = "controlled by \na program."
	 * 			|	else message = "without a program."
	 * 			| in
	 * 			| 	result == "Buzam at position\n" + getPosition().toString() +
	 *			|			"\nwith " + String.valueOf(getHitPoints()) + " hit points\nand" +  message
	 */
	@Override
	public String toString(){
		String programMessage;
		if(getProgram()!= null)
			programMessage = "controlled by \na program.";
		else
			programMessage = "without a program.";
		return "Buzam at position\n" + getPosition().toString() +
				"\nwith " + String.valueOf(getHitPoints()) + " hit points\nand" +  programMessage;
	}

	/**
	 * Returns whether Buzam can have a program or not.
	 * 
	 * @return	...
	 * 			| result == true
	 */
	@Override
	protected boolean canHaveProgram() {
		return true;
	}
	
}
