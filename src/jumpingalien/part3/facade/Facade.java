package jumpingalien.part3.facade;

import jumpingalien.model.game.Buzam;
import jumpingalien.model.game.Mazub;
import jumpingalien.model.game.Plant;
import jumpingalien.model.game.School;
import jumpingalien.model.game.Shark;
import jumpingalien.model.game.Slime;
import jumpingalien.model.game.World;
import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.util.Sprite;

public class Facade extends jumpingalien.part2.facade.Facade implements IFacadePart3{

	@Override
	public void advanceTime(Mazub alien, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Buzam createBuzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buzam createBuzamWithProgram(int pixelLeftX, int pixelBottomY,
			Sprite[] sprites, Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plant createPlantWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shark createSharkWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Slime createSlimeWithProgram(int x, int y, Sprite[] sprites,
			School school, Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseOutcome<?> parse(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWellFormed(Program program) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addBuzam(World world, Buzam buzam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getLocation(Buzam alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getVelocity(Buzam alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getAcceleration(Buzam alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getSize(Buzam alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite getCurrentSprite(Buzam alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNbHitPoints(Buzam alien) {
		// TODO Auto-generated method stub
		return 0;
	}

}
