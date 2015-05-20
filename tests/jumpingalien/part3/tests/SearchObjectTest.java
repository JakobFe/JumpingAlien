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
		
		// Generate an impassable tile above the location where the buzam will be
		testWorld.getTileAtPos(500,980).setGeoFeature(Terrain.GROUND);
		
		// Generate a plant above the slime.
		thePlant = new Plant(new Position(60,400),spriteArrayForSize(10, 10));
		
		// Generate a Mazub, which will be unused.
		theMazub = new Mazub(new Position(0,0),spriteArrayForSize(10, 10));
		
		// Add the game objects to the world.
		testWorld.addAsPlant(thePlant);
		testWorld.addAsShark(theShark);
		testWorld.addAsSlime(theSlime);
		testWorld.setMazub(theMazub);
	}
	
	private World testWorld;
	private Slime theSlime;
	private Shark theShark;
	private Plant thePlant;
	private Mazub theMazub;

	@Test
	public void testSearchTheSlimeToTheLeftOfAlien() {
		// Initialise the program
		Program theProgram = parseProgram("object o; if true then o:= searchobj left; fi");
		
		// Generate a Buzam on top of a solid tile with theProgram as program
		Buzam alien = new Buzam(new Position(500,250), spriteArrayForSize(10, 10),theProgram);
		testWorld.setBuzam(alien);
		
		Statement temp = ((IfStatement) theProgram.getMainStatement()).getIfBody();
		Assignment ifBody = (Assignment)temp;
		SearchObject seeker = (SearchObject) ifBody.getValue();
		Object result = seeker.outcome();
		assertTrue(result == theSlime);
	}
	
	
	
	@Test
	public void testSearchTheSharkToTheRightOfAlien() {
		// Initialise the program
		Program theProgram = parseProgram("object o; if true then o:= searchobj right; fi");
		
		// Generate a Buzam on top of a solid tile with theProgram as program
		Buzam alien = new Buzam(new Position(500,250), spriteArrayForSize(10, 10),theProgram);
		testWorld.setBuzam(alien);
		
		Statement temp = ((IfStatement) theProgram.getMainStatement()).getIfBody();
		Assignment ifBody = (Assignment)temp;
		SearchObject seeker = (SearchObject) ifBody.getValue();
		Object result = seeker.outcome();
		assertTrue(result == theShark);
	}
	

	@Test
	public void testSearchTheGroundBelowShark() {
		// Initialise the program
		Program theProgram = parseProgram("object o; if true then o:= searchobj down; fi");
		
		// replace theShark by a shark with theProgram
		Shark newShark = new Shark(theShark.getPosition(), spriteArrayForSize(10, 10),theProgram);
		testWorld.addAsShark(newShark);
		
		Statement temp = ((IfStatement) theProgram.getMainStatement()).getIfBody();
		Assignment ifBody = (Assignment)temp;
		SearchObject seeker = (SearchObject) ifBody.getValue();
		Object result = seeker.outcome();
				
		assertTrue(result == testWorld.getTileAtPos(802, 2));
	}
	
	@Test
	public void testSearchThePlantAboveSlime() {
		// Initialise the program
		Program theProgram = parseProgram("object o; if true then o:= searchobj up; fi");
		
		// replace theSlime by a slime with theProgram
		Slime newSlime = new Slime(theSlime.getPosition(), spriteArrayForSize(10, 10),
								   theSlime.getSchool(),theProgram);
		testWorld.addAsSlime(newSlime);
		
		Statement temp = ((IfStatement) theProgram.getMainStatement()).getIfBody();
		Assignment ifBody = (Assignment)temp;
		SearchObject seeker = (SearchObject) ifBody.getValue();
		Object result = seeker.outcome();

		
		assertTrue(result == thePlant);
	}
	
	@Test
	public void testnothingFoundLeftFromSlime() {
		// Initialise the program
		Program theProgram = parseProgram("object o; if true then o:= searchobj left; fi");
		
		// replace theSlime by a slime with theProgram
		Slime newSlime = new Slime(theSlime.getPosition(), spriteArrayForSize(10, 10),
								   theSlime.getSchool(),theProgram);
		testWorld.addAsSlime(newSlime);
		
		Statement temp = ((IfStatement) theProgram.getMainStatement()).getIfBody();
		Assignment ifBody = (Assignment)temp;
		SearchObject seeker = (SearchObject) ifBody.getValue();
		assertTrue(seeker.outcome() == null);
	}
	
	@Test
	public void testNothingFoundAboveShark() {
		// Initialise the program
		Program theProgram = parseProgram("object o; if true then o:= searchobj up; fi");
		
		// replace theShark by a shark with theProgram
		Shark newShark = new Shark(theShark.getPosition(), spriteArrayForSize(10, 10),theProgram);
		testWorld.addAsShark(newShark);
		
		Statement temp = ((IfStatement) theProgram.getMainStatement()).getIfBody();
		Assignment ifBody = (Assignment)temp;
		SearchObject seeker = (SearchObject) ifBody.getValue();
		Object result = seeker.outcome();
				
		assertTrue(result == null);
	}
	
}

