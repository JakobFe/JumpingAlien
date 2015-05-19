package jumpingalien.part3.tests;

import static org.junit.Assert.*;
import jumpingalien.model.game.*;
import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.expressions.unaryexpression.SearchObject;
import jumpingalien.part3.programs.SourceLocation;

import org.junit.Before;
import org.junit.Test;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;


public class SearchObjectTest {

	@Before
	public void setUp() throws Exception {
		testWorld = new World(25, 40, 20, 1000, 1000, 0, 0);
		
		// Generate an alien on top of a solid tile
		alien = new Mazub(new Position(500,250), spriteArrayForSize(10, 10));
		testWorld.getTileAtPos(500,240).setGeoFeature(Terrain.GROUND);
		
		// Generate a Slime on top of a solid tile
		theSlime = new Slime(new Position(60,250), spriteArrayForSize(10, 10), new School());
		testWorld.getTileAtPos(60, 240).setGeoFeature(Terrain.GROUND);
		
		// Generate a Shark above a pile of water
		theShark = new Shark(new Position(802,250), spriteArrayForSize(10, 10));
		int index = 240;
		while(index > 25){
			testWorld.getTileAtPos(802, index).setGeoFeature(Terrain.WATER);
			index -= 25;
		}
		testWorld.getTileAtPos(802,1).setGeoFeature(Terrain.GROUND);
		
		// Generate an impassable tile above the Mazub
		testWorld.getTileAtPos(500,980).setGeoFeature(Terrain.GROUND);
		
		// Generate a plant above the slime.
		thePlant = new Plant(new Position(60,400),spriteArrayForSize(10, 10));
		
		// Add the game objects to the world.
		testWorld.addAsPlant(thePlant);
		testWorld.setMazub(alien);
		testWorld.addAsShark(theShark);
		testWorld.addAsSlime(theSlime);
		
		// Create a source location
		loc = new SourceLocation(2, 3);
	}
	
	private World testWorld;
	private Mazub alien;
	private Slime theSlime;
	private Shark theShark;
	private Plant thePlant;
	private SourceLocation loc;

	@Test
	public void testSearchTheSlimeToTheLeftOfAlien() {
		SearchObject seeker = new SearchObject(loc, 
				(new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>
				(loc,jumpingalien.part3.programs.IProgramFactory.Direction.LEFT)), 
				new Constant<GameObject>(loc,alien));
		Object result = seeker.outcome();
//		System.out.println("Test search the slime to the left of alien: ");
//		System.out.println(alien);
//		System.out.println(result);
//		System.out.println();
		assertTrue(result == theSlime);
	}
	
	
	@Test
	public void testSearchTheSharkToTheRightOfAlien() {
		SearchObject seeker = new SearchObject(loc, 
				(new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>
				(loc,jumpingalien.part3.programs.IProgramFactory.Direction.RIGHT)), 
				new Constant<GameObject>(loc,alien));
		Object result = seeker.outcome();
//		System.out.println("Test search the shark to the right of alien: ");
//		System.out.println(alien);
//		System.out.println(result);
//		System.out.println();
		assertTrue(result == theShark);
	}
	
	
	@Test
	public void testSearchTheGroundBelowShark() {
		SearchObject seeker = new SearchObject(loc, 
				(new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>
				(loc,jumpingalien.part3.programs.IProgramFactory.Direction.DOWN)), 
				new Constant<GameObject>(loc,theShark));
		Object result = seeker.outcome();
//		System.out.println("Test search the ground below Shark: ");
//		System.out.println(theShark);
//		System.out.println(result);
//		System.out.println();
		assertTrue(result == testWorld.getTileAtPos(802, 2));
	}
	
	@Test
	public void testSearchThePlantAboveSlime() {
		SearchObject seeker = new SearchObject(loc, 
				(new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>
				(loc,jumpingalien.part3.programs.IProgramFactory.Direction.UP)), 
				new Constant<GameObject>(loc,theSlime));
		Object result = seeker.outcome();
//		System.out.println("Test search the plant above Slime: ");
//		System.out.println(theSlime);
//		System.out.println(result);
//		System.out.println();
		assertTrue(result == thePlant);
	}
	
	@Test
	public void testnothingFound() {
		SearchObject seeker = new SearchObject(loc, 
				(new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>
				(loc,jumpingalien.part3.programs.IProgramFactory.Direction.LEFT)), 
				new Constant<GameObject>(loc,theSlime));
		assertTrue(seeker.outcome() == null);
		}
	

}

