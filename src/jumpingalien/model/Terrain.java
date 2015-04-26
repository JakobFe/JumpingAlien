package jumpingalien.model;

/**
 * An enumeration involving terrain types as an attribute for tiles.
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version	1.0
 *
 */
public enum Terrain {
	AIR(0,6,true), GROUND(1,0,false), WATER(2,2,true), MAGMA(3,50,true);
	
	/**
	 * Initialize this terrain with given value, given hit points loss
	 * and given characteristic of obstruction.
	 * 
	 * @param 	value
	 * 			The value for this new terrain.
	 * @param 	hpLoss
	 * 			The hit points loss for this new terrain.
	 * @param 	isPassable
	 * 			The characteristic of obstruction for this new terrain.
	 * @post	...
	 * 			| new.getValue() == value
	 * 			| new.getHpLoss() == hpLoss
	 * 			| new.isPassable() == isPassable
	 */
	private Terrain(int value, int hpLoss, boolean isPassable){
		this.value = value;
		this.hpLoss = hpLoss;
		this.isPassable = isPassable;
	}
	
	/**
	 * Returns the value of this terrain type.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * A variable storing the terrain type number.
	 */
	private final int value;
	
	/**
	 * Return the hit points loss of this terrain type.
	 */
	public int getHpLoss() {
		return hpLoss;
	}

	/**
	 * A variable storing the hit points loss per 0.2 seconds.
	 */
	private final int hpLoss;
	
	/**
	 * Return whether this terrain type is passable.
	 */
	public boolean isPassable(){
		return isPassable;
	}
	
	/**
	 * A variable storing the characteristic of obstruction for this terrain type.
	 */
	private final boolean isPassable;
	
	/**
	 * A method to transform a given value to a terrain type.
	 * 
	 * @param 	value
	 * 			The value to transform.
	 * @return	...
	 * 			| if (for some ter in Terrain.values() ter.getValue() == value)
	 * 			|	then result == ter
	 * 			| else
	 * 			|	result == null
	 */
	public static Terrain mapValueToTerrain(int value){
		for(Terrain ter: Terrain.values()){
			if (ter.getValue() == value)
				return ter;
		}
		return null;
	}
}
