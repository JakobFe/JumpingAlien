package jumpingalien.part2.facade;

import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.intArray;

import java.util.Collection;

import jumpingalien.model.Direction;
import jumpingalien.model.IllegalJumpInvokeException;
import jumpingalien.model.IllegalTimeIntervalException;
import jumpingalien.model.IllegalXPositionException;
import jumpingalien.model.IllegalYPositionException;
import jumpingalien.model.Mazub;
import jumpingalien.model.World;
import jumpingalien.model.Plant;
import jumpingalien.model.Slime;
import jumpingalien.model.Shark;
import jumpingalien.model.School;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Facade implements IFacadePart2 {

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		// TODO Auto-generated method stub
		try{return new Mazub(pixelLeftX,pixelBottomY,sprites);}
		catch (IllegalXPositionException exc){
			throw new ModelException("Illegal X Position!");}
		catch (IllegalYPositionException exc){
			throw new ModelException("Illegal Y Position!");}
	}

	@Override
	public int[] getLocation(Mazub alien) {
		// TODO Auto-generated method stub
		return intArray(alien.getPosition().getDisplayedXPosition(),
				alien.getPosition().getDisplayedYPosition());
	}

	@Override
	public double[] getVelocity(Mazub alien) {
		// TODO Auto-generated method stub
		return doubleArray(alien.getHorDirection().getFactor()*alien.getHorVelocity(),
			   alien.getVertDirection().getFactor()*alien.getVertVelocity());
	}

	@Override
	public double[] getAcceleration(Mazub alien) {
		// TODO Auto-generated method stub
		return doubleArray(alien.getHorDirection().getFactor()*alien.getHorAcceleration(),
						   alien.getVertAcceleration());
	}

	@Override
	public int[] getSize(Mazub alien) {
		// TODO Auto-generated method stub
		return intArray(alien.getWidth(),alien.getHeight());
	}

	@Override
	public Sprite getCurrentSprite(Mazub alien) {
		// TODO Auto-generated method stub
		alien.updateSpriteIndex();
		return alien.getCurrentSprite();
	}

	@Override
	public void startJump(Mazub alien) {
		// TODO Auto-generated method stub
		try{
			alien.startJump();}
		catch (IllegalJumpInvokeException exc){
			new ModelException("Illegal Jumping Invoke!");
			System.out.println("Illegal Jumping Invoke!");
		}
	}

	@Override
	public void endJump(Mazub alien) {
		// TODO Auto-generated method stub
		alien.endJump();
	}

	@Override
	public void startMoveLeft(Mazub alien) {
		// TODO Auto-generated method stub
		alien.startMove(Direction.LEFT);
	}

	@Override
	public void endMoveLeft(Mazub alien) {
		// TODO Auto-generated method stub
		if (alien.isMoving(Direction.LEFT))
			alien.endMove(Direction.LEFT);
	}

	@Override
	public void startMoveRight(Mazub alien) {
		// TODO Auto-generated method stub
		alien.startMove(Direction.RIGHT);
	}

	@Override
	public void endMoveRight(Mazub alien) {
		// TODO Auto-generated method stub
		if (alien.isMoving(Direction.RIGHT))
			alien.endMove(Direction.RIGHT);
	}

	@Override
	public void startDuck(Mazub alien) {
		// TODO Auto-generated method stub
		alien.startDuck();
	}

	@Override
	public void endDuck(Mazub alien) {
		// TODO Auto-generated method stub
		alien.endDuck();
	}

	@Override
	public void advanceTime(Mazub alien, double dt) {
		// TODO Auto-generated method stub
		try{
		alien.advanceTime(dt);}
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
	public int getNbHitPoints(Mazub alien) {
		// TODO Auto-generated method stub
		return alien.getHitPoints();
	}

	@Override
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY) {
		// TODO Auto-generated method stub
		return new World(tileSize,nbTilesX,nbTilesY,visibleWindowWidth,
						 visibleWindowHeight,targetTileX,targetTileY);
	}

	@Override
	public int[] getWorldSizeInPixels(World world) {
		// TODO Auto-generated method stub
		return intArray(world.getWorldWidth(),world.getWorldHeight());
	}

	@Override
	public int getTileLength(World world) {
		// TODO Auto-generated method stub
		return world.getTileSize();
	}

	@Override
	public void startGame(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isGameOver(World world) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean didPlayerWin(World world) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void advanceTime(World world, double dt) {
		// TODO Auto-generated method stub
		world.advanceTime(dt);
	}

	@Override
	public int[] getVisibleWindow(World world) {
		// TODO Auto-generated method stub
		return intArray(world.getWindowXPos(),world.getWindowYPos(),
						world.getWindowXPos()+world.getVisibleWindowWidth(),
						world.getWindowYPos()+world.getVisibleWindowHeight());
	}

	@Override
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY) {
		// TODO Auto-generated method stub
		/*return intArray(world.getTileAtTilePos(tileX, tileY).getXPosition(),
						world.getTileAtTilePos(tileX, tileY).getTileYPos());
		*/
		return null;
	}

	@Override
	public int[][] getTilePositionsIn(World world, int pixelLeft,
			int pixelBottom, int pixelRight, int pixelTop) {
		// TODO Auto-generated method stub
		return world.getTilePositionsIn(pixelLeft,pixelBottom,pixelRight,pixelTop);
	}

	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY)
			throws ModelException {
		// TODO Auto-generated method stub
		return world.getTileAtPos(pixelX,pixelY).getGeoFeature().getValue();
	}

	@Override
	public void setGeologicalFeature(World world, int tileX, int tileY,
			int tileType) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setMazub(World world, Mazub alien) {
		// TODO Auto-generated method stub
		world.setMazub(alien);
	}

	@Override
	public boolean isImmune(Mazub alien) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Plant createPlant(int x, int y, Sprite[] sprites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPlant(World world, Plant plant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Plant> getPlants(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getLocation(Plant plant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite getCurrentSprite(Plant plant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shark createShark(int x, int y, Sprite[] sprites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addShark(World world, Shark shark) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Shark> getSharks(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getLocation(Shark shark) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite getCurrentSprite(Shark shark) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public School createSchool() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Slime createSlime(int x, int y, Sprite[] sprites, School school) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSlime(World world, Slime slime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Slime> getSlimes(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getLocation(Slime slime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite getCurrentSprite(Slime slime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public School getSchool(Slime slime) {
		// TODO Auto-generated method stub
		return null;
	}

}
