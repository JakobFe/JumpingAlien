package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import jumpingalien.model.game.*;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.expressions.unaryexpression.*;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.statements.*;
import jumpingalien.model.program.types.ObjectOfWorld;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.Sprite;
import static jumpingalien.tests.util.TestUtils.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ForeachTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sprites = spriteArrayForSize(5, 5);
		loc = new SourceLocation(0,0);
		school1 = new School();
		mazub = new Mazub(new Position(100,100), sprites);
		buzam1 = new Buzam(new Position(150,150), sprites);
		buzam2 = new Buzam(new Position(200,200), sprites);
		plant1 = new Plant(new Position(250,250), sprites);
		plant2 = new Plant(new Position(300,300), sprites);
		plant3 = new Plant(new Position(350,350), sprites);
		plant4 = new Plant(new Position(400,400), sprites);
		shark1 = new Shark(new Position(450,450), sprites);
		shark2 = new Shark(new Position(500,500), sprites);
		slime1 = new Slime(new Position(550,550),sprites,school1);
		slime2 = new Slime(new Position(600,600),sprites,school1);
	}

	@Before
	public void setUp() throws Exception {
		testWorld = new World(100, 10, 10, 1000, 1000, 9, 9);
		testWorld.setMazub(mazub);
		testWorld.addAsGameObject(buzam1);
		testWorld.addAsGameObject(buzam2);
		testWorld.addAsGameObject(plant1);
		testWorld.addAsGameObject(plant2);
		testWorld.addAsGameObject(plant3);
		testWorld.addAsGameObject(plant4);
		testWorld.addAsGameObject(shark1);
		testWorld.addAsGameObject(shark2);
		testWorld.addAsGameObject(slime1);
		testWorld.addAsGameObject(slime2);
		for(int i=0;i<10;i++){
			testWorld.getTileAtTilePos(i, 0).setGeoFeature(Terrain.GROUND);
			testWorld.getTileAtTilePos(0, i).setGeoFeature(Terrain.GROUND);
			testWorld.getTileAtTilePos(9,i).setGeoFeature(Terrain.GROUND);
		}
		
		readOTestProgram = new ReadVariable(loc, "o", new ObjectOfWorld());
		readXTestProgram = new ReadVariable(loc, "x", new jumpingalien.model.program.types.Double());
		getXTestProgram = new GetX(loc, readOTestProgram);
		assignTestProgram = new Assignment("x", new jumpingalien.model.program.types.Double(), 
				getXTestProgram, loc);
		printTestProgram = new Print(readXTestProgram, loc);
		print2TestProgram = new Print(new GetY(null, readXTestProgram), loc);
		seqTestProgram = new SequenceStatement(loc, assignTestProgram,printTestProgram);
		forTestProgram = new Foreach("o", jumpingalien.part3.programs.IProgramFactory.Kind.PLANT,
				new NotBoolean(loc, new IsDead(loc, readOTestProgram)), 
				null,null,seqTestProgram,loc);
		variablesTestProgram = new HashMap<String,Type>();
		variablesTestProgram.put("o", new ObjectOfWorld());
		variablesTestProgram.put("x", new jumpingalien.model.program.types.Double());
		testProgram = new Program(forTestProgram, variablesTestProgram);
		
	}
	
	private static Sprite[] sprites;
	private static SourceLocation loc;
	
	private World testWorld;
	private static Mazub mazub;
	private static Buzam buzam1;
	private static Buzam buzam2;
	private static Plant plant1;
	private static Plant plant2;
	private static Plant plant3;
	private static Plant plant4;
	private static Shark shark1;
	private static Shark shark2;
	private static School school1;
	private static Slime slime1;
	private static Slime slime2;
	
	private Expression readOTestProgram;
	private Expression readXTestProgram;
	private Expression getXTestProgram;
	private Statement forTestProgram;
	private Statement assignTestProgram;
	private Statement printTestProgram;
	private Statement print2TestProgram;
	private Statement seqTestProgram;
	private Map<String,Type> variablesTestProgram;
	private Program testProgram;
	
	
	@Test
	public void testForeachWithoutRestrictionOrSort(){
		// Count the total amount of game objects and tiles in the testWorld.
		Program theProgram = parseProgram(
				"double result:=0;object o;"
				+"foreach (any,o) do "
				+ 	"result:= result + 1;"
				+"done "
				+"wait 20;");
		Buzam theBuzam = new Buzam(new Position(600,40), sprites,theProgram);
		testWorld.addAsGameObject(theBuzam);
		int expected = 0;
		expected += testWorld.getAllGameObjects().size();
		expected += testWorld.getAllTiles().size();
		theProgram.execute(1);
		double result = (Double)theProgram.getGlobalVariables().get("result").getValue();
		assertEquals(expected, result,0.01);
	}
	
	@Test
	public void testForeachWithRestriction(){
		//Count the total amount of game objects in the testWorld
		Program theProgram = parseProgram(
				"double result:=0;object o;"
				+"foreach (any,o) where(!isterrain o) do "
				+ 	"result:= result + 1;"
				+"done "
				+"wait 20;");
		Buzam theBuzam = new Buzam(new Position(600,40), sprites,theProgram);
		testWorld.addAsGameObject(theBuzam);
		int expected = 0;
		expected += testWorld.getAllGameObjects().size();
		theProgram.execute(1);
		double result = (Double)theProgram.getGlobalVariables().get("result").getValue();
		assertEquals(expected, result,0.01);
	}
	
	@Test
	public void testForeachForKindPlant(){
		//Count the sum of the y positions of all plants in the testWorld
		Program theProgram = parseProgram(
				"double y:=0;double result:=0;object o;"
				+"foreach (plant,o) where(!isterrain o) do "
				+	"y:= gety o;"
				+ 	"result:= result + y;"
				+"done "
				+"wait 20;");
		Buzam theBuzam = new Buzam(new Position(600,40), sprites,theProgram);
		testWorld.addAsGameObject(theBuzam);
		int expected = 0;
		for(GameObject plant: testWorld.getAllPlants()){
			expected += plant.getPosition().getDisplayedYPosition();
		}
		theProgram.execute(1);
		double result = (Double)theProgram.getGlobalVariables().get("result").getValue();
		assertEquals(expected, result,0.01);
	}
	
	@Test
	public void testForEachSortingRestrictionAscending(){
		//Iterate over all plants in ascending x position.
		Program theProgram = parseProgram(
				"double x:=0;double result:=0;object o;"
				+"foreach (plant,o) sort getx o ascending do "
				+	"x:= getx o;"
				+ 	"result:= result + x;"
				+"done "
				+"wait 20;");
		Buzam theBuzam = new Buzam(new Position(600,40), sprites,theProgram);
		testWorld.addAsGameObject(theBuzam);
		double valueOfX;
		
		theProgram.execute(0.005);
		// the body of the for is being executed for the plant with the smallest x coordinate
		valueOfX = (Double)theProgram.getGlobalVariables().get("x").getValue();
		assertEquals(250, valueOfX,0.001);
		
		theProgram.execute(0.002);
		// the body of the for is being executed for the second plant
		valueOfX = (Double)theProgram.getGlobalVariables().get("x").getValue();
		assertEquals(300, valueOfX,0.001);
		
		theProgram.execute(0.002);
		// the body of the for is being executed for the third plant
		valueOfX = (Double)theProgram.getGlobalVariables().get("x").getValue();
		assertEquals(350, valueOfX,0.001);
		
		theProgram.execute(0.002);
		// the body of the for is being executed for the fourth plant
		valueOfX = (Double)theProgram.getGlobalVariables().get("x").getValue();
		assertEquals(400, valueOfX,0.001);
	}
	
	@Test
	public void testForEachSortingRestrictionDescending(){
		//Iterate over all plants in ascending x position.
		Program theProgram = parseProgram(
				"double x:=0;double result:=0;object o;"
				+"foreach (plant,o) sort getx o descending do "
				+	"x:= getx o;"
				+ 	"result:= result + x;"
				+"done "
				+"wait 20;");
		Buzam theBuzam = new Buzam(new Position(600,40), sprites,theProgram);
		testWorld.addAsGameObject(theBuzam);
		double valueOfX;
		
		theProgram.execute(0.005);
		// the body of the for is being executed for the plant with the greatest x coordinate
		valueOfX = (Double)theProgram.getGlobalVariables().get("x").getValue();
		assertEquals(400, valueOfX,0.001);
		
		theProgram.execute(0.002);
		// the body of the for is being executed for the second plant
		valueOfX = (Double)theProgram.getGlobalVariables().get("x").getValue();
		assertEquals(350, valueOfX,0.001);
		
		theProgram.execute(0.002);
		// the body of the for is being executed for the third plant
		valueOfX = (Double)theProgram.getGlobalVariables().get("x").getValue();
		assertEquals(300, valueOfX,0.001);
		
		theProgram.execute(0.002);
		// the body of the for is being executed for the fourth plant
		valueOfX = (Double)theProgram.getGlobalVariables().get("x").getValue();
		assertEquals(250, valueOfX,0.001);
	}
	
	@Test
	public void hasAsSubstatementCorrect(){
		assertTrue(testProgram.isWellFormed());
		assertTrue(forTestProgram.hasAsSubStatement(printTestProgram));
		assertTrue(forTestProgram.hasAsSubStatement(assignTestProgram));
	}
	
	@Test
	public void hasAsSubstatementInCorrect(){
		assertTrue(testProgram.isWellFormed());
		assertFalse(forTestProgram.hasAsSubStatement(print2TestProgram));		
	}
	
	@Test
	public void hasActionStatAsSubStatInCorrect(){
		assertTrue(testProgram.isWellFormed());
		assertFalse(forTestProgram.hasActionStatAsSubStat());
	}
	
	@Test
	public void iteratorTest(){
		Buzam theBuzam = new Buzam(new Position(600,40), sprites,testProgram);
		testWorld.addAsGameObject(theBuzam);
		StatementIterator<Statement> iter = forTestProgram.iterator();
		assertTrue(iter.hasNext());
		assertEquals(forTestProgram, iter.next());
		for(int i=0;i<theBuzam.getWorld().getAllPlants().size();i++){
			assertTrue(iter.hasNext());
			assertEquals(assignTestProgram, iter.next());
			assertTrue(iter.hasNext());
			assertEquals(printTestProgram, iter.next());
		}
		assertFalse(iter.hasNext());
		
	}
	
}
