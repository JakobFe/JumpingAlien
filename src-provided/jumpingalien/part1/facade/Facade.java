package jumpingalien.part1.facade;


import jumpingalien.model.Mazub;
import jumpingalien.util.Sprite;

public class Facade implements IFacade {

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		// TODO Auto-generated method stub
		return new Mazub(pixelLeftX,pixelBottomY,6,11,1,3);
	}

	@Override
	public int[] getLocation(Mazub alien) {
		// TODO Auto-generated method stub
		int[] result = new int[2];
		result[0] = alien.getXPosition();
		result[1] = alien.getYPosition();
		return result;
	}

	@Override
	public double[] getVelocity(Mazub alien) {
		// TODO Auto-generated method stub
		double[] result = new double[2];
		result[0] = alien.getHorDirection()*alien.getHorVelocity();
		result[1] = alien.getVertDirection()*alien.getVertVelocity();
		return result;
	}

	@Override
	public double[] getAcceleration(Mazub alien) {
		// TODO Auto-generated method stub
		double[] result = new double[2];
		result[0] = alien.getHorAcceleration();
		result[1] = alien.getVertAcceleration();
		return result;
	}

	@Override
	public int[] getSize(Mazub alien) {
		// TODO Auto-generated method stub
		int[] result = new int[2];
		result[0] = alien.getWidth();
		result[1] = alien.getHeight();
		return result;
	}

	@Override
	public Sprite getCurrentSprite(Mazub alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startJump(Mazub alien) {
		// TODO Auto-generated method stub
		alien.startJump();
	}

	@Override
	public void endJump(Mazub alien) {
		// TODO Auto-generated method stub
		alien.endJump();
	}

	@Override
	public void startMoveLeft(Mazub alien) {
		// TODO Auto-generated method stub
		alien.startMoveLeft();
	}

	@Override
	public void endMoveLeft(Mazub alien) {
		// TODO Auto-generated method stub
		alien.endMoveLeft();
	}

	@Override
	public void startMoveRight(Mazub alien) {
		// TODO Auto-generated method stub
		alien.startMoveRight();
	}

	@Override
	public void endMoveRight(Mazub alien) {
		// TODO Auto-generated method stub
		alien.endMoveRight();
	}

	@Override
	public void startDuck(Mazub alien) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDuck(Mazub alien) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void advanceTime(Mazub alien, double dt) {
		// TODO Auto-generated method stub
		alien.advanceTime(dt);
	}
	

}
