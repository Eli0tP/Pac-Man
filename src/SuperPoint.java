import game_items.GameObject;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SuperPoint extends Points{
    private BufferedImage sPoint = null;

    public SuperPoint(int x, int y){
        try{
            this.score = 10;
            this.x = x;
            this.y = y;
            sPoint = ImageIO.read(new File("resources/Pac_Man_SuperP.png"));
        } catch (IOException e){
            System.out.println("Erreur l'image Super Point");
        }
    }

    public void draw(Graphics g){
        g.drawImage(sPoint,x,y,grideSize,grideSize,null);
    }

    @Override
    public int getScore(){
        return score;
    }


}
