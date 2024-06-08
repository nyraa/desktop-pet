package desktoppet.animals;

import desktoppet.control.State;
import desktoppet.model.Animal;
import desktoppet.ui.Window;
import desktoppet.animals.ClawMark;

import java.awt.MouseInfo;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.*;
import java.net.*;

import java.lang.*;

public class Mouse extends Animal{
    double directionX = 1;
    double directionY = -1;
    static final double changeThreshold = 0.995;
    static final int speed = 1;

    ImageIcon walk_right = null;
    ImageIcon walk_left = null;
    Window window = null;

    public Mouse(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        System.out.println("Cat created");
        try{
            //read in image and resize
            walk_right = new ImageIcon(getClass().getResource("/images/mouse_right.gif"));
            walk_right.setImage(walk_right.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)); //resize the image
            walk_left = new ImageIcon(getClass().getResource("/images/mouse_left.gif"));
            walk_left.setImage(walk_left.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

            this.setIcon(walk_right);
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
    }

    

    @Override
    public void update(State state)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //randomly change the direction
        double probability = Math.random();

        if(probability >= changeThreshold){
            double randX = Math.random();
            double randY = Math.random();
            directionX = (randX / Math.sqrt(randX*randX + randY*randY))*2;
            directionY = (randY / Math.sqrt(randX*randX + randY*randY))*2;
            this.setIcon(directionX>0?walk_right:walk_left);
            System.out.println("Direction changed: "+directionX+" "+directionY);
        }

        //check if the mouse hits the edge
        if(getX() > screenSize.width-this.getWidth() || getX() < 0 || getY() > screenSize.height-this.getHeight() || getY() < 0){
            directionX = -directionX;
            directionY = -directionY;
            this.setIcon(directionX>0?walk_right:walk_left);
            System.out.println("hit edge, Direction changed: "+directionX+" "+directionY);
        }

        //move the instance
        setLocation(getX() + speed*(int)directionX, getY()+ speed*(int)directionY);

    }
        
    @Override
    public void paintContent(Graphics g)
    {
    
    }
}
