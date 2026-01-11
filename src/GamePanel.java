import game_items.GameObject;
import game_items.PacMan;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private PacMan pacMan;
    private Points points;
    private Cherry cherry;
    private Wall wall;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private int score = 0;
    public int gridSize = 32;
    private ArrayList<Teleporter> teleporters = new ArrayList<>();
    private int pointsRestants = 0;
    private boolean gameOver = false;
    private boolean victoire = false;
//    private final ArrayList<Object> removedObjects = new ArrayList<>();
//    private final ArrayList<Points> pointsObject = new ArrayList<Points>();

    public GamePanel() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        setBackground(Color.BLACK);

        pacMan = new PacMan();
        gameObjects.add(pacMan);
        Ghost ghost1 = new Ghost(352, 352,100, 0, gameObjects, pacMan, "resources/Pac_Man_Ghost.png");
        gameObjects.add(ghost1);
        Ghost ghost2 = new Ghost(384, 352,100, 50, gameObjects, pacMan, "resources/Pac_Man_Ghost1.png");
        gameObjects.add(ghost2);
        Ghost ghost3 = new Ghost(416, 352, 100, 50, gameObjects,pacMan, "resources/Pac_Man_Ghost2.png");
        gameObjects.add(ghost3);
        Ghost ghost4 = new Ghost(448, 352, 100, 75, gameObjects,pacMan, "resources/Pac_Man_Ghost3.png");
        gameObjects.add(ghost4);
        Musique.musiqueDebut();


        String map = "src/map.txt";
        int countTeleporter = 0;
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
                        pointsRestants ++;
                        if (Math.random() < 0.02) {
                            gameObjects.add(new Cherry(x, y));
                        } else {
                            gameObjects.add(new Points(x, y));
                        }
                    } else if (type == '2') {
                        Teleporter tp = new Teleporter(x, y);
                        gameObjects.add(tp);
                        teleporters.add(tp);
                        countTeleporter++;
                    } else if (type == '3') {
                        gameObjects.add(new SuperPoint(x, y));
                        pointsRestants++;
                    } else if (type == '4') {
                        gameObjects.add(new WallFantome(x, y));
                    }
                }
                row++;
            }if (countTeleporter != 0 && countTeleporter != 2) {
                System.out.println("La map contient " + countTeleporter + " téléporteur(s). Il en faut exactement 0 ou 2.");
                System.exit(1);
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

    public void collision() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        int pacX = pacMan.getX();
        int pacY = pacMan.getY();

        ArrayList<GameObject> objectsToRemove = new ArrayList<>();

        for (GameObject obj : gameObjects) {
            if (obj instanceof Points) {
                Points p = (Points) obj;
                double distance = Math.sqrt(Math.pow((p.getX() - pacX), 2) + Math.pow((p.getY() - pacY), 2));

                if (distance < 20) {
                    objectsToRemove.add(obj);
                    if (obj instanceof Cherry){
                        Musique.sonMangerCerise();
                    }
                    score += p.getScore();
                    pointsRestants--;
                    if (pointsRestants == 0) {victoire = true;}
                }
            }
            else if (obj instanceof Wall) {
                Wall w = (Wall) obj;
                double distance = Math.sqrt(Math.pow((w.getX() - pacX), 2) + Math.pow((w.getY() - pacY), 2));
                if (distance < 32) {
                    pacMan.collisionMur();
                    return;
                }
            }
            else if (obj instanceof Teleporter) {
            Teleporter tp = (Teleporter) obj;
            double distance = Math.sqrt(Math.pow((tp.getX() - pacX), 2) + Math.pow((tp.getY() - pacY), 2));
            if (distance < 16) {
                Teleporter tpDestination = (teleporters.get(0) == tp) ? teleporters.get(1) : teleporters.get(0);
                if (tp.getX() < tpDestination.getX()) {
                    pacMan.setX(tpDestination.getX() - gridSize);
                } else {
                    pacMan.setX(tpDestination.getX() + gridSize);
                }
                pacMan.setY(tpDestination.getY());
                return;
            }
        }
            if (obj instanceof SuperPoint) {
                SuperPoint sPoint = (SuperPoint) obj;
                double dist = Math.sqrt(Math.pow(sPoint.getX() - pacX, 2) + Math.pow(sPoint.getY() - pacY, 2));
                if (dist < 20) {
                    objectsToRemove.add(obj);
                    pacMan.setSuper(true);
                    Musique.sonMangerCerise();
                    score += sPoint.getScore();
                }
            }
            else if (obj instanceof Ghost) {
                Ghost g = (Ghost) obj;
                double dist = Math.sqrt(Math.pow(g.getX() - pacX, 2) + Math.pow(g.getY() - pacY, 2));
                if (dist < 28) {
                    if (pacMan.estSuper()) {
                        Musique.sonMangerFantome();
                        g.Mort();
                        g.resetPosition();
                        score += 200;
                    } else {
                        Musique.sonMort();
                        gameOver = true;
                        System.out.println("GAME OVER"); /*  ICI mettre une (image) de fin */
                        System.out.println("Votre score était de " + score);

                    }
                }
            }
        }
        gameObjects.removeAll(objectsToRemove);
    }

    private void recommencerPartie() {
        this.score = 0;
        this.gameOver = false;
        pacMan.resetPosition();

        for (GameObject obj : gameObjects) {
            if (obj instanceof Ghost) {
                ((Ghost) obj).resetPosition();
            }
        }
        try { Musique.musiqueDebut(); } catch(Exception e){}
        repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !victoire) {
            for (GameObject gameObject : gameObjects) { /* Parcours la ArrayList gameObject avec element gameObject de type GameObject */
                gameObject.update();
            }
            try {
                collision();
            } catch (UnsupportedAudioFileException ex) {   /* Résolution d'erreur par l'ide pour le son comme les throw je comprends pas pourquoi */
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) { /* Mouvement du Pacman*/
        if (!gameOver && !victoire) {
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
        if (gameOver || victoire) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setFont(new Font("Arial", Font.BOLD, 50));
            if (gameOver) {
                g.setColor(Color.RED);
                g.drawString("GAME OVER", getWidth()/2 - 150, getHeight()/2);
            } else {
                g.setColor(Color.GREEN);
                g.drawString("GAGNÉ !", getWidth()/2 - 100, getHeight()/2);
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Score Final: " + score, getWidth()/2 - 70, getHeight()/2 + 50);
        }
    }
}