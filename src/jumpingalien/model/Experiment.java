package jumpingalien.model;

public class Experiment {
	public static void main(String[] args){
		World testWorld = new World(5,6,4,100,100,5,3);
		/*for (int row = 0; row < 4; row++){
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
		}*/
		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 10; y++){
				try{
					System.out.println(testWorld.getTileAtTilePos(x, y).getXPosition());
					System.out.println(testWorld.getTileAtTilePos(x, y).getYPosition());
					System.out.println("next tile");
				}catch (IndexOutOfBoundsException e){}
			}
		}
	}
}