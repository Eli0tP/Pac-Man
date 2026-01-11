import game_items.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Points implements GameObject {
    protected int x;
    protected int y;
    protected int grideSize = 32;
    protected int score = 1;
    private BufferedImage dot = null;

    public Points(){
    }

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
        g.drawImage(dot, x, y, grideSize, grideSize, null);
    }

    public void update() {
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getScore(){
        return score;
    }
}
