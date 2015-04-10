package jumpingalien.model.other;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import jumpingalien.model.gameobjects.Mazub;
import jumpingalien.model.worldfeatures.Terrain;
import jumpingalien.model.worldfeatures.Tile;
import jumpingalien.model.worldfeatures.World;

public class Experiment {
	public static void main(String[] args){
		/*
		for (int row = 0; row < 4; row++){
			for (int col = 0; col < 6; col++){
			System.out.println(row);
			System.out.println(col);
			System.out.println(testWorld.getTileAt(row,col).isTargetTile());
			}
		}
		World testWorld2 = new World(20,8,6,1000,1000,7,5);
		int[][] tilePositions = testWorld2.getTilePositionsIn(35,22,62,53);
		for (int index = 0; index < 6; index++){
			System.out.println(tilePositions[index][0]);
			System.out.println(tilePositions[index][1]);
			System.out.println("next tile");
		}
		System.out.println("next test");
		System.out.println();
		int[][] tilePositions2 = testWorld2.getTilePositionsIn(19,19,140,80);
		for (int index = 0; index < 8*6; index++){
			try{
				System.out.println(tilePositions2[index][0]);
				System.out.println(tilePositions2[index][1]);
				System.out.println("next tile");
			}catch (IndexOutOfBoundsException e) {
			    System.err.println("IndexOutOfBoundsException: " + e.getMessage());
			}
		}
		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 10; y++){
				try{
					System.out.println(testWorld.getTileAtTilePos(x, y).getXPosition());
					System.out.println(testWorld.getTileAtTilePos(x, y).getYPosition());
					System.out.println("next tile");
				}catch (IndexOutOfBoundsException e){}
			}
		}
		System.out.println(Terrain.mapValueToTerrain(3));
		Mazub alien = new Mazub(0,0,spriteArrayForSize(10, 10, 30));
		int[][] leftPerimeter = alien.getLeftPerimeter();
		for(int index=0;index<leftPerimeter.length;index++){
			System.out.print(leftPerimeter[index][0]);
			System.out.print(leftPerimeter[index][1]);
			System.out.println();
		}
		Mazub alien = new Mazub(0,0,spriteArrayForSize(10, 10, 30));
		int[][] rightPerimeter = alien.getRightPerimeter();
		for(int index=0;index<rightPerimeter.length;index++){
			System.out.print(rightPerimeter[index][0]);
			System.out.print(rightPerimeter[index][1]);
			System.out.println();
		}*/
		Mazub alien = new Mazub(70,70,spriteArrayForSize(10, 10, 30));
		int[][] lowerPerimeter = alien.getLowerPerimeter();
		for(int index=0;index<lowerPerimeter.length;index++){
			System.out.print(lowerPerimeter[index][0]);
			System.out.print(lowerPerimeter[index][1]);
			System.out.println();
		}
		/*
		Mazub alien = new Mazub(0,0,spriteArrayForSize(10, 10, 30));
		int[][] upperPerimeter = alien.getUpperPerimeter();
		for(int index=0;index<upperPerimeter.length;index++){
			System.out.print(upperPerimeter[index][0]);
			System.out.print(upperPerimeter[index][1]);
			System.out.println();
		}*/
		World testWorld = new World(70,6,4,100,100,5,3);
		testWorld.getTileAtTilePos(0, 0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(1, 0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(2, 0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(3, 0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(4, 0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(5, 0).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(3, 1).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(5, 1).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(0, 1).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(0, 2).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(0, 3).setGeoFeature(Terrain.GROUND);
		testWorld.getTileAtTilePos(4, 1).setGeoFeature(Terrain.WATER);
		testWorld.getTileAtTilePos(3, 1).setGeoFeature(Terrain.GROUND);
		Mazub alien2 = new Mazub(0,0,spriteArrayForSize(50,50));
		testWorld.setMazub(alien2);
		//for(Tile tile: testWorld.getImpassableTiles())
			//System.out.println(tile.toString());
		for(Tile tile: testWorld.getImpassableTiles()){
			if (alien2.isOverlapping(tile)){
				System.out.print("Alien overlaps with ");
				System.out.print(tile.toString());
				System.out.println();
			}
		}
	}
}