import game_items.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Teleporter implements GameObject{
    private int x;
    private int y;
    private int grideSize = 32;
    private BufferedImage teleporter = null;

/*
1er teleporteur (gauche) en 32 352
2eme en 768 352*/


    public Teleporter(int x, int y) {
        try {
            this.x = x;
            this.y = y;
            teleporter = ImageIO.read(new File("resources/Pac_Man_Tel.png"));
        } catch (IOException e) {
            System.out.println("Erreur l'image Wall");
        }
    }

    public void draw(Graphics g) {
        g.drawImage(teleporter, x, y, grideSize, grideSize, null);
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
