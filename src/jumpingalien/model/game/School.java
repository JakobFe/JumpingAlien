package jumpingalien.model.game;

import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class concerning schools as a collection of slimes.
 * 
 * @invar	...
 * 			| hasProperSlimes()
 * @invar	...
 * 			| hasProperWorld()
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version 2.0
 */
public class School {
	
	/**
	 * Returns the amount of slimes in this school. 
	 */
	@Model
	int getNbSlimes(){
		return slimes.size();
	}
	
	/**
	 * Returns a set of all slimes in this school.
	 */
	@Basic@Model
	HashSet<Slime> getAllSlimes(){
		return slimes;
	}
	
	/**
	 * Return a slime of this school.
	 * 
	 * @return	...
	 * 			| result.getSchool() == this &&
	 * 			| this.hasAsSlime(result)
	 */
	@Model
	Slime getASlime(){
		if(getNbSlimes() == 0)
			return null;
		else{
			for(Slime slime: slimes)
				return slime;
		}
		return null;
	}
	
	/**
	 * Check whether this school has proper slimes.
	 * 
	 * @return	...
	 * 			| if(for some slime in getAllSlimes()
	 * 			|	 slime.getSchool() != this)
	 * 			|	then result == false
	 * @return	...
	 * 			| else if(getNbSlimes() != null)
	 * 			|	let world = getASlime().getWorld()
	 * 			|	in
	 * 			|	if(for some slime in getAllSlimes()
	 * 			|		slime.getWorld() != world
	 * 			|		then result == false
	 * @return	...
	 * 			| else
	 * 			|	result == true
	 */
	@Model
	boolean hasProperSlimes(){
		World slimeWorld = null;
		for(Slime slime: slimes){
			if(slime.getSchool() != this)
				return false;
			if(slimeWorld == null)
				slimeWorld = slime.getWorld();
			else if(slime.getWorld() != slimeWorld)
				return false;
		}
		return true;
	}
	
	/**
	 * Check whether this school has a proper world.
	 * 
	 * @return	...
	 * 			| if(getNbSlimes() != null)
	 * 			|	let world = getASlime().getWorld()
	 * 			|	in
	 * 			|	if(for some slime in getAllSlimes()
	 * 			|		slime.getWorld() != world
	 * 			|		then result == false
	 * 			| else
	 * 			|	result == true
	 */
	@Model
	boolean hasProperWorld(){
		World slimeWorld = null;
		for(Slime slime: slimes){
			if(slimeWorld == null)
				slimeWorld = slime.getWorld();
			if(slime.getWorld() != slimeWorld ||
			   !slimeWorld.getAllSchools().contains(this))
				return false;
		}
		return true;
	}
	
	/**
	 * A method to reduce the hit points of all slimes that are part of this school
	 * with one point except for a given slime.
	 * 
	 * @param 	other
	 * 			The slime that will not lose hit points.
	 * @effect	...
	 * 			| for each slime in getAllSlimes()
	 * 			|	if(slime != other)
	 * 			|		then slime.subtractHp(1) 
	 */
	@Model
	void reduceHpAll(Slime other){
		for(Slime slime: slimes){
			if(slime != other)
				slime.subtractHp(1);
		}
	}
	
	/**
	 * A method to add one hit point to the hit points of all slimes that are
	 * part of this school except for a given slime.
	 * 
	 * @param 	other
	 * 			The slime that will not gain hit points.
	 * @effect	...
	 * 			| for each slime in getAllSlimes()
	 * 			|	if(slime != other)
	 * 			|		slime.setHitPoints(slime.getHitPoints()+1)
	 */
	@Model
	void addHpAll(Slime other){
		for(Slime slime: slimes){
			if(slime != other)
				slime.setHitPoints(slime.getHitPoints()+1);
		}
	}
	
	/**
	 * A method to add a slime to this school.
	 * 
	 * @param 	slime
	 * 			The slime to add to this school.
	 * @pre		...
	 * 			| slime.getSchool() == null
	 * @post	...
	 * 			| new.getAllSlimes().contains(slime)
	 * 			| (new slime).getSchool() == this
	 */
	@Raw@Model
	void addSlime(Slime slime){
		assert slime.getSchool() == null;
		slimes.add(slime);
		slime.setSchool(this);
	}
	
	/**
	 * A method to check whether a given slime is part of this school.
	 * 
	 * @param 	slime
	 * 			The slime to check for.
	 * @return	...
	 * 			| result == getAllSlimes().contains(slime)
	 */
	@Model
	boolean hasAsSlime(Slime slime){
		return slimes.contains(slime);
	}
	
	/**
	 * A method to remove a slime from this school.
	 * 
	 * @param 	slime
	 * 			The slime to remove from this school.
	 * @pre		...
	 * 			| hasAsSlime(slime)
	 * @post	...
	 * 			| !new.getAllSlimes().contains(slime)
	 * 			| (new slime).getSchool() == null
	 */
	@Model
	void removeSlime(Slime slime){
		assert hasAsSlime(slime);
		slimes.remove(slime);
		slime.setSchool(null);
	}
	
	/**
	 * A variable storing all the slimes in this school.
	 * 
	 * @invar	...
	 * 			| slimes != null
	 * @invar	...
	 * 			| for each slime in slimes
	 * 			|	slime.getSchool() == this
	 */
	private HashSet<Slime> slimes = new HashSet<Slime>();
	
	/**
	 * Return a textual representation of this school.
	 * 
	 * @return	...
	 * 			| result.contains(String.valueOf(hashCode()%10)) 
	 */
	@Override
	public String toString(){
		return String.valueOf(hashCode()%10);
	}
}
