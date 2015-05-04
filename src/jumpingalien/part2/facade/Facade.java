package jumpingalien.part2.facade;

import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.intArray;

import java.util.Collection;

import jumpingalien.model.*;
import jumpingalien.model.exceptions.*;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Facade implements IFacadePart2 {

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		try{return new Mazub(new Position(pixelLeftX,pixelBottomY),sprites);}
		catch (IllegalXPositionException exc){
			throw new ModelException("Illegal X Position!");}
		catch (IllegalYPositionException exc){
			throw new ModelException("Illegal Y Position!");}
	}

	@Override
	public int[] getLocation(Mazub alien) {
		try {
			return intArray(alien.getPosition().getDisplayedXPosition(),
					alien.getPosition().getDisplayedYPosition());
		} catch (NullPointerException e) {
			System.out.println("Modelexception!");
			throw new ModelException("This Mazub is terminated!");
		}
	}

	@Override
	public double[] getVelocity(Mazub alien) {
		return doubleArray(alien.getHorDirection().getFactor()*alien.getHorVelocity(),
			   alien.getVertDirection().getFactor()*alien.getVertVelocity());
	}

	@Override
	public double[] getAcceleration(Mazub alien) {
		return doubleArray(alien.getHorDirection().getFactor()*alien.getHorAcceleration(),
						   alien.getVertAcceleration());
	}

	@Override
	public int[] getSize(Mazub alien) {
		return intArray(alien.getWidth(),alien.getHeight());
	}

	@Override
	public Sprite getCurrentSprite(Mazub alien) {
		alien.updateSpriteIndex();
		return alien.getCurrentSprite();
	}

	@Override
	public void startJump(Mazub alien) {
		if(!alien.isDead()){
			try{
				alien.startJump();}
			catch (IllegalJumpInvokeException exc){
				new ModelException("Illegal Jumping Invoke!");
				System.out.println("Illegal Jumping Invoke!");
			}
		}
	}

	@Override
	public void endJump(Mazub alien) {
		alien.endJump();
	}

	@Override
	public void startMoveLeft(Mazub alien) {
		if(!alien.isDead())
			alien.startMove(Direction.LEFT);
	}

	@Override
	public void endMoveLeft(Mazub alien) {
		if (alien.isMoving(Direction.LEFT))
			alien.endMove(Direction.LEFT);
		if(!alien.isMoving(Direction.RIGHT))
			alien.endMove(Direction.LEFT);
	}

	@Override
	public void startMoveRight(Mazub alien) {
		if(!alien.isDead())
			alien.startMove(Direction.RIGHT);
	}

	@Override
	public void endMoveRight(Mazub alien) {
		//if (alien.isMoving(Direction.RIGHT))
		//alien.endMove(Direction.RIGHT);
		if(!alien.isMoving(Direction.LEFT))
			alien.endMove(Direction.RIGHT);
	}

	@Override
	public void startDuck(Mazub alien) {
		if(!alien.isDead())
			alien.startDuck();
	}

	@Override
	public void endDuck(Mazub alien) {
		alien.endDuck();
	}
	
	@Override
	public int getNbHitPoints(Mazub alien) {
		return alien.getHitPoints();
	}

	@Override
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY) {
		return new World(tileSize,nbTilesX,nbTilesY,visibleWindowWidth,
						 visibleWindowHeight,targetTileX,targetTileY);
	}

	@Override
	public int[] getWorldSizeInPixels(World world) {
		return intArray(world.getWorldWidth(),world.getWorldHeight());
	}

	@Override
	public int getTileLength(World world) {
		return world.getTileSize();
	}

	@Override
	public void startGame(World world) {
		world.setGameStarted(false);
	}

	@Override
	public boolean isGameOver(World world) {
		return world.isGameOver();
	}

	@Override
	public boolean didPlayerWin(World world) {
		return world.didPlayerWin();
	}

	@Override
	public void advanceTime(World world, double dt) {
		try{
			world.advanceTime(dt);}
			catch (IllegalXPositionException exc){
				new ModelException("Illegal X Position!");
				System.out.println("Illegal X Position!");
				}
			catch (IllegalYPositionException exc){
				new ModelException("Illegal Y Position!");
				System.out.println("Illegal Y Position!");
				}
			catch (IllegalTimeIntervalException exc){
				throw new ModelException("Computing time to long!");
			}
	}

	@Override
	public int[] getVisibleWindow(World world) {
		return intArray(world.getWindowXPos(),world.getWindowYPos(),
						world.getWindowXPos()+world.getVisibleWindowWidth(),
						world.getWindowYPos()+world.getVisibleWindowHeight());
	}

	@Override
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY) {
		return intArray(world.getTileAtTilePos(tileX, tileY).getXPosition(),
						world.getTileAtTilePos(tileX, tileY).getYPosition());
	}

	@Override
	public int[][] getTilePositionsIn(World world, int pixelLeft,
			int pixelBottom, int pixelRight, int pixelTop) {
		return world.getTilePositionsIn(pixelLeft,pixelBottom,pixelRight,pixelTop);
	}

	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY)
			throws ModelException {
		return world.getTileAtPos(pixelX,pixelY).getGeoFeature().getValue();
	}

	@Override
	public void setGeologicalFeature(World world, int tileX, int tileY,
			int tileType) {
		Terrain terrain = Terrain.mapValueToTerrain(tileType);
		world.getTileAtTilePos(tileX, tileY).setGeoFeature(terrain);
	}

	@Override
	public void setMazub(World world, Mazub alien) {
		world.setMazub(alien);
	}

	@Override
	public boolean isImmune(Mazub alien) {
		return alien.isImmune();
	}

	@Override
	public Plant createPlant(int x, int y, Sprite[] sprites) {
		if(enablePlants)
			return new Plant(new Position(x,y),sprites);
		else
			return null;
	}

	@Override
	public void addPlant(World world, Plant plant) {
		if(enablePlants)
			world.addAsPlant(plant);
	}

	@Override
	public Collection<Plant> getPlants(World world) {
		return world.getAllUnterminatedPlants();
	}

	@Override
	public int[] getLocation(Plant plant) {
		return intArray(plant.getPosition().getDisplayedXPosition(),
				plant.getPosition().getDisplayedYPosition());
	}

	@Override
	public Sprite getCurrentSprite(Plant plant) {
		return plant.getCurrentSprite();
	}

	@Override
	public Shark createShark(int x, int y, Sprite[] sprites) {
		if(enableSharks)
			return new Shark(new Position(x,y),sprites);
		else
			return null;
	}

	@Override
	public void addShark(World world, Shark shark) {
		if(enableSharks)
			world.addAsShark(shark);
	}

	@Override
	public Collection<Shark> getSharks(World world) {
		return world.getAllUnterminatedSharks();
	}

	@Override
	public int[] getLocation(Shark shark) {
		return intArray(shark.getPosition().getDisplayedXPosition(),
				shark.getPosition().getDisplayedYPosition());
	}

	@Override
	public Sprite getCurrentSprite(Shark shark) {
		return shark.getCurrentSprite();
	}

	@Override
	public School createSchool() {
		return new School();
	}

	@Override
	public Slime createSlime(int x, int y, Sprite[] sprites, School school) {
		if(enableSlimes){	
			System.out.print("Slime added at position");
			System.out.println(new Position(x,y).toString());
			return new Slime(new Position(x,y),sprites,school);
		}
		else
			return null;
	}

	@Override
	public void addSlime(World world, Slime slime) {
		if(enableSlimes)
			world.addAsSlime(slime);
	}

	@Override
	public Collection<Slime> getSlimes(World world) {
		return world.getAllSlimes();
	}

	@Override
	public int[] getLocation(Slime slime) {
		return intArray(slime.getPosition().getDisplayedXPosition(),
				slime.getPosition().getDisplayedYPosition());
	}

	@Override
	public Sprite getCurrentSprite(Slime slime) {
		return slime.getCurrentSprite();
	}

	@Override
	public School getSchool(Slime slime) {
		return slime.getSchool();
	}
	
	/**
	 * Added constants to easily turn off plants, sharks and slimes.
	 */
	//private static final boolean enablePlants = false;
	//private static final boolean enableSharks = false;
	//private static final boolean enableSlimes = false;
	
	private static final boolean enablePlants = true;
	private static final boolean enableSharks = true;
	private static final boolean enableSlimes = true;
	
}
