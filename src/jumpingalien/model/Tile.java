package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

public class Tile {
	public Tile(World world, int xPosition, int yPosition, Terrain geologicalFeature,
			boolean isTargetTile) throws IllegalArgumentException{
		if(!isValidWorld(world))
			throw new IllegalArgumentException("This world is not valid!");
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
	
	protected World getWorld() {
		return world;
	}
	
	private boolean isValidWorld(World world){
		return world == null || world.hasAsTile(this);
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
		if (!geoFeature.isPassable())
			getWorld().addAsImpassableTile(this);
	}

	private Terrain geoFeature = Terrain.AIR;
	
	public boolean isTargetTile(){
		return this.isTargetTile;
	}
	
	private final boolean isTargetTile;
	
	@Override
	public String toString(){
		return "Tile at tile position " + getTileXPos()+ "," + getTileYPos()+
				" in " + getWorld().toString();
	}
}
