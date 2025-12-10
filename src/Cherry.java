import game_items.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Cherry implements GameObject {
    private int x = 120;
    private int y = 120;
    private int gridSize = 30;
    private BufferedImage cherry = null;
    private int score = 2;

    public Cherry(){
        try{
            cherry = ImageIO.read(new File("resources/Pac_Man_Cherry.png"));
        } catch (IOException e){
            System.out.println("Erreur l'image Cherry");
        }
    }

    public void draw(Graphics g){
        g.drawImage(cherry,x,y,gridSize,gridSize,null);
    }

    public void update() {
    }
}
