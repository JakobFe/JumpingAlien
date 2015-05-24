package jumpingalien.model.game;

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
	 * 			| 	then 
	 * @effect	If this Mazub is dead and the time sum of the hit points timer
	 * 			is greater than 0.6 seconds, this Buzam is terminated.
	 * 			| if(isDead() && getHpTimer().getTimeSum()>0.6)
	 * 			|	 terminate()
	 */
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
			for (Slime slime: getWorld().getAllSlimes()){
				if(!isDead() && isOverlappingWith(slime)){
					if(!isImmune()){
						getHurtBy(slime);
						isHurt = true;
					}
					if(!slime.isImmune())
						slime.getHurtBy(this);
				}
			}
			for (Shark shark: getWorld().getAllSharks()){
				if(!isDead() && isOverlappingWith(shark)){
					if(!isImmune()){
						getHurtBy(shark);
						isHurt = true;
					}
					if(!shark.isImmune())
						shark.getHurtBy(this);
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

	@Override
	public void terminate(){
		assert (getHitPoints()==0);
		assert getHpTimer().getTimeSum() > 0.6;
		setIsTerminated();
		getWorld().removeAsGameObject(this);
		setWorld(null);
	}
	
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

	@Override
	protected boolean canHaveProgram() {
		return true;
	}
	
}
