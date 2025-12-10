import game_items.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Points implements GameObject {
    private int x = 120;
    private int y = 120;
    private int gridSize = 16;
    private BufferedImage dot = null;



    public Points() {
        try {
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
}
