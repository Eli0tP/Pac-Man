import game_items.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Cherry extends Points{
    private BufferedImage cherry = null;

    public Cherry(int x, int y){
        try{
            this.score = 2;
            this.x = x;
            this.y = y;
            cherry = ImageIO.read(new File("resources/Pac_Man_Cherry.png"));
        } catch (IOException e){
            System.out.println("Erreur l'image Cherry");
        }
    }

    public void draw(Graphics g){
        g.drawImage(cherry,x,y,gridSize,gridSize,null);
    }

    @Override
    public int getScore(){
        return score;
    }


}
