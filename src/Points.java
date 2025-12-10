import game_items.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Points implements GameObject {
    private int x;
    private int y;
    private int gridSize = 32;
    private BufferedImage dot = null;
    private int score = 1;



    public Points(int x, int y) {
        try {
            this.x = x;
            this.y = y;
            dot = ImageIO.read(new File("resources/Pac_Man_Dot.png"));
        } catch (IOException e) {
            System.out.println("Erreur l'image Dot");
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawImage(dot, x, y, gridSize, gridSize, null);
    }

    public void update() {
    }

//    public void distRemove(ArrayList<GameObject> liste , int xPac, int yPac, int xPoint, int yPoint) {
//        double distance = Math.sqrt(Math.pow((xPoint - xPac), 2) + Math.pow((yPoint - yPac), 2));
//        if (distance < (double) 16){
//            liste.remove();
//        }
//    }
}
