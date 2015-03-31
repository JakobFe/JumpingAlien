package jumpingalien.model;

public enum Terrain {
	AIR(0,0), GROUND(1,0), WATER(2,2), MAGMA(3,50);
	
	private Terrain(int value, int hpLoss){
		this.value = value;
		this.hpLoss = hpLoss;
	}
	
	public int getValue() {
		return value;
	}

	private final int value;
	
	public int getHpLoss() {
		return hpLoss;
	}

	private final int hpLoss;
}
