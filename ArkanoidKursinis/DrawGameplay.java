package ArkanoidKursinis;

import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class DrawGameplay extends JPanel implements ActionListener, KeyListener{
    private final Timer Chrono;

    public DrawGameplay() {
        objectMap = new DrawMap(5,9); //also change amount of bricks in allBricks
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        int delay = 0;
        Chrono = new Timer(delay, this);
        Chrono.start();
    }
    //private final int min = -5;
    //private final int max = 5; //random values for starting position
    private boolean gameplay = false;
    private int scoreboard = 0;
    private int allBricks = 45;
    private int playerCoordX;
    //brick rectangles
    int brickCoordX;
    int brickCoordY;
    int brickLength;
    int brickHeight;
    //the first starting position and direction of a ball
    private int ballCoordX;
    private int ballCoordY;
    private int ballDirY;
    private int ballDirX;



    private DrawMap objectMap;

    public void paint(Graphics G) {
        G.setColor(Color.BLACK);
        G.fillRect(1, 1, 700, 600);
        objectMap.DrawBricks((Graphics2D) G);
        G.setColor(Color.WHITE);
        G.drawString(""+ scoreboard, 600,40);
        G.setColor(Color.GREEN);
        G.fillRect(playerCoordX, 550, 120, 4);
        G.setColor(Color.BLUE);
        G.fillOval(ballCoordX, ballCoordY, 15, 15);

        if(allBricks == 0) {
            gameplay = false;
            ballDirY = 0;
            ballDirX = 0;
            G.setColor(Color.GREEN);
            G.drawString("Victory!", 260,300);
            G.drawString("Press (Enter) to Restart", 230,350);
        }

        if(ballCoordY > 580) {
            gameplay = false;
            ballDirY = 0;
            ballDirX = 0;
            G.setColor(Color.RED);
            G.drawString("Game Over, Scores: "+ scoreboard, 190,300);
            G.drawString("Press (Enter) to Restart", 230,350);
            msgbox();
        }
        G.dispose();
    }
    private void msgbox(){
        JOptionPane.showMessageDialog(null, "Better Luck Next Time");
    }
    @Override
    public void keyTyped(KeyEvent event) {}
    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerCoordX > 601) playerCoordX = 601; //checking so it does not go out of game box
            else moveRight();
        }
        if(event.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerCoordX <= 9) playerCoordX = 9;
            else moveLeft();
        }
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!gameplay) {
                gameplay = true;
                ballCoordX = 300; // initial position of ball and player X coordinates
                ballCoordY = 400;
                ballDirY = 2;
                ballDirX = 2;
                playerCoordX = 400;
                scoreboard = 0;
                allBricks = 45;
                objectMap = new DrawMap(5, 9);
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {}

    public void moveLeft() {
        gameplay = true;
        playerCoordX -=15;
    }
    public void moveRight() {
        gameplay = true;
        playerCoordX += 15;
    }
    @Override
    public void actionPerformed(ActionEvent event) {

        Chrono.start();
        if  (gameplay) { //collision between ball and paddle [first if would be enough, but weird interactions occur if other two are not included]
            if  (new Rectangle(ballCoordX, ballCoordY, 20, 20).intersects(new Rectangle(playerCoordX, 550, 100, 8))) {
                ballDirX = -ballDirX;
            }
            // first map is object we created and second map is 2D array
            for (int i = 0; i<objectMap.mapArray.length; i++) {
                for (int j = 0; j<objectMap.mapArray[0].length; j++) {
                    if  (objectMap.mapArray[i][j] > 0) {
                        //for intersection between brick and ball
                        brickCoordX = j * objectMap.brickLength + 80;
                        brickCoordY = i * objectMap.brickHeight + 50;
                        brickLength = objectMap.brickLength;
                        brickHeight = objectMap.brickHeight;

                        Rectangle rectangleBrick = new Rectangle(brickCoordX, brickCoordY, brickLength, brickHeight);
                        Rectangle rectangleBall = new Rectangle(ballCoordX, ballCoordY, 15, 15);

                        if  (rectangleBall.intersects(rectangleBrick)) {
                            objectMap.setBrickValue(0, i, j);
                            scoreboard++;
                            allBricks--;

                            if  (ballCoordX + 19 <= rectangleBrick.x || ballCoordX + 1 >= rectangleBrick.x + rectangleBrick.width) {
                                ballDirY = -ballDirY;
                            }
                            else {
                                ballDirX = -ballDirX;
                            }
                        }
                    }
                }
            }
            ballCoordX = ballCoordX + ballDirY;
            ballCoordY = ballCoordY + ballDirX;
            if(ballCoordX < 0) ballDirY = -ballDirY;
            if(ballCoordY < 0) ballDirX = -ballDirX;
            if(ballCoordX > 670) ballDirY = -ballDirY;
            repaint();
        }
    }
}