package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

public class Tile {
	public Tile(World world, int xPosition, int yPosition, Terrain geologicalFeature){
		this.world = world;
		assert isValidXPosition(xPosition);
		assert isValidYPosition(yPosition);
		this.xPosition = xPosition;
		this.yPosition = yPosition;		
	}
	
	
	public World getWorld() {
		return world;
	}
	
	private final World world;
	
	/**
	 * Return the x position of this tile.
	 */
	@Basic@Immutable
	public double getXPosition(){
		return this.xPosition;
	}
	
	private boolean isValidXPosition(int xpos){
		return (xpos >=0 && xpos<= getWorld().getWorldWidth() &&
				(xpos%getWorld().getTileSize() == 0));
	}
	
	private final int xPosition;
	
	@Basic@Immutable
	public double getYPosition(){
		return this.yPosition;
	}
	
	private boolean isValidYPosition(int ypos){
		return (ypos >=0 && ypos<= getWorld().getWorldWidth() &&
				(ypos%getWorld().getTileSize() == 0));
	}
	
	private final int yPosition;
}
