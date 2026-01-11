package game_items;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PacMan implements GameObject {
    private int x = 32;
    private int y = 32;
    private int dx = 0;
    private int dy = 0;
    private int futureDx = 0;
    private int futureDy = 0;
    private int speed = 4;
    private int gridSize = 32;
    private BufferedImage img = null;
    private BufferedImage imgU = null;
    private BufferedImage imgD = null;
    private BufferedImage imgR = null;
    private BufferedImage imgL = null;
    private boolean estSuper = false;
    private int tSuper = 0;

    public PacMan(){
        try {
            imgU = ImageIO.read(new File("resources/Pac_Man_Up.png"));
            imgD = ImageIO.read(new File("resources/Pac_Man_Down.png"));
            imgR = ImageIO.read(new File("resources/Pac_Man_Right.png"));
            imgL = ImageIO.read(new File("resources/Pac_Man_Left.png"));

            img = imgR;
        } catch (IOException e) {
            System.out.println("Erreur img PacMan");
        }
    }

    public void update() {
        updateSpeed();
        updatePosition();

        if (estSuper) {
            tSuper--;
            if (tSuper <= 0) { /* Pour une raison inconnue le tSuper == 0 avait quelques erreurs et le timer devenait négatif*/
                estSuper = false;
            }
        }
    }

    private void updateSpeed(){
        if (isOnGrid()){
            dx = futureDx;
            dy = futureDy;
        }
    }

    private void updatePosition(){
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawImage(img,x,y,gridSize,gridSize,null);

    }

    public void resetPosition() {
        this.x = 32; // Coordonnée X de départ
        this.y = 32 ; // Coordonnée Y de départ
        this.dx = 0;     // On arrête le mouvement horizontal
        this.dy = 0;
    }

    public void keyDown(){
        futureDy = speed;
        futureDx = 0;
        img = imgD;
    }

    public void keyUp(){
        futureDy = -speed;
        futureDx = 0;
        img = imgU;
    }

    public void keyLeft(){
        futureDy = 0;
        futureDx = -speed;
        img = imgL;
    }

    public void keyRight(){
        futureDy = 0;
        futureDx = speed;
        img = imgR;
    }

    private boolean isOnGrid(){
        return x%gridSize == 0 && y%gridSize == 0;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) { this.y = y; }

    public void collisionMur() {
        this.x -= this.dx;
        this.y -= this.dy;
        this.dx = 0;
        this.dy = 0;
    }

    public boolean estSuper() { return estSuper;}

    public void setSuper(boolean statut) {
        this.estSuper = statut;
        if (statut) this.tSuper = 300; // 600 = 10sec soit 10 * 60frames
    }
}