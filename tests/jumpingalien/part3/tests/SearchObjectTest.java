package jumpingalien.part3.tests;

import static org.junit.Assert.*;

import jumpingalien.model.game.*;
import jumpingalien.model.program.expressions.unaryexpression.SearchObject;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.statements.*;
import static jumpingalien.tests.util.TestUtils.*;

import org.junit.Before;
import org.junit.Test;

public class SearchObjectTest {

	@Before
	public void setUp() throws Exception {
		testWorld = new World(25, 40, 20, 1000, 1000, 0, 0);
		
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
		testWorld.addAsShark(theShark);
		testWorld.addAsSlime(theSlime);
	}
	
	private World testWorld;
	private Slime theSlime;
	private Shark theShark;
	private Plant thePlant;

	@Test
	public void testSearchTheSlimeToTheLeftOfAlien() {
		// Initialise the program
		Program theProgram = parseProgram("object o; if true then o:= searchobj left; fi");
		// Generate an alien on top of a solid tile
		Buzam alien = new Buzam(new Position(500,250), spriteArrayForSize(10, 10),theProgram);
		testWorld.setBuzam(alien);
		
		Statement temp = ((IfStatement) theProgram.getMainStatement()).getIfBody();
		Assignment ifBody = (Assignment)temp;
		SearchObject seeker = (SearchObject) ifBody.getValue();
		Object result = seeker.outcome();
		assertTrue(result == theSlime);
	}
	
	
	/**
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
	
	*/
}

