package jumpingalien.part3.tests;

import static org.junit.Assert.*;
import static jumpingalien.tests.util.TestUtils.*;
import jumpingalien.model.program.programs.Program;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProgramTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

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
	
	
}
