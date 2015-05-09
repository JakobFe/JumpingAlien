package jumpingalien.model.program.expressions;

import java.util.NoSuchElementException;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.game.Tile;
import jumpingalien.model.game.World;
import jumpingalien.part3.programs.SourceLocation;

public class SearchObject extends UnaryOperator {

	protected SearchObject(SourceLocation sourceLocation, Expression direction, Expression self) {
		super(sourceLocation, direction);
		this.self = self;
	}

	public Expression getSelf() {
		return self;
	}

	private final Expression self;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object outcome() throws NoSuchElementException{
		GameObject thisObject = ((Constant<GameObject>)getSelf()).outcome();
		Constant<jumpingalien.part3.programs.IProgramFactory.Direction> givenDirection =
				(Constant<jumpingalien.part3.programs.IProgramFactory.Direction>)getOperand();
		jumpingalien.model.game.Direction thisDirection = 
				convertDirection(givenDirection);
		World thisWorld = thisObject.getWorld();
		
		double xPos = thisObject.getPosition().getXPosition();
		double yPos = thisObject.getPosition().getYPosition();
		double smallestDistance = Double.POSITIVE_INFINITY;
		Object nearestObject = null;
		double currentDistance;
		int searchPos;
		
		if(thisDirection == jumpingalien.model.game.Direction.LEFT || 
		   thisDirection == jumpingalien.model.game.Direction.RIGHT){
			for(GameObject other: thisWorld.getAllGameObjects()){
				if(occupiesYPosition(thisObject, yPos)){
					if(thisDirection == jumpingalien.model.game.Direction.LEFT){
						currentDistance = xPos - (other.getPosition().getXPosition()+other.getWidth());
					}
					else{
						currentDistance = (other.getPosition().getXPosition()) - (xPos+thisObject.getWidth());
					}
					if(currentDistance > 0 && currentDistance < smallestDistance){
						smallestDistance = currentDistance;
						nearestObject = other;
					}
				}
			}
			if(thisDirection == jumpingalien.model.game.Direction.LEFT)
				searchPos = (int) Math.floor(xPos-1);
			else
				searchPos = (int) Math.floor(xPos + thisObject.getWidth());
			
			while(searchPos > 0 && searchPos < thisWorld.getWorldWidth()){
				Tile tile = thisWorld.getTileAtPos(searchPos, (int) Math.floor(yPos));
				if(!tile.getGeoFeature().isPassable()){
					if(thisDirection == jumpingalien.model.game.Direction.LEFT){
						currentDistance = xPos - (tile.getXPosition()+thisWorld.getTileSize());
					}
					else{
						currentDistance = (tile.getXPosition()) - (xPos+thisObject.getWidth());
					}
					if(currentDistance > 0 && currentDistance < smallestDistance){
						smallestDistance = currentDistance;
						nearestObject = tile;
					}
					break;
				}
				else{
					searchPos += thisWorld.getTileSize() * thisDirection.getFactor();
				}
			}
			return nearestObject;
		}
		
		
		
		
		
		
		else if(thisDirection == jumpingalien.model.game.Direction.UP || 
				   thisDirection == jumpingalien.model.game.Direction.DOWN){
					for(GameObject other: thisWorld.getAllGameObjects()){
						if(occupiesXPosition(thisObject, xPos)){
							if(thisDirection == jumpingalien.model.game.Direction.DOWN){
								currentDistance = yPos - (other.getPosition().getYPosition()+other.getHeight());
							}
							else{
								currentDistance = (other.getPosition().getYPosition()) - (yPos+thisObject.getHeight());
							}
							if(currentDistance > 0 && currentDistance < smallestDistance){
								smallestDistance = currentDistance;
								nearestObject = other;
							}
						}
					}
					if(thisDirection == jumpingalien.model.game.Direction.DOWN)
						searchPos = (int) Math.floor(yPos-1);
					else
						searchPos = (int) Math.floor(yPos + thisObject.getHeight());
					
					while(searchPos > 0 && searchPos < thisWorld.getWorldHeight()){
						Tile tile = thisWorld.getTileAtPos((int) Math.floor(xPos), searchPos);
						if(!tile.getGeoFeature().isPassable()){
							if(thisDirection == jumpingalien.model.game.Direction.DOWN){
								currentDistance = yPos - (tile.getYPosition()+thisWorld.getTileSize());
							}
							else{
								currentDistance = (tile.getYPosition()) - (yPos+thisObject.getHeight());
							}
							if(currentDistance > 0 && currentDistance < smallestDistance){
								smallestDistance = currentDistance;
								nearestObject = tile;
							}
							break;
						}
						else{
							searchPos += thisWorld.getTileSize() * thisDirection.getFactor();
						}
					}
					return nearestObject;
		}
		else
			throw new NoSuchElementException();
	}
	
	private boolean occupiesYPosition(GameObject gameObject, double yPos){
		return (yPos >= gameObject.getPosition().getYPosition() &&
			    yPos <= (gameObject.getPosition().getYPosition() + gameObject.getHeight()));
	}
	
	private boolean occupiesXPosition(GameObject gameObject, double xPos){
		return (xPos >= gameObject.getPosition().getXPosition() &&
			    xPos <= (gameObject.getPosition().getXPosition() + gameObject.getWidth()));
	}

	@Override
	public String getOperatorSymbol() {
		return "searchobj";
	}
	
	public jumpingalien.model.game.Direction convertDirection(
			Constant<jumpingalien.part3.programs.IProgramFactory.Direction> dir)
			throws IllegalArgumentException{
		// TO DO
		if (dir.outcome() == jumpingalien.part3.programs.IProgramFactory.Direction.LEFT)
			return jumpingalien.model.game.Direction.LEFT;
		else if (dir.outcome() == jumpingalien.part3.programs.IProgramFactory.Direction.RIGHT)
			return jumpingalien.model.game.Direction.RIGHT;
		else if (dir.outcome() == jumpingalien.part3.programs.IProgramFactory.Direction.DOWN)
			return jumpingalien.model.game.Direction.DOWN;
		else if (dir.outcome() == jumpingalien.part3.programs.IProgramFactory.Direction.UP)
			return jumpingalien.model.game.Direction.UP;
		else
			throw new IllegalArgumentException();
	}

	
}
