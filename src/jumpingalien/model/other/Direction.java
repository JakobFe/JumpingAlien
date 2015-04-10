package jumpingalien.model.other;

public enum Direction {
	
	RIGHT(1), LEFT(-1), UP(1), DOWN(-1), NULL(0);
	
	private Direction(int factor){
		this.factor = factor;
	}
	
	public int getFactor() {
		return factor;
	}
	
	private final int factor;

}
