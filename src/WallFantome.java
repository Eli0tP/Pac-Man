import game_items.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class WallFantome extends Wall {
    private BufferedImage img = null;

    public WallFantome(int x, int y) {
        try {
            this.x = x;
            this.y = y;
            img = ImageIO.read(new File("resources/Pac_Man_Laser.png"));
        } catch (IOException e){
            System.out.println("Erreur l'image lazer");
        }
    }

    public void draw(Graphics g){
        g.drawImage(img,x,y,grideSize,grideSize,null);
    }

}