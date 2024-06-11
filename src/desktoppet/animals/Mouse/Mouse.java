package desktoppet.animals.Mouse;

import desktoppet.animals.Cat.Cat;
import desktoppet.control.State;
import desktoppet.model.Animal;
import desktoppet.control.World;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

public class Mouse extends Animal{
    private double directionX = 1;
    private double directionY = -1;
    private double x = 0;
    private double y = 0;
    private static final double changeThreshold = 0.995;
    private static final double mouseRespawnProbability = 0.999;
    private static final int distanceThreshold = 300;
    private static final int walkingSpeed = 1;
    private double runningSpeed = walkingSpeed;
    private double currentSpeed = walkingSpeed;

    private ImageIcon walk_right = null;
    private ImageIcon walk_left = null;
    private ImageIcon run_right = null;
    private ImageIcon run_left = null;

    //private boolean beingCaught = false;
    private boolean beingChased = false;

    public Mouse(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        System.out.println("Mouse created");
        try{
            //read in image and resize
            walk_right = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Mouse/mouse_right.gif"));
            walk_right.setImage(walk_right.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)); //resize the image
            walk_left = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Mouse/mouse_left.gif"));
            walk_left.setImage(walk_left.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
            run_right = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Mouse/mouse_run_right.gif"));
            run_right.setImage(run_right.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
            run_left = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Mouse/mouse_run_left.gif"));
            run_left.setImage(run_left.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
            this.setIcon(walk_right);
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
        randomRunningSpeed();
    }

    public static void entry(World world)
    {
        Mouse mouse = new Mouse(500, 500, 80, 80);
        world.addAnimal(mouse);
    }

    @Override
    public void update(State state){
        int screenWidth = state.getScreenWidth();
        int screenHeight = state.getScreenHeight();

        //check the relative position of the mouse and the cat
        double minDistanceToCat = -1;  // inf
        beingChased = false;
        Component[] components = state.getComponents();
        for (Component component : components)
        {
            if(component instanceof Cat)
            {
                Cat cat = (Cat)component;
                double catToMouseDistance = Math.sqrt(Math.pow((cat.getX() - this.getX()), 2) + Math.pow((cat.getY() - this.getY()), 2));
                if(minDistanceToCat == -1 || catToMouseDistance < minDistanceToCat)
                {
                    minDistanceToCat = catToMouseDistance;
                }

                //check if the mouse is being chased by the cat
                if(this.isVisible() && catToMouseDistance < distanceThreshold)
                {
                    System.out.println("Mouse being chased by cat");
                    beingChased = true;
                    //directionX = cat.getDirectionX() > 0 ? 1: -1;
                    directionX = cat.getDirectionX();
                    directionY = cat.getDirectionY();
                }
            }
        }

        //has been disappeared for a while, generate a new mouse
        if(this.isVisible() == false && minDistanceToCat >= 0 && minDistanceToCat > distanceThreshold){  
            double respawnProbability = Math.random();
            if(respawnProbability > mouseRespawnProbability){
                //beingCaught = false;
                beingChased = false;
                this.setVisible(true);
                randomRunningSpeed();
                System.out.println("Mouse respawned");
            }
        }

        //randomly change the direction
        double probability = Math.random();

        if(probability >= changeThreshold)
        {
            double randX = Math.random();
            double randY = Math.random();
            directionX = (randX / Math.sqrt(randX*randX + randY*randY))*2;
            directionY = (randY / Math.sqrt(randX*randX + randY*randY))*2;
            System.out.println("Direction changed: "+directionX+" "+directionY);
        }
        
        if(beingChased)
        {
            if(this.x + this.getWidth() < 0 || this.x > screenWidth || this.y + this.getHeight() < 0 || this.y > screenHeight)
            {
                // mouse is out of screen, disappear
                this.setVisible(false);
                this.setIcon(null);
                beingChased = false;
            }
        }
        else
        {
            // bounce when hit the wall
            if(this.x < 0)
            {
                directionX = Math.abs(directionX);
            }
            if(this.x > screenWidth - this.getWidth())
            {
                directionX = -Math.abs(directionX);
            }
            if(this.y < 0)
            {
                directionY = Math.abs(directionY);
            }
            if(this.y > screenHeight - this.getHeight())
            {
                directionY = -Math.abs(directionY);
            }
        }

        
        //move the instance
        if(beingChased)
        {
            currentSpeed = runningSpeed;
            this.setIcon(directionX > 0 ? run_right : run_left);
        }
        else
        {
            currentSpeed = walkingSpeed;
            this.setIcon(directionX > 0 ? walk_right : walk_left);
        }
        x = x + currentSpeed * directionX;
        y = y + currentSpeed * directionY;
        setLocation((int)x, (int)y);
    }

    private void randomRunningSpeed()
    {
        double rand = Math.random();
        if(rand > 0.8)
        {
            runningSpeed = 3;
        }
        else if(rand > 0.6)
        {
            runningSpeed = 2;
        }
        else if(rand > 0.4)
        {
            runningSpeed = 1.5;
        }
        else
        {
            runningSpeed = 1.3;
        }
        System.out.println("Mouse running speed: " + runningSpeed);
    }

    @Override
    public void setIcon(Icon icon)
    {
        if(this.getIcon() != icon)
        {
            super.setIcon(icon);
        }
    }

    public void caught()
    {
        this.setVisible(false);
        this.setIcon(null);
        beingChased = false;
    }
        
    @Override
    public void paintContent(Graphics g)
    {
    
    }
}
