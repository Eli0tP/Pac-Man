import game_items.GameObject;
import game_items.PacMan;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Ghost implements GameObject{
    private ArrayList<GameObject> gameObjects;
    private int x, y;
    private int startX, startY;
    private int dx = speed, dy = 0;
    private static final int speed = 2;
    private int grideSize = 32;
    private BufferedImage img;
    private BufferedImage imgSuper;
    private Random random = new Random();
    private int dir;
    private PacMan pacMan;
    private boolean isDead = false;
    private int respawnT = 0;
    private int respawnD = 180;
    private double distP = 0;
    private int pPoursuite = 0;
    private int pAleatoire = 0;

/*
1er fantome (gauche) en 320 352
2 fantome (gauche) en 352 352
3 fantome (gauche) en 448 352
4 fantome (gauche) en 480 352
*/


    public Ghost(int x, int y, int pPoursuite, int pAleatoire, ArrayList<GameObject> gameObjects,PacMan pacMan, String imagePath) {
        try {
            this.x = x;
            this.y = y;
            this.startX = x;
            this.startY = y;
            this.pAleatoire = pAleatoire;
            this.pPoursuite = pPoursuite;
            this.pacMan = pacMan;
            this.gameObjects = gameObjects;
            this.img = ImageIO.read(new File(imagePath));
            this.imgSuper = ImageIO.read(new File("resources/Pac_Man_GB.png"));
        } catch (IOException e) {
            System.out.println("Erreur l'image Ghost");
        }
    }

    @Override
    public void draw(Graphics g) {
        if (pacMan.estSuper()) {
            g.drawImage(imgSuper, x, y, grideSize, grideSize, null);
        } else {
            g.drawImage(img, x, y, grideSize, grideSize, null);
        }
    }

    @Override
    public void update() {
        if (isDead) {
            respawnT--;
            if (respawnT <= 0) {
                isDead = false;
                resetPosition();
            }
            return;
        }
        x += dx;
        y += dy;
        if (x % grideSize == 0 && y % grideSize == 0) {
        // connaitre le dir pour analynser les autres propositions pour pouvoir/savoir ou allez ou rester sur la meme dir
            ArrayList<String> choixPossibles = obtenirChoixPossibles();
            if (!choixPossibles.isEmpty()) {
                randomEtat(choixPossibles);
            } else {
                dx = -dx;
                dy = -dy;
            }
        }
    }

    private ArrayList<String> obtenirChoixPossibles() {
        ArrayList<String> choix = new ArrayList<>();
        if (mouvementPossible(dx, dy)) {
            choix.add("e");
        }
        if (dx != 0) {
            if (mouvementPossible(0, -grideSize)) choix.add("HAUT");
            if (mouvementPossible(0, grideSize)) choix.add("BAS");
        } else {
            if (mouvementPossible(-grideSize, 0)) choix.add("GAUCHE");
            if (mouvementPossible(grideSize, 0)) choix.add("DROITE");
        }
        return choix;
    }

    public boolean mouvementPossible(int fDX, int fDY) {
        int checkX = x + (fDX > 0 ? grideSize : (fDX < 0 ? -grideSize : 0));
        int checkY = y + (fDY > 0 ? grideSize : (fDY < 0 ? -grideSize : 0));
        for (GameObject obj : gameObjects) {
            if (obj instanceof Wall && !(obj instanceof WallFantome)) {
                Wall w = (Wall) obj;
                if (w.getX() == checkX && w.getY() == checkY)
                    return false;
            } else if (obj instanceof Teleporter) {
                Teleporter t = (Teleporter) obj;
                if (t.getX() == checkX && t.getY() == checkY)
                    return false;
        }
    }
    return true;
}

    public void randomEtat(ArrayList<String> choixPossibles) {
        int tirage = random.nextInt(100);
        String decision;

        if (pacMan.estSuper()) {
            decision = choisirMeilleureDirection(choixPossibles, false);
        } else if (tirage < this.pPoursuite) {
            decision = choisirMeilleureDirection(choixPossibles, true);
        } else {
            decision = choixPossibles.get(random.nextInt(choixPossibles.size()));
        }
        appliquerDecision(decision);
    }


    public String choisirMeilleureDirection(ArrayList<String> choix, boolean versPacman) {
        String meilleure = choix.get(0);
        double distanceCible = versPacman ? Double.MAX_VALUE : -1.0;

        for (String c : choix) {
            int nextX = x;
            int nextY = y;

            if (c.equals("HAUT")) nextY -= grideSize;
            else if (c.equals("BAS")) nextY += grideSize;
            else if (c.equals("GAUCHE")) nextX -= grideSize;
            else if (c.equals("DROITE")) nextX += grideSize;
            else if (c.equals("e")) {
                nextX += (dx > 0) ? grideSize : (dx < 0 ? -grideSize : 0);
                nextY += (dy > 0) ? grideSize : (dy < 0 ? -grideSize : 0);
            }

            double d = Math.sqrt(Math.pow(nextX - pacMan.getX(), 2) + Math.pow(nextY - pacMan.getY(), 2));

            if (versPacman) {
                if (d < distanceCible) { distanceCible = d; meilleure = c; }
            } else {
                if (d > distanceCible) { distanceCible = d; meilleure = c; }
            }
        }
        return meilleure;
    }

    public void appliquerDecision(String decision) {
        switch (decision) {
            case "HAUT":   dx = 0; dy = -speed; break;
            case "BAS":    dx = 0; dy = speed; break;
            case "GAUCHE": dx = -speed; dy = 0; break;
            case "DROITE": dx = speed; dy = 0; break;
        }
    }

    public void resetPosition() {
        this.x = startX;
        this.y = startY;
        this.dx = speed;
        this.dy = 0;
    }

    public void Mort() {
        this.isDead = true;
        this.respawnT = respawnD;
    }



//    public void randomDirection() {
//        x -= dx;
//        y -= dy;
//        randomDir();
//        dx = 0; dy = 0;
//        if (dir == 0) dy = -speed;
//        else if (dir == 1) dy = speed;
//        else if (dir == 2) dx = -speed;
//        else if (dir == 3) dx = speed;
//    }
//    public int randomDir() {
//        int dir = random.nextInt(4);
//        return dir;
//    }


    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }


}
