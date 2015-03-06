package jumpingalien.model;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * This test case contains tests for each public method of the class Mazub,
 * excluding inspectors.
 * @author	Jakob Festraets, Vincent Kemps
 */ 

public class MazubTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	/**
	 * A variable referencing a Mazub at position(0,0), with 
	 * default settings for all other attributes.
	 */
	private Mazub Mazub_POS_0_0;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Mazub_POS_0_0 = new Mazub(0,0,6,11,1,3,null);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void isValidXPosition_TrueCase() {
		assertTrue(Mazub.isValidXPosition(500));
	}
	
	@Test
	public void isValidXPosition_PositionNegative() {
		assertFalse(Mazub.isValidXPosition(-1));
	}
	
	@Test
	public void isValidXPosition_PositionTooLarge() {
		assertFalse(Mazub.isValidXPosition(2000));
	}
	
	@Test
	public void setXPosition_EffectiveCase() {
		Mazub_POS_0_0.setXPosition(20);
		assertEquals(20,Mazub_POS_0_0.getXPosition());
	}
	
	@Test(expected = IllegalXPositionException.class)
	public void setXPosition_IllegalCase() {
		Mazub_POS_0_0.setXPosition(-20);
	}
	
	@Test
	public void isValidEffectiveXPos_TrueCase() {
		assertTrue(Mazub.isValidEffectiveXPos(129.457896));
	}
	
	@Test
	public void isValidEffectiveXPos_PositionNegative() {
		assertFalse(Mazub.isValidEffectiveXPos(-5.1235459));
	}
	
	@Test
	public void isValidEffectiveXPos_PositionTooLarge() {
		assertFalse(Mazub.isValidEffectiveXPos(1500.4846541));
	}
	
	@Test
	public void setEffectiveXPos_EffectiveCase() {
		Mazub_POS_0_0.setEffectiveXPos(20.458);
		assertEquals(20.458,Mazub_POS_0_0.getEffectiveXPos(),0.0001);		
	}
	
	@Test(expected = IllegalXPositionException.class)
	public void setEffectiveXPos_IllegalCase() {
		Mazub_POS_0_0.setEffectiveXPos(-20.458);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void isValidYPosition_TrueCase() {
		assertTrue(Mazub.isValidYPosition(500));
	}
	
	@Test
	public void isValidYPosition_PositionNegative() {
		assertFalse(Mazub.isValidYPosition(-1));
	}
	
	@Test
	public void isValidYPosition_PositionTooLarge() {
		assertFalse(Mazub.isValidYPosition(2000));
	}
	
	@Test
	public void setYPosition_EffectiveCase() {
		Mazub_POS_0_0.setYPosition(20);
		assertEquals(20,Mazub_POS_0_0.getYPosition());
	}
	
	@Test(expected = IllegalYPositionException.class)
	public void setYPosition_IllegalCase() {
		Mazub_POS_0_0.setYPosition(-20);
	}
	
	@Test
	public void isValidEffectiveYPos_TrueCase() {
		assertTrue(Mazub.isValidEffectiveYPos(129.457896));
	}
	
	@Test
	public void isValidEffectiveYPos_PositionNegative() {
		assertFalse(Mazub.isValidEffectiveYPos(-5.1235459));
	}
	
	@Test
	public void isValidEffectiveYPos_PositionTooLarge() {
		assertFalse(Mazub.isValidEffectiveYPos(1500.4846541));
	}
	
	@Test
	public void setEffectiveYPos_EffectiveCase() {
		Mazub_POS_0_0.setEffectiveYPos(20.458);
		assertEquals(20.458,Mazub_POS_0_0.getEffectiveYPos(),0.0001);		
	}
	
	@Test(expected = IllegalYPositionException.class)
	public void setEffectiveYPos_IllegalCase() {
		Mazub_POS_0_0.setEffectiveYPos(-20.458);
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void isValidWidth_TrueCase() {
		assertTrue(Mazub.isValidWidth(10));
	}
	
	@Test
	public void isValidWidth_WidthNegative() {
		assertFalse(Mazub.isValidWidth(-10));
	}
	
	@Test
	public void setWidth_EffectiveCase() {
		Mazub_POS_0_0.setWidth(20);
		assertEquals(20,Mazub_POS_0_0.getWidth());		
	}
	
	@Test(expected = IllegalWidthException.class)
	public void setWidth_IllegalCase() {
		Mazub_POS_0_0.setWidth(-20);
	}
	
	@Test
	public void isValidHeight_TrueCase() {
		assertTrue(Mazub.isValidHeight(10));
	}
	
	@Test
	public void isValidHeight_HeightNegative() {
		assertFalse(Mazub.isValidHeight(-10));
	}
	
	@Test
	public void setHeight_EffectiveCase() {
		Mazub_POS_0_0.setHeight(20);
		assertEquals(20,Mazub_POS_0_0.getHeight());		
	}
	
	@Test(expected = IllegalHeightException.class)
	public void setHeight_IllegalCase() {
		Mazub_POS_0_0.setHeight(-20);
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void isValidHorDirection_TrueCase() {
		assertTrue(Mazub.isValidHorDirection(-1));
	}
	
	@Test
	public void isValidHorDirection_FalseCase() {
		assertFalse(Mazub.isValidHorDirection(2));
	}
	
	@Test
	public void setHorDirection_SingleCase() {
		Mazub_POS_0_0.setHorDirection(1);
		assertEquals(1,Mazub_POS_0_0.getHorDirection());		
	}
	
	@Test
	public void isValidVertDirection_TrueCase() {
		assertTrue(Mazub.isValidVertDirection(0));
	}
	
	@Test
	public void isValidVertDirection_FalseCase() {
		assertFalse(Mazub.isValidVertDirection(2));
	}
	
	@Test
	public void setVertDirection_SingleCase() {
		Mazub_POS_0_0.setVertDirection(1);
		assertEquals(1,Mazub_POS_0_0.getVertDirection());		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void isValidInitHorVelocity_TrueCase() {
		assertTrue(Mazub.isValidInitHorVelocity(1.2456));
	}
	
	@Test
	public void isValidInitHorVelocity_FalseCase() {
		assertFalse(Mazub.isValidInitHorVelocity(0.999));
	}
	
	@Test
	public void canHaveAsMaxHorVelocity_TrueCase() {
		assertTrue(Mazub_POS_0_0.canHaveAsMaxHorVelocity(10.2456));
	}
	
	@Test
	public void canHaveAsHorVelocity_TrueCase() {
		assertTrue(Mazub_POS_0_0.canHaveAsHorVelocity(2.4568));
	}
	
	@Test
	public void setHorVelocity_EffectiveCase() {
		Mazub_POS_0_0.setHorVelocity(1.9546);
		assertEquals(1.9546,Mazub_POS_0_0.getHorVelocity(),0.00001);		
	}
	
	@Test
	public void setHorAcceleration_EffectiveCase() {
		Mazub_POS_0_0.setHorAcceleration(Mazub.getMaxHorAcceleration());
		assertEquals(Mazub.getMaxHorAcceleration(),Mazub_POS_0_0.getHorAcceleration(),0.01);		
	}
	
	@Test
	public void setHorAcceleration_NonEffectiveCase() {
		Mazub_POS_0_0.setHorAcceleration(Mazub.getMaxHorAcceleration()+5);
		assertEquals(0,Mazub_POS_0_0.getHorAcceleration(),0.001);		
	}
	
	@Test
	public void isValidHorAcceleration_TrueCase() {
		assertTrue(Mazub.isValidHorAcceleration(0.9));
	}
	
	@Test
	public void isValidHorAcceleration_FalseCase() {
		assertFalse(Mazub.isValidHorAcceleration(-123));
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
		
}