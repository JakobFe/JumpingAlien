package jumpingalien.model;
import jumpingalien.model.*;

public class Experiment {
	public static void main(String[] args){
		World testWorld = new World(5,6,4,100,100,5,3);
		for (int row = 0; row < 4; row++){
			for (int col = 0; col < 6; col++){
			System.out.println(row);
			System.out.println(col);
			System.out.println(testWorld.getTileAt(row,col).isTargetTile());
			}
		}
	
	}
}
