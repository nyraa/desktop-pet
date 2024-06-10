package desktoppet.animals.Mouse;

import desktoppet.control.State;
import desktoppet.model.Animal;
import desktoppet.control.World;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

public class Mouse extends Animal{
    private double directionX = 1;
    private double directionY = -1;
    private static final double changeThreshold = 0.995;
    private static final int speed = 1;

    private ImageIcon walk_right = null;
    private ImageIcon walk_left = null;

    public Mouse(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        System.out.println("Mouse created");
        try{
            //read in image and resize
            walk_right = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Mouse/mouse_right.gif"));
            walk_right.setImage(walk_right.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)); //resize the image
            walk_left = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Mouse/mouse_left.gif"));
            walk_left.setImage(walk_left.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

            this.setIcon(walk_right);
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
    }

    public static void entry(World world)
    {
        Mouse mouse = new Mouse(0, 0, 100, 100);
        world.addAnimal(mouse);
    }

    @Override
    public void update(State state)
    {
        int screenWidth = state.getScreenWidth();
        int screenHeight = state.getScreenHeight();

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
        if(getX() > screenWidth-this.getWidth() || getX() < 0 || getY() > screenHeight-this.getHeight() || getY() < 0){
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
