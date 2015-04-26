package jumpingalien.model;

import java.util.HashSet;

/**
 * A class concerning schools.
 * 
 * @invar	...
 * 			| hasProperSlimes()
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version 1.0
 */
public class School {
	
	/**
	 * Returns the amount of slimes in this school. 
	 */
	int getNbSlimes(){
		return slimes.size();
	}
	
	/**
	 * Returns a hash set of all slimes in this school.
	 */
	HashSet<Slime> getAllSlimes(){
		return slimes;
	}
	
	/**
	 * Check whether this school has proper slimes.
	 * 
	 * @return	...
	 * 			| if(for some slime in getAllSlimes()
	 * 			|	 slime.getSchool() != this)
	 * 			|	then result == false
	 * 			| else
	 * 			| 	result == true
	 */
	boolean hasProperSlimes(){
		for(Slime slime: slimes){
			if(slime.getSchool() != this)
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
	void removeSlime(Slime slime){
		assert hasAsSlime(slime);
		slimes.remove(slime);
		slime.setSchool(null);
	}
	
	/**
	 * A variable storing all the slimes in this school.
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
