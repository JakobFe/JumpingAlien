package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.game.*;
import jumpingalien.util.Sprite;

import org.junit.Before;
import org.junit.Test;

/**
 * A class involving tests for the class of game objects.
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version	1.0
 *
 */
public class GameObjectTest {
	
	@Before
	public void setUp() throws Exception {
		sprites = spriteArrayForSize(10, 10, 10 + 2 * 10);
		mazubPos_225_50 = new Mazub(new Position(225,50),1,3,sprites);
		mazubPos_400_50 = new Mazub(new Position(400,50),1,3,sprites);
		mazubPos_425_201 = new Mazub(new Position(425,201),1,3,sprites);
		slimeSprites = spriteArrayForSize(10,5,2);
		slimePos_100_50 = new Slime(new Position(100,50),slimeSprites,new School());
		
		// X........X
		// X...XX..MX
		// X.......XX
		// X........X
		// XXXXXXXXXX
		testWorld = new World(50,10,5,500,500,9,4);
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
		testWorld.getTileAtTilePos(0,3).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(0,4).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(9,1).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(9,2).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(9,3).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(9,4).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(4,3).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(4,4).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(8,2).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(8,3).setGeoFeature(Terrain.MAGMA);
	}
	
	private static Sprite[] sprites;
	private static Sprite[] slimeSprites;
	private Mazub mazubPos_225_50;
	private Mazub mazubPos_400_50;
	private Mazub mazubPos_425_201;
	private Slime slimePos_100_50;
	private World testWorld;

	
	@Test
	public void createGameObjectCorrect(){
		GameObject alien = new Mazub(new Position(20,0),spriteArrayForSize(2, 2));
		assertArrayEquals(intArray(20,0),intArray(
				alien.getPosition().getDisplayedXPosition(),
				alien.getPosition().getDisplayedYPosition()));
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	@Test
	public void collideLeftWithObjectCorrect(){
		testWorld.setMazub(mazubPos_225_50);
		testWorld.addAsGameObject(slimePos_100_50);
		mazubPos_225_50.startMove(Direction.LEFT);
		
		for (int i = 0; i < 100; i++) {
			mazubPos_225_50.advanceTime(0.2 / 9);
		}
		assertArrayEquals(intArray(109,49),mazubPos_225_50.getPosition().toIntArray());
	}
	
	@Test
	public void dieTerrainCorrect(){
		testWorld.setMazub(mazubPos_425_201);
		
		for (int i = 0; i < 5; i++){ 
			testWorld.advanceTime(0.11);
		}
		assertTrue(mazubPos_425_201.isDead());
	}
	
	@Test
	public void collideRightWithTileCorrect(){
		testWorld.setMazub(mazubPos_400_50);
		mazubPos_400_50.startMove(Direction.RIGHT);
		
		for (int i = 0; i < 100; i++) {
			mazubPos_400_50.advanceTime(0.2 / 9);
		}
		
		assertArrayEquals(intArray(441,49),mazubPos_400_50.getPosition().toIntArray());
	}
}
