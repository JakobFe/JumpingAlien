package jumpingalien.model.program;

import java.util.List;
import java.util.Map;

import jumpingalien.model.game.*;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.SourceLocation;

public class ProgramFactory implements IProgramFactory<Expression,Statement,Type,Program> {

	@Override
	public Expression createReadVariable(String variableName,
			Type variableType, SourceLocation sourceLocation) {
		return new Variable(sourceLocation, variableName, variableType);
	}

	@Override
	public Expression createDoubleConstant(double value,
			SourceLocation sourceLocation) {
		return new Constant<Double>(sourceLocation, value);
	}

	@Override
	public Expression createTrue(SourceLocation sourceLocation) {
		return new Constant<Boolean>(sourceLocation, true);
	}

	@Override
	public Expression createFalse(SourceLocation sourceLocation) {
		return new Constant<Boolean>(sourceLocation, false);
	}

	@Override
	public Expression createNull(SourceLocation sourceLocation) {
		return new Constant<Object>(sourceLocation, null);
	}

	@Override
	public Expression createSelf(SourceLocation sourceLocation) {
		return new Constant<Object>(sourceLocation, this);
	}
	
	@Override
	public Expression createDirectionConstant(
			jumpingalien.part3.programs.IProgramFactory.Direction value,
			SourceLocation sourceLocation) {
		return new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>(sourceLocation, value);
	}

	@Override
	public Expression createAddition(Expression left, Expression right,
			SourceLocation sourceLocation) {
		return new Addition(sourceLocation, left, right);
	}

	@Override
	public Expression createSubtraction(Expression left, Expression right,
			SourceLocation sourceLocation) {
		return new Subtraction(sourceLocation, left, right);
	}

	@Override
	public Expression createMultiplication(Expression left, Expression right,
			SourceLocation sourceLocation) {
		return new Multiplication(sourceLocation, left, right);
	}

	@Override
	public Expression createDivision(Expression left, Expression right,
			SourceLocation sourceLocation) {
		return new Division(sourceLocation, left, right);
	}

	@Override
	public Expression createSqrt(Expression expr, SourceLocation sourceLocation) {
		return new SquareRoot(sourceLocation, expr);
	}

	@Override
	public Expression createRandom(Expression maxValue,
			SourceLocation sourceLocation) {
		return new RandomDouble(sourceLocation, maxValue);
	}

	@Override
	public Expression createAnd(Expression left, Expression right,
			SourceLocation sourceLocation) {
		return new ConditionalAnd(sourceLocation, left, right);
	}

	@Override
	public Expression createOr(Expression left, Expression right,
			SourceLocation sourceLocation) {
		return new ConditionalOr(sourceLocation, left, right);
	}

	@Override
	public Expression createNot(Expression expr, SourceLocation sourceLocation) {
		return new NotBoolean(sourceLocation, expr);
	}

	@Override
	public Expression createLessThan(Expression left, Expression right,
			SourceLocation sourceLocation) {
		return new LessThan(sourceLocation, left, right);
	}

	@Override
	public Expression createLessThanOrEqualTo(Expression left,
			Expression right, SourceLocation sourceLocation) {
		return new LessThanOrEqualTo(sourceLocation, left, right);
	}

	@Override
	public Expression createGreaterThan(Expression left, Expression right,
			SourceLocation sourceLocation) {
		return new GreaterThan(sourceLocation, left, right);
	}

	@Override
	public Expression createGreaterThanOrEqualTo(Expression left,
			Expression right, SourceLocation sourceLocation) {
		return new GreaterThanOrEqualTo(sourceLocation, left, right);
	}

	@Override
	public Expression createEquals(Expression left, Expression right,
			SourceLocation sourceLocation) {
		return new Equals(sourceLocation, left, right);
	}

	@Override
	public Expression createNotEquals(Expression left, Expression right,
			SourceLocation sourceLocation) {
		return new NotEquals(sourceLocation, left, right);
	}

	@Override
	public Expression createGetX(Expression expr, SourceLocation sourceLocation) {
		return new GetX(sourceLocation, expr);
	}

	@Override
	public Expression createGetY(Expression expr, SourceLocation sourceLocation) {
		return new GetY(sourceLocation, expr);
	}

