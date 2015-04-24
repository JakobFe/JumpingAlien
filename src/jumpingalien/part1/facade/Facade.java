package jumpingalien.part1.facade;


import jumpingalien.model.Direction;
import jumpingalien.model.Mazub;
import jumpingalien.model.exceptions.*;
import jumpingalien.util.*;
import static jumpingalien.tests.util.TestUtils.*;

public class Facade implements IFacade {

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		// TODO Auto-generated method stub
		try{return new Mazub(pixelLeftX,pixelBottomY,sprites);}
		catch (IllegalXPositionException exc){
			throw new ModelException("Illegal X Position!");}
		catch (IllegalYPositionException exc){
			throw new ModelException("Illegal Y Position!");}
	}

	@Override
	public int[] getLocation(Mazub alien) {
		// TODO Auto-generated method stub
		return intArray(alien.getPosition().getDisplayedXPosition(),
				alien.getPosition().getDisplayedYPosition());
	}

	@Override
	public double[] getVelocity(Mazub alien) {
		// TODO Auto-generated method stub
		return doubleArray(alien.getHorDirection().getFactor()*alien.getHorVelocity(),
			   alien.getVertDirection().getFactor()*alien.getVertVelocity());
	}

	@Override
	public double[] getAcceleration(Mazub alien) {
		// TODO Auto-generated method stub
		return doubleArray(alien.getHorDirection().getFactor()*alien.getHorAcceleration(),
						   alien.getVertAcceleration());
	}

	@Override
	public int[] getSize(Mazub alien) {
		// TODO Auto-generated method stub
		return intArray(alien.getWidth(),alien.getHeight());
	}

	@Override
	public Sprite getCurrentSprite(Mazub alien) {
		// TODO Auto-generated method stub
		alien.updateSpriteIndex();
		return alien.getCurrentSprite();
	}

	@Override
	public void startJump(Mazub alien) {
		// TODO Auto-generated method stub
		try{
			alien.startJump();}
		catch (IllegalJumpInvokeException exc){
			new ModelException("Illegal Jumping Invoke!");
			System.out.println("Illegal Jumping Invoke!");
		}
	}

	@Override
	public void endJump(Mazub alien) {
		// TODO Auto-generated method stub
		alien.endJump();
	}

	@Override
	public void startMoveLeft(Mazub alien) {
		// TODO Auto-generated method stub
		alien.startMove(Direction.LEFT);
	}

	@Override
	public void endMoveLeft(Mazub alien) {
		// TODO Auto-generated method stub
		if (alien.isMoving(Direction.LEFT))
			alien.endMove(Direction.LEFT);
	}

	@Override
	public void startMoveRight(Mazub alien) {
		// TODO Auto-generated method stub
		alien.startMove(Direction.RIGHT);
	}

	@Override
	public void endMoveRight(Mazub alien) {
		// TODO Auto-generated method stub
		if (alien.isMoving(Direction.RIGHT))
			alien.endMove(Direction.RIGHT);
	}

	@Override
	public void startDuck(Mazub alien) {
		// TODO Auto-generated method stub
		alien.startDuck();
	}

	@Override
	public void endDuck(Mazub alien) {
		// TODO Auto-generated method stub
		alien.endDuck();
	}

	@Override
	public void advanceTime(Mazub alien, double dt) {
		// TODO Auto-generated method stub
		try{
		alien.advanceTime(dt);}
		catch (IllegalXPositionException exc){
			new ModelException("Illegal X Position!");
			System.out.println("Illegal X Position!");
			}
		catch (IllegalYPositionException exc){
			new ModelException("Illegal Y Position!");
			System.out.println("Illegal Y Position!");
			}
		catch (IllegalTimeIntervalException exc){
			throw new ModelException("Computing time to long!");
		}
	}
	
}
