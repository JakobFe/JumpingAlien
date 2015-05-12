package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.game.Direction;
import jumpingalien.model.game.Mazub;
import jumpingalien.model.game.Position;
import jumpingalien.model.game.Terrain;
import jumpingalien.model.game.World;
import jumpingalien.util.Sprite;

import org.junit.Before;
import org.junit.Test;

public class WorldTest {

	@Before
	public void setUp() throws Exception {
		sprites = spriteArrayForSize(10, 10, 10 + 2 * 10);
		mazubPos_225_50 = new Mazub(new Position(225,50),1,3,sprites);
		
		// X........X
		// XM......TX
		// XXXXXXXXXX
		testWorld = new World(50,10,5,500,500,8,1);
		testWorld.getTileAtTilePos(0,0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(1,0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(2,0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(3,0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(4,0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(5,0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(6,0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(7,0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(8,0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(9,0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(0,1).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(0,2).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(9,1).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(9,2).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(1,1).setGeoFeature(Terrain.MAGMA);
	}
	
	private static Sprite[] sprites;
	private Mazub mazubPos_225_50;
	private World testWorld;

	@Test
	public void createWorldCorrect(){
		World world = new World(5,12,8,100,100,10,6);
		assertArrayEquals(intArray(60,40),intArray(
				world.getWorldWidth(), world.getWorldHeight()));
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	@Test
	public void didPlayerWinCorrect(){
		testWorld.setMazub(mazubPos_225_50);
		mazubPos_225_50.startMove(Direction.RIGHT);
		for (int i = 0; i < 200; i++) {
			mazubPos_225_50.advanceTime(0.2 / 9);
		}
		assertTrue(testWorld.didPlayerWin());
		assertTrue(testWorld.isGameOver());
	}
	
	@Test
	public void isGameOverByWinCorrect(){
		testWorld.setMazub(mazubPos_225_50);
		mazubPos_225_50.startMove(Direction.RIGHT);
		for (int i = 0; i < 200; i++) {
			mazubPos_225_50.advanceTime(0.2 / 9);
		}
		assertTrue(testWorld.isGameOver());
	}

	
	@Test
	public void isGameOverByDeadCorrect(){
		testWorld.setMazub(mazubPos_225_50);
		mazubPos_225_50.startMove(Direction.LEFT);
		for (int i = 0; i < 200; i++) {
			testWorld.advanceTime(0.2 / 9);
		}
		assertTrue(testWorld.isGameOver());
	}
}
