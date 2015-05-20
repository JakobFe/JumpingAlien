package jumpingalien.part1.facade;


import jumpingalien.model.exceptions.*;
import jumpingalien.model.game.Direction;
import jumpingalien.model.game.Mazub;
import jumpingalien.model.game.Position;
import jumpingalien.util.*;
import static jumpingalien.tests.util.TestUtils.*;

public class Facade implements IFacade {

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		try{return new Mazub(new Position(pixelLeftX,pixelBottomY),sprites);}
		catch (IllegalXPositionException exc){
			throw new ModelException("Illegal X Position!");}
		catch (IllegalYPositionException exc){
			throw new ModelException("Illegal Y Position!");}
	}

	@Override
	public int[] getLocation(Mazub alien) {
		try {
			return intArray(alien.getPosition().getDisplayedXPosition(),
					alien.getPosition().getDisplayedYPosition());
		} catch (NullPointerException e) {
			System.out.println("Modelexception!");
			throw new ModelException("This Mazub is terminated!");
		}
	}

	@Override
	public double[] getVelocity(Mazub alien) {
		return doubleArray(alien.getHorDirection().getFactor()*alien.getHorVelocity(),
			   alien.getVertDirection().getFactor()*alien.getVertVelocity());
	}

	@Override
	public double[] getAcceleration(Mazub alien) {
		return doubleArray(alien.getHorDirection().getFactor()*alien.getHorAcceleration(),
						   alien.getVertAcceleration());
	}

	@Override
	public int[] getSize(Mazub alien) {
		return intArray(alien.getWidth(),alien.getHeight());
	}

	@Override
	public Sprite getCurrentSprite(Mazub alien) {
		alien.updateSpriteIndex();
		return alien.getCurrentSprite();
	}

	@Override
	public void startJump(Mazub alien) {
		if(!alien.isDead()){
			try{
				alien.startJump();}
			catch (IllegalJumpInvokeException exc){
				new ModelException("Illegal Jumping Invoke!");
				System.out.println("Illegal Jumping Invoke!");
			}
		}
	}

	@Override
	public void endJump(Mazub alien) {
		try{
			alien.endJump();
		}
		catch(IllegalStateException e){
			new ModelException("Can not end jumping!");
		}
	}

	@Override
	public void startMoveLeft(Mazub alien) {
		if(!alien.isDead())
			alien.startMove(Direction.LEFT);
	}

	@Override
	public void endMoveLeft(Mazub alien) {
		if (alien.isMoving(Direction.LEFT))
			alien.endMove(Direction.LEFT);
		if(!alien.isMoving(Direction.RIGHT))
			alien.endMove(Direction.LEFT);
	}

	@Override
	public void startMoveRight(Mazub alien) {
		if(!alien.isDead())
			alien.startMove(Direction.RIGHT);
	}

	@Override
	public void endMoveRight(Mazub alien) {
		//if (alien.isMoving(Direction.RIGHT))
		//alien.endMove(Direction.RIGHT);
		if(!alien.isMoving(Direction.LEFT))
			alien.endMove(Direction.RIGHT);
	}

	@Override
	public void startDuck(Mazub alien) {
		if(!alien.isDead())
			alien.startDuck();
	}

	@Override
	public void endDuck(Mazub alien) {
		alien.endDuck();
	}

	@Override
	public void advanceTime(Mazub alien, double dt) {
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
