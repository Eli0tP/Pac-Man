import game_items.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wall implements GameObject{
    protected int x;
    protected int y;
    protected int grideSize = 32;
    private BufferedImage wall = null;


    public Wall(int x, int y) {
        try {
            this.x = x;
            this.y = y;
            wall = ImageIO.read(new File("resources/Pac_Man_Wall.png"));
        } catch (IOException e) {
            System.out.println("Erreur l'image Wall");
        }
    }

    public Wall() {
    }

    public void draw(Graphics g) {
        g.drawImage(wall, x, y, grideSize, grideSize, null);
    }

    public void update(){
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
