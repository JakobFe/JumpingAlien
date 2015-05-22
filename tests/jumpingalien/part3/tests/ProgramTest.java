package jumpingalien.part3.tests;

import static org.junit.Assert.*;
import static jumpingalien.tests.util.TestUtils.*;

import java.util.HashMap;

import jumpingalien.model.game.Mazub;
import jumpingalien.model.game.Position;
import jumpingalien.model.game.Shark;
import jumpingalien.model.game.World;
import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.statements.IfStatement;
import jumpingalien.model.program.statements.Print;
import jumpingalien.model.program.statements.SequenceStatement;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.SourceLocation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProgramTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loc = new SourceLocation(0,0);
		trueConst = new Constant<Boolean>(loc,true);
		doubleConst = new Constant<Double>(loc,8.0);
	}

	@Before
	public void setUp() throws Exception {
		ifBody = new Print(trueConst, loc);
		elseBody = new Print(doubleConst,loc);
		ifStat1 = new IfStatement(trueConst, ifBody, elseBody, loc);
		sequence = new SequenceStatement(loc, ifStat1,ifBody,elseBody);
	}
	
	private static SourceLocation loc;
	private static Constant<Boolean> trueConst;
	private static Constant<Double> doubleConst;
	private IfStatement ifStat1;
	private Statement ifBody;
	private Statement elseBody;
	private Statement sequence;
	
	@Test
	public void isWellFormedCorrectSimpleCase() {
		Program theProgram = parseProgram(
				"double x;"
				+"while true do "
				+ 	"x:=getx self;"
				+ 	"foreach(plant,o) do "
				+ 		"if(!isdead(o)) then print(gethp o); fi "
				+ 	"done "
				+"done");
		assertTrue(theProgram.isWellFormed());
	}

	@Test
	public void isWellFormedCorrectBreakInWhile() {
		Program theProgram = parseProgram(
				"double x;"
				+"while true do "
				+ 	"x:=getx self;"
				+	"break;"
				+ 	"foreach(plant,o) do "
				+ 		"if(!isdead(o)) then print(gethp o); fi "
				+ 	"done "
				+"done");
		assertTrue(theProgram.isWellFormed());
	}
	
	@Test
	public void isWellFormedCorrect2BreaksInWhile() {
		Program theProgram = parseProgram(
				"double x;"
				+"while true do "
				+ 	"x:=getx self;"
				+	"break;"
				+	"break;"
				+ 	"foreach(plant,o) do "
				+ 		"if(!isdead(o)) then print(gethp o); fi "
				+ 	"done "
				+"done");
		assertTrue(theProgram.isWellFormed());
	}
	
	@Test
	public void isWellFormedCorrectBreakInWhileInIf(){
		Program theProgram = parseProgram(
				"double x; "
				+"while true do "
				+ 	"x:=getx self; "
				+ 	"if (x<100) then break; fi "
				+ 	"foreach(plant,o) do "
				+ 		"if(!isdead(o)) then print(gethp o); fi "
				+ 	"done "
				+"done");
		assertTrue(theProgram.isWellFormed());
	}
	
	@Test
	public void isWellFormedCorrectBreakInWhileAfterFor(){
		Program theProgram = parseProgram(
				"double x;"
				+"while true do "
				+ 	"x:=getx self;"
				+ 	"foreach(plant,o) do "
				+ 		"if(!isdead(o)) then print(gethp o); fi "
				+ 	"done "
				+ 	"break; "
				+"done");
		assertTrue(theProgram.isWellFormed());
	}
	
	@Test
	public void isWellFormedCorrectBreakInFor(){
		Program theProgram = parseProgram(
				"double x;"
				+"while true do "
				+ 	"x:=getx self;"
				+ 	"foreach(plant,o) do "
				+ 		"if(!isdead(o)) then print(gethp o); else break; fi "
				+ 	"done "
				+"done");
		assertTrue(theProgram.isWellFormed());
	}
	
	@Test
	public void isWellFormedIncorrectBreakOutsideLoop(){
		Program theProgram = parseProgram(
				"double x;"
				+"x:= getx self; "
				+"if(x>20) then x:=20; fi break;");
		assertFalse(theProgram.isWellFormed());
	}
	
	@Test
	public void isWellFormedIncorrectBreakOutsideWhile(){
		Program theProgram = parseProgram(
				"double x;"
				+"x:= getx self; "
				+"if(x>20) then x:=20; fi "
				+"while(x>50) do x:= x-1; done "
				+"break;");
		assertFalse(theProgram.isWellFormed());
	}
	
	@Test
	public void isWellFormedIncorrectBreakOutsideFor(){
		Program theProgram = parseProgram(
				"double x;object o;"
				+"x:= getx self; "
				+"if(x>20) then x:=20; fi "
				+"foreach(plant,o) do "
				+ 	"print gethp o; "
				+"done "
				+"break;");
		assertFalse(theProgram.isWellFormed());
	}
	
	@Test
	public void isWellFormedCorrectWithActionStatements(){
		Program theProgram = parseProgram(
				"double x;"
				+"x:= getx self; "
				+"if(x>20) then start_run left; else start_jump; fi");
		assertTrue(theProgram.isWellFormed());
	}
	
	@Test
	public void isWellFormedCorrectWithActionStatementsInWhile(){
		Program theProgram = parseProgram(
				"double x;"
				+"x:= getx self; "
				+"while(x>20) do start_run left; done");
		assertTrue(theProgram.isWellFormed());
	}
	
	@Test
	public void isWellFormedInCorrectActionStatementsInFor(){
		Program theProgram = parseProgram(
				"double x; object o; "
				+"x:= getx self; "
				+"foreach(plant,o) do start_run left; done");
		assertFalse(theProgram.isWellFormed());
	}
	
	@Test
	public void isWellFormedInCorrectActionStatementsInForViaIf(){
		Program theProgram = parseProgram(
				"double x;double h; object o; "
				+"x:= getx self; "
				+"foreach(plant,o) do "
				+	"h:=gethp o; "
				+ 	"if(h>50) then start_run left; fi done");
		assertFalse(theProgram.isWellFormed());
	}
	
	@Test
	public void isWellFormedInCorrectActionStatementsInForViaWhile(){
		Program theProgram = parseProgram(
				"double x;double h; object o; "
				+"x:= getx self;"
				+"foreach(plant,o) do "
				+ 	"h:=gethp o; "
				+ 	"while(x>100) do "
				+ 		"x:= x-10;"
				+ 		"h:= h-1; "
				+ 		"if(h>50) then start_run left; fi "
				+ 	"done "
				+"done");
		assertFalse(theProgram.isWellFormed());
	}
	
	@Test
	public void isGameObjectWithProgramCorrentInitialised(){
		Program theProgram = parseProgram(
				"double x;"
				+"while true do "
				+ 	"x:=getx self;"
				+ 	"foreach(plant,o) do "
				+ 		"if(!isdead(o)) then print(gethp o); else break; fi "
				+ 	"done "
				+"done");
		Shark theShark = new Shark(new Position(802,250), spriteArrayForSize(10, 10), theProgram);
		assertEquals(theProgram, theShark.getProgram());
		assertEquals(theProgram.getGameObject(), theShark);
	}
	
	@Test
	public void stopExecutingCorrect(){
		Program theProgram = new Program(sequence, new HashMap<String, Type>());
		Shark theShark = new Shark(new Position(802,250), spriteArrayForSize(10, 10), theProgram);
		World testWorld = new World(50,10,5,500,500,8,1);
		Mazub theMazub = new Mazub(new Position(0,0),1,3,spriteArrayForSize(10, 10));
		
		testWorld.addAsGameObject(theShark);
		testWorld.setMazub(theMazub);
		
		theProgram.execute(0.003);
		assertEquals(elseBody, theProgram.getProgramIterator().next());
	}
	
	@Test
	public void restartExecutingCorrect(){
		Program theProgram = new Program(sequence, new HashMap<String, Type>());
		Shark theShark = new Shark(new Position(802,250), spriteArrayForSize(10, 10), theProgram);
		World testWorld = new World(50,10,5,500,500,8,1);
		Mazub theMazub = new Mazub(new Position(0,0),1,3,spriteArrayForSize(10, 10));
		
		testWorld.addAsGameObject(theShark);
		testWorld.setMazub(theMazub);
		
		theProgram.execute(0.007);
		assertEquals(elseBody, theProgram.getProgramIterator().next());		
	}
	
	@Test
	public void initialiseGlobalVariablesCorrect(){
		Program theProgram = parseProgram(
				"double x;double h; object o; "
				+"x:= getx self;"
				+"foreach(plant,o) do "
				+ 	"h:=gethp o; "
				+ 	"while(x>100) do "
				+ 		"x:= x-10;"
				+ 		"h:= h-1; "
				+ 		"if(h>50) then start_run left; fi "
				+ 	"done "
				+"done");
		assertTrue(theProgram.getGlobalVariables().containsKey("x"));
		assertTrue(theProgram.getGlobalVariables().containsKey("h"));
		assertTrue(theProgram.getGlobalVariables().containsKey("o"));
	}	
}
