package jumpingalien.model.program.expressions.unaryexpression;


import jumpingalien.model.game.GameObject;
import jumpingalien.model.game.Tile;
import jumpingalien.model.game.World;
import jumpingalien.model.program.expressions.*;
import jumpingalien.part3.programs.SourceLocation;

public class SearchObject extends UnaryOperator {

	public SearchObject(SourceLocation sourceLocation, Expression direction) {
		super(sourceLocation, direction);
	}
	
	@Override
	public Object outcome() throws IllegalArgumentException{
		GameObject thisObject = getProgram().getGameObject();
		Expression givenDirection = getOperand();
		jumpingalien.model.game.Direction thisDirection = 
				jumpingalien.model.game.Direction.convertDirection(givenDirection);
		World thisWorld = thisObject.getWorld();
		
		double xPos = thisObject.getPosition().getXPosition();
		double yPos = thisObject.getPosition().getYPosition();
		double smallestDistance = Double.POSITIVE_INFINITY;
		
		if(thisDirection == jumpingalien.model.game.Direction.LEFT || 
		   thisDirection == jumpingalien.model.game.Direction.RIGHT){
			return searchLeftRight(thisObject, thisDirection, thisWorld, xPos,
					yPos, smallestDistance);
		}
		
		else if(thisDirection == jumpingalien.model.game.Direction.UP || 
				   thisDirection == jumpingalien.model.game.Direction.DOWN){
			return searchUpDown(thisObject, thisDirection, thisWorld,
					xPos, yPos, smallestDistance);
		}
		else
			throw new IllegalArgumentException();
	}

	private Object searchUpDown(GameObject thisObject,
			jumpingalien.model.game.Direction thisDirection, World thisWorld,
			double xPos, double yPos, double smallestDistance) {
		double currentDistance;
		int searchPos;
		Object nearestObject = null;
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

	private Object searchLeftRight(GameObject thisObject,
			jumpingalien.model.game.Direction thisDirection, World thisWorld,
			double xPos, double yPos, double smallestDistance) {
		double currentDistance;
		int searchPos;
		Object nearestObject = null;
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
}