	@Override
	public Expression createGetWidth(Expression expr,
			SourceLocation sourceLocation) {
		return new GetWidth(sourceLocation, expr);
	}

	@Override
	public Expression createGetHeight(Expression expr,
			SourceLocation sourceLocation) {
		return new GetHeight(sourceLocation, expr);
	}

	@Override
	public Expression createGetHitPoints(Expression expr,
			SourceLocation sourceLocation) {
		return new GetHp(sourceLocation, expr);
	}

	@Override
	public Expression createGetTile(Expression x, Expression y,
			SourceLocation sourceLocation) {
		return new GetTile(sourceLocation,x,y,createSelf(sourceLocation));
	}

	@Override
	public Expression createSearchObject(Expression direction,
			SourceLocation sourceLocation) {
		return new SearchObject(sourceLocation,direction,createSelf(sourceLocation));
	}

	@Override
	public Expression createIsMazub(Expression expr,
			SourceLocation sourceLocation) {
		return new IsGameObject<Mazub>(sourceLocation, expr, Mazub.class);
	}

	@Override
	public Expression createIsShark(Expression expr,
			SourceLocation sourceLocation) {
		return new IsGameObject<Shark>(sourceLocation, expr, Shark.class);
	}

	@Override
	public Expression createIsSlime(Expression expr,
			SourceLocation sourceLocation) {
		return new IsGameObject<Slime>(sourceLocation, expr, Slime.class);
	}

	@Override
	public Expression createIsPlant(Expression expr,
			SourceLocation sourceLocation) {
		return new IsGameObject<Plant>(sourceLocation, expr, Plant.class);
	}

	@Override
	public Expression createIsDead(Expression expr,
			SourceLocation sourceLocation) {
		return new IsDead(sourceLocation, expr);
	}

	@Override
	public Expression createIsTerrain(Expression expr,
			SourceLocation sourceLocation) {
		return new IsTerrain(sourceLocation, expr);
	}

	@Override
	public Expression createIsPassable(Expression expr,
			SourceLocation sourceLocation) {
		return new IsPassable(sourceLocation, expr);
	}

	@Override
	public Expression createIsWater(Expression expr,
			SourceLocation sourceLocation) {
		return new IsTerrainOfType(sourceLocation, expr, Terrain.WATER);
	}

	@Override
	public Expression createIsMagma(Expression expr,
			SourceLocation sourceLocation) {
		return new IsTerrainOfType(sourceLocation, expr, Terrain.MAGMA);
	}

	@Override
	public Expression createIsAir(Expression expr, SourceLocation sourceLocation) {
		return new IsTerrainOfType(sourceLocation, expr, Terrain.AIR);
	}

	@Override
	public Expression createIsMoving(Expression expr, Expression direction,
			SourceLocation sourceLocation) {
		return new IsMoving(sourceLocation, expr, direction);
	}

	@Override
	public Expression createIsDucking(Expression expr,
			SourceLocation sourceLocation) {
		return new IsDucking(sourceLocation, expr);
	}

	@Override
	public Expression createIsJumping(Expression expr,
			SourceLocation sourceLocation) {
		return new IsJumping(sourceLocation, expr);
	}

	@Override
	public Statement createAssignment(String variableName, Type variableType,
			Expression value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWhile(Expression condition, Statement body,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createForEach(
			String variableName,
			jumpingalien.part3.programs.IProgramFactory.Kind variableKind,
			Expression where,
			Expression sort,
			jumpingalien.part3.programs.IProgramFactory.SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createIf(Expression condition, Statement ifBody,
			Statement elseBody, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createPrint(Expression value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartRun(Expression direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopRun(Expression direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWait(Expression duration,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSkip(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSequence(List<Statement> statements,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getDoubleType() {
		return Type.DOUBLE;
	}

	@Override
	public Type getBoolType() {
		// TODO Auto-generated method stub
		return Type.BOOLEAN;
	}

	@Override
	public Type getGameObjectType() {
		// TODO Auto-generated method stub
		return Type.OBJECT;
	}

	@Override
	public Type getDirectionType() {
		// TODO Auto-generated method stub
		return Type.DIRECTION;
	}

	@Override
	public Program createProgram(Statement mainStatement,
			Map<String, Type> globalVariables) {
		// TODO Auto-generated method stub
		return null;
	}
}
