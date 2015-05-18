package jumpingalien.part3.facade;

import java.util.Optional;

import jumpingalien.model.exceptions.*;
import jumpingalien.model.game.*;
import jumpingalien.model.program.ProgramFactory;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;
import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.intArray;

public class Facade extends jumpingalien.part2.facade.Facade implements IFacadePart3{

	@Override
	public void advanceTime(Mazub alien, double dt) {
		alien.advanceTime(dt);
	}

	@Override
	public Buzam createBuzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		try{
			return new Buzam(new Position(pixelLeftX,pixelBottomY), sprites);
		}
		catch(IllegalXPositionException |IllegalYPositionException e){
			throw new ModelException("Illegal position!");
		}
	}

	@Override
	public Buzam createBuzamWithProgram(int pixelLeftX, int pixelBottomY,
			Sprite[] sprites, Program program) {
		//return new Buzam (new Position(pixelLeftX, pixelBottomY), sprites,program);
		return createBuzam(pixelLeftX, pixelBottomY, sprites);
	}

	@Override
	public Plant createPlantWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		return new Plant(new Position(x, y), sprites,program);
	}

	@Override
	public Shark createSharkWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		return new Shark(new Position(x, y), sprites,program);
	}

	@Override
	public Slime createSlimeWithProgram(int x, int y, Sprite[] sprites,
			School school, Program program) {
		return new Slime(new Position(x, y), sprites, school,program);
	}

	@Override
	public ParseOutcome<?> parse(String text) {
		IProgramFactory<Expression, Statement, Type, Program> theFactory = 
				new ProgramFactory();
		ProgramParser<Expression, Statement, Type, Program> theParser = 
				new ProgramParser<>(theFactory);
		Optional<Program> parseResult = theParser.parseString(text);
		if(parseResult.isPresent())
			return ParseOutcome.success(parseResult.get());
		else
			return ParseOutcome.failure(theParser.getErrors());
	}

	@Override
	public boolean isWellFormed(Program program) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addBuzam(World world, Buzam buzam) {
		world.setBuzam(buzam);
	}

	@Override
	public int[] getLocation(Buzam alien) {
		return alien.getPosition().toIntArray();
	}

	@Override
	public double[] getVelocity(Buzam alien) {
		return doubleArray(alien.getHorVelocity(),alien.getVertVelocity());
	}

	@Override
	public double[] getAcceleration(Buzam alien) {
		return doubleArray(alien.getHorAcceleration(),alien.getVertAcceleration());
	}

	@Override
	public int[] getSize(Buzam alien) {
		return intArray(alien.getWidth(),alien.getHeight());
	}

	@Override
	public Sprite getCurrentSprite(Buzam alien) {
		alien.updateSpriteIndex();
		return alien.getCurrentSprite();
	}

	@Override
	public int getNbHitPoints(Buzam alien) {
		return alien.getHitPoints();
	}

}
