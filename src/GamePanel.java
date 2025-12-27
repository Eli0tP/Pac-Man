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
//    private final ArrayList<Object> removedObjects = new ArrayList<>();
//    private final ArrayList<Points> pointsObject = new ArrayList<Points>();

    public GamePanel() {
        setBackground(Color.BLACK);

        pacMan = new PacMan();
        gameObjects.add(pacMan);

        /* for liste des coordonnes des points */
        int[][] cooPoints = { {64,32},{300,300},{180,500},{64,300} };
        for(int[] cooPoint : cooPoints){
            if(0.2 > Math.random()){
                cherry = new Cherry(cooPoint[0],cooPoint[1]);
                gameObjects.add(cherry);
            } else {
                points = new Points(cooPoint[0],cooPoint[1]);
                gameObjects.add(points);
            }
        }

        int[][] coowalls = { {256,32},{100,200},{180,700},{64,250} };
        for(int[] coowall : coowalls){

            wall = new Wall(coowall[0],coowall[1]);
            gameObjects.add(wall);
        }


        Timer timer = new Timer(16, this);
        timer.start();

        setFocusable(true);
        addKeyListener(this);
    }

    //    public void distRemove(ArrayList<GameObject> liste , int xPac, int yPac, int xPoint, int yPoint) {
//        double distance = Math.sqrt(Math.pow((xPoint - xPac), 2) + Math.pow((yPoint - yPac), 2));
//        if (distance < (double) 16){
//            liste.remove();
//        }
//    }

    public void collision() {
        int pacX = pacMan.getX();
        int pacY = pacMan.getY();

        ArrayList<GameObject> objectsToRemove = new ArrayList<>();

        for (GameObject obj : gameObjects) {
            if (obj instanceof Points) {
                Points p = (Points) obj;
                double distance = Math.sqrt(Math.pow((p.getX() - pacX), 2) + Math.pow((p.getY() - pacY), 2));

                if (distance < 20) {
                    objectsToRemove.add(obj);
                }
            }else if (obj instanceof Wall) {
                Wall w = (Wall) obj;
                // On calcule la distance entre le centre de Pac-Man et le centre du mur
                double distance = Math.sqrt(Math.pow((w.getX() - pacX), 2) + Math.pow((w.getY() - pacY), 2));

                // Si la distance est inférieure à 32, on est "dans" le mur
                if (distance < 32) { // 31 au lieu de 32 pour laisser une toute petite marge de sécurité
                    pacMan.collisionMur();
                    return; // On arrête la boucle dès qu'un mur est touché
                }
            }
        }
        gameObjects.removeAll(objectsToRemove);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (GameObject gameObject : gameObjects) { /* Parcours la ArrayList gameObject avec element gameObject de type GameObject */
            gameObject.update();
        }
        collision();

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