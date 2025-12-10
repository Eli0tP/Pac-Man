import game_items.GameObject;
import game_items.PacMan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private PacMan pacMan;
    private Points points;
    private Cherry cherry;
    private Wall wall;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
//    private final ArrayList<Points> pointsObject = new ArrayList<Points>();

    public GamePanel() {
        setBackground(Color.BLACK);

        pacMan = new PacMan();
        gameObjects.add(pacMan);

        /* for liste des coordonnes des points */
        int[][] cooPoints = { {120,64},{300,300},{180,500},{64,300} };
        for(int[] cooPoint : cooPoints){
            if(0.2 > Math.random()){
                cherry = new Cherry(cooPoint[0],cooPoint[1]);
                gameObjects.add(cherry);
            } else {
                points = new Points(cooPoint[0],cooPoint[1]);
                gameObjects.add(points);
            }
        }

        int[][] coowalls = { {12,22},{100,200},{180,700},{64,250} };
        for(int[] coowall : coowalls){

            wall = new Wall(coowall[0],coowall[1]);
            gameObjects.add(wall);
        }


        Timer timer = new Timer(16, this);
        timer.start();

        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (GameObject gameObject : gameObjects) { /* Parcours la ArrayList gameObject avec element gameObject de type GameObject */
            gameObject.update();
        }
        repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) { /* Mouvement du Pacman*/
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP: {
                pacMan.keyUp();
                break;
            }
            case KeyEvent.VK_DOWN: {
                pacMan.keyDown();
                break;
            }
            case KeyEvent.VK_LEFT: {
                pacMan.keyLeft();
                break;
            }
            case KeyEvent.VK_RIGHT: {
                pacMan.keyRight();
                break;
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g);
        }

    }
}