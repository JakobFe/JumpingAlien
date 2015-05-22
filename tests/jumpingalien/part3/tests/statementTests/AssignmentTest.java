package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;

import java.util.HashMap;

import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.statements.Assignment;
import jumpingalien.part3.programs.SourceLocation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AssignmentTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loc = new SourceLocation(4, 5);
		doubleConst = new Constant<java.lang.Double>(loc,4.5);
		globals = new HashMap<String, jumpingalien.model.program.types.Type>();
		globals.put("x",new jumpingalien.model.program.types.Double());
	}

	@Before
	public void setUp() throws Exception {
		assign = new Assignment("x",new jumpingalien.model.program.types.Double(),doubleConst, loc);
		theProgram = new Program(assign, globals);
	}
	
	private static SourceLocation loc;
	private static Constant<java.lang.Double> doubleConst;
	private static HashMap<String, jumpingalien.model.program.types.Type> globals;
	private Assignment assign;
	private Program theProgram;

	@Test
	public void setProgramCorrect(){
		assign.setProgram(null);
		assertTrue(assign.getProgram() == null);
		assertTrue(assign.getValue().getProgram() == null);
		assign.setProgram(theProgram);
		assertTrue(assign.getProgram() == theProgram);
		assertTrue(assign.getValue().getProgram() == theProgram);
	}
	
	@Test
	public void executeCorrect(){
		assign.executeSingleStatement();
		assertEquals(4.5, ((java.lang.Double)theProgram.getGlobalVariables().get("x").outcome()),0.000001);
	}
	
	@Test
	public void executeNoEffect(){
		assign.setProgram(null);
		assign.executeSingleStatement();
		assertEquals(0, ((java.lang.Double)theProgram.getGlobalVariables().get("x").outcome()),0.000001);
	}

}
