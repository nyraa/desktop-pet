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
    private static final double changeThreshold = 0.995;
    private static final double mouseGenerateProbability = 0.999;
    private static final int distanceThreshold = 300;
    private double catToDogDistance = 0;
    private static final int walkingSpeed = 1;
    private double runningSpeed = 3;
    private double currentSpeed = walkingSpeed;

    private ImageIcon walk_right = null;
    private ImageIcon walk_left = null;
    private ImageIcon run_right = null;
    private ImageIcon run_left = null;

    //private boolean beingCaught = false;
    private boolean beingChased = false;
    private boolean setVisible = true;

    public Mouse(int x, int y, int width, int height)
    {
        super(x, y, width, height);
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

            //randomly set running speed
            double probability = Math.random();
            if(probability > 0.8){
                runningSpeed = 3;
            }
            else if(probability > 0.6){
                runningSpeed = 2;
            }
            else if (probability > 0.4){
                runningSpeed = 1.5;
            }
            else{
                runningSpeed = 1.3;
            }
            System.out.println("Mouse running speed: "+runningSpeed);

            this.setIcon(walk_right);
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
        
    }

    public static void entry(World world)
    {
        Mouse mouse = new Mouse(300, 300, 80, 80);
        world.addAnimal(mouse);
    }

    @Override
    public void update(State state){
        int screenWidth = state.getScreenWidth();
        int screenHeight = state.getScreenHeight();

        //check the relative position of the mouse and the cat
        Component[] components = state.getComponents();
        for (Component component : components)
        {
            if(component instanceof Cat)
            {
                Cat cat = (Cat)component;
                catToDogDistance = Math.sqrt(Math.pow((cat.getX() - this.getX()), 2) + Math.pow((cat.getY() - this.getY()), 2));

                //check if the mouse is being chased by the cat
                if(this.setVisible == true && catToDogDistance < distanceThreshold)
                {
                    System.out.println("Mouse being chased by cat");
                    beingChased = true;

                    if(directionX > 0){
                        this.setIcon(run_right);
                    }
                    else{
                        this.setIcon(run_left);
                    }

                    if(cat.getDirectionX() > 0){
                        directionX = 1;
                    }
                    else{
                        directionX = -1;
                    }
                    if(cat.getDirectionY() > 0){
                        directionY = 1;
                    }
                    else{
                        directionY = -1;
                    }
                }

                //check if the mouse is caught by the cat
                if(this.isVisible() == true && cat.getX() < this.getX() + this.getWidth()/2 && cat.getX() + cat.getWidth()/2 > this.getX() && cat.getY() < this.getY() + this.getHeight()/2 && cat.getY() + cat.getHeight()/2 > this.getY())
                {
                    System.out.println("Mouse caught by cat");
                    //beingCaught = true;
                    setVisible = false;
                    beingChased = false;
                }
            }
        }

        //has been disappeared for a while, generate a new mouse
        if(this.isVisible() == false && catToDogDistance > distanceThreshold){  
            double visibleProbability = Math.random();
            if(visibleProbability > mouseGenerateProbability){
                //beingCaught = false;
                beingChased = false;
                setVisible = true;
                if(directionX > 0){
                    this.setIcon(walk_right);
                }
                else{
                    this.setIcon(walk_left);
                }
                //randomly set running speed
                double probability = Math.random();
                if(probability > 0.8){
                    runningSpeed = 3;
                }
                else if(probability > 0.6){
                    runningSpeed = 2;
                }
                else if (probability > 0.3){
                    runningSpeed = 1.5;
                }
                else{
                    runningSpeed = 1.3;
                }
                System.out.println("Mouse running speed: "+runningSpeed);
                System.out.println("Mouse generated");
            }
        }

        //set visible or not
        if(setVisible){
            this.setVisible(true);
        }
        else{
            this.setVisible(false);
            this.repaint();
            this.setIcon(null);
            //System.out.println("Mouse hidden");
        }

        //randomly change the direction
        double probability = Math.random();

        if(probability >= changeThreshold){
            double randX = Math.random();
            double randY = Math.random();
            directionX = (randX / Math.sqrt(randX*randX + randY*randY))*2;
            directionY = (randY / Math.sqrt(randX*randX + randY*randY))*2;
            if(beingChased){
                this.setIcon(directionX>0?run_right:run_left);
            }
            else{
                this.setIcon(directionX>0?walk_right:walk_left);
            }
            System.out.println("Direction changed: "+directionX+" "+directionY);
        }
        

        //check if the mouse hits the edge
        if(getX() > screenWidth-this.getWidth() || getX() < 0 || getY() > screenHeight-this.getHeight() || getY() < 0){
            if(beingChased){
                //beingChased = false;
                setVisible = false;
                this.setVisible(false);
            }
            directionX = -directionX;
            directionY = -directionY;
            currentSpeed = runningSpeed;
            if(beingChased){
                this.setIcon(directionX>0?run_right:run_left);
            }
            else{
                this.setIcon(directionX>0?walk_right:walk_left);
            }
            System.out.println("hit edge, Direction changed: "+directionX+" "+directionY);
        }
        else{
            if(beingChased){
                currentSpeed = runningSpeed;
            }
            else{
                currentSpeed = walkingSpeed;
            }
        }

        //move the instance
        
        setLocation(getX() + (int)(currentSpeed*(int)directionX), getY()+ (int)(currentSpeed*(int)directionY));


    }
        
    @Override
    public void paintContent(Graphics g)
    {
    
    }
}
