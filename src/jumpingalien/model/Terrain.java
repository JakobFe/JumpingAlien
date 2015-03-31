package jumpingalien.model;

public enum Terrain {
	AIR(0,0,true), GROUND(1,0,false), WATER(2,2,true), MAGMA(3,50,true);
	
	private Terrain(int value, int hpLoss, boolean isPassable){
		this.value = value;
		this.hpLoss = hpLoss;
		this.isPassable = isPassable;
	}
	
	public int getValue() {
		return value;
	}

	private final int value;
	
	public int getHpLoss() {
		return hpLoss;
	}
	
	public boolean isPassable(){
		return isPassable;
	}
	
	public final boolean isPassable;

	private final int hpLoss;
}
