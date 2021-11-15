package ArkanoidKursinis;
import java.awt.Graphics2D;
import java.awt.Color;

public class DrawMap {

    public int brickLength;
    public int brickHeight;
    public final int[][] mapArray;

    public DrawMap(int numberRow, int numberCol) {
        mapArray = new int[numberRow][numberCol];
        for(int i = 0; i< mapArray.length; i++) {
            for(int j = 0; j< mapArray[0].length; j++) { // value set to 1 so it means brick was not intersected yet
                mapArray[i][j] = 1;
            }
        }
        brickLength = 530/numberCol;
        brickHeight = 140/numberRow;
    }
    public void DrawBricks(Graphics2D G) {
        for(int i = 0; i< mapArray.length; i++) {
            for(int j = 0; j< mapArray[0].length; j++) {
                if(mapArray[i][j] > 0) {
                    G.setColor(Color.white);
                    G.fillRect(j * brickLength + 80, i * brickHeight + 50, brickLength, brickHeight);

                    G.setColor(Color.black);
                    G.drawRect(j * brickLength + 80, i * brickHeight + 50, brickLength, brickHeight);
                }
            }
        }
    }
    public void setBrickValue(int value, int row, int col) { mapArray[row][col] = value; } // change value of brick to 0 if it hits
}