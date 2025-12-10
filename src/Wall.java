import game_items.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wall implements GameObject{
    private int x_debut;
    private int y_debut;
    private int x_fin;
    private int y_fin;
    private int grideSize;
    private BufferedImage wall = null;


    public Wall() {
        try {
            wall = ImageIO.read(new File("resources/Pac_Man_Wall.png"));
        } catch (IOException e) {
            System.out.println("Erreur l'image Wall");
        }
    }

    public void draw(Graphics g) {
        g.drawImage(wall, x_debut, y_debut, grideSize, grideSize, null);
    }

    public void update(){
    }
}
