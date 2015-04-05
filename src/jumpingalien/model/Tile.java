package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

public class Tile {
	public Tile(World world, int xPosition, int yPosition, Terrain geologicalFeature,
			boolean isTargetTile){
		this.world = world;
		assert isValidXPosition(xPosition);
		assert isValidYPosition(yPosition);
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		setGeoFeature(geologicalFeature);
		this.isTargetTile = isTargetTile;
	}
	
	public Tile(World world, int xPosition, int yPosition, boolean isTargetTile){
		this(world,xPosition,yPosition,Terrain.AIR,isTargetTile);
	}
	
	public World getWorld() {
		return world;
	}
	
	private final World world;
	
	/**
	 * Return the x position of this tile.
	 */
	@Basic@Immutable
	public int getXPosition(){
		return this.xPosition;
	}
	
	public int getTileXPos(){
		return getXPosition()/getWorld().getTileSize();
	}
	
	private boolean isValidXPosition(int xpos){
		return (xpos >=0 && xpos<= getWorld().getWorldWidth() &&
				(xpos%getWorld().getTileSize() == 0));
	}
	
	private final int xPosition;
	
	@Basic@Immutable
	public int getYPosition(){
		return this.yPosition;
	}
	
	public int getTileYPos(){
		return getYPosition()/getWorld().getTileSize();
	}
	
	private boolean isValidYPosition(int ypos){
		return (ypos >=0 && ypos<= getWorld().getWorldWidth() &&
				(ypos%getWorld().getTileSize() == 0));
	}
	
	private final int yPosition;
	
	public Terrain getGeoFeature() {
		return geoFeature;
	}


	public void setGeoFeature(Terrain geoFeature) {
		this.geoFeature = geoFeature;
	}

	private Terrain geoFeature = Terrain.AIR;
	
	public boolean isTargetTile(){
		return this.isTargetTile;
	}
	
	private final boolean isTargetTile;
}
