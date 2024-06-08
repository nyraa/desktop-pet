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

public class Cat extends Animal
{
    double directionX = 1;
    double directionY = -1;
    static final double changeThreshold = 0.995;
    static final double clawThreshold = 0.998;
    static final int speed = 1;

    ImageIcon walk_right = null;
    ImageIcon walk_left = null;
    ImageIcon play_left = null;
    ImageIcon play_right = null;
    ImageIcon scratch_gif = null;
    ImageIcon scratch_static = null;
    ClawMark mark = null;
    Window window = null;

    public Cat(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        System.out.println("Cat created");
        try{
            //read in image and resize
            walk_right = new ImageIcon(getClass().getResource("/images/right_150.gif"));
            walk_right.setImage(walk_right.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT)); //resize the image
            walk_left = new ImageIcon(getClass().getResource("/images/left_150.gif"));
            walk_left.setImage(walk_left.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
            play_left = new ImageIcon(getClass().getResource("/images/left_play_.gif"));
            play_left.setImage(play_left.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
            play_right = new ImageIcon(getClass().getResource("/images/right_play_.gif"));
            play_right.setImage(play_right.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
            scratch_gif = new ImageIcon(getClass().getResource("/images/scratch.gif"));
            scratch_gif.setImage(scratch_gif.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)); 
            scratch_static = new ImageIcon(getClass().getResource("/images/scratch.png"));
            scratch_static.setImage(scratch_static.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

            this.setIcon(walk_right);
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
    }

    

    @Override
    public void update(State state)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //scratching control
        if(mark != null){
            Date now = new Date();
            //set animation of scratching cat
            if(now.getTime() - mark.createTime <= 4200){
                this.setIcon(scratch_gif);
                return;
            }

            //determine if the claw should be deleted
            if((now.getTime() - mark.createTime) >= mark.existTime * 1000){
                state.window.deleteAnimal(mark);
                mark.createTime = 0;
                mark = null;
                System.out.println("Claw deleted");
            }
        }

        //check if the mouse is on the cat
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;
        if(mouseX>getX() && mouseX<getX()+getWidth()/2 && mouseY>getY() && mouseY<getY()+getHeight() && this.getIcon() != play_right){
            this.setIcon(play_left);
            return;
        }else if(mouseX > getX() + getWidth()/2 && mouseX < getX() + getWidth() && mouseY > getY() && mouseY < getY() + getHeight()  && this.getIcon() != play_left){
            this.setIcon(play_right);
            return;
        }else{
            this.setIcon(directionX>0?walk_right:walk_left);
        }

        //randomly change the direction
        double probability = Math.random();

        //randomly scratch the screen
        if(probability >= clawThreshold){
            this.setIcon(scratch_gif);
            if(mark == null){
                mark = new ClawMark(getX(), getY(), 100, 100, 8); //width, height, existTime
            }
            return;
        }

        if(probability >= changeThreshold){
            double randX = Math.random();
            double randY = Math.random();
            directionX = (randX / Math.sqrt(randX*randX + randY*randY))*2;
            directionY = (randY / Math.sqrt(randX*randX + randY*randY))*2;
            this.setIcon(directionX>0?walk_right:walk_left);
            System.out.println("Direction changed: "+directionX+" "+directionY);
        }

        

        //check if the cat hits the edge
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
        /*int x = 50; // x-coordinate of the circle's center
        int y = 50; // y-coordinate of the circle's center
        int radius = 30; // radius of the circle
        
        g.setColor(java.awt.Color.RED);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);*/
    }
}