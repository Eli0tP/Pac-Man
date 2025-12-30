import game_items.GameObject;
import game_items.PacMan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private PacMan pacMan;
    private Points points;
    private Cherry cherry;
    private Wall wall;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private int score = 0;
    public int gridSize = 32;
//    private final ArrayList<Object> removedObjects = new ArrayList<>();
//    private final ArrayList<Points> pointsObject = new ArrayList<Points>();

    public GamePanel() {
        setBackground(Color.BLACK);

        pacMan = new PacMan();
        gameObjects.add(pacMan);

        String map = "src/map.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(map))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    int x = col * gridSize;
                    int y = row * gridSize;
                    char type = line.charAt(col);

                    if (type == '1') {
                        gameObjects.add(new Wall(x, y));
                    } else if (type == '0') {
                        if (Math.random() < 0.02) {
                            gameObjects.add(new Cherry(x, y));
                        } else {
                            gameObjects.add(new Points(x, y));
                        }
                    } else if (type == '2') {
                    }
                }
                row++;
            }
        } catch (IOException e){
            System.out.println("Le fichier map.txt est intouvable");
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
                    score += p.getScore();
                }
            }else if (obj instanceof Wall) {
                Wall w = (Wall) obj;
                double distance = Math.sqrt(Math.pow((w.getX() - pacX), 2) + Math.pow((w.getY() - pacY), 2));
                if (distance < 32) {
                    pacMan.collisionMur();
                    return;
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
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("SCORE: " + score, 15, 25);
    }
}