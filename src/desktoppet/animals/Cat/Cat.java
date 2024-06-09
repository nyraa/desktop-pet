package desktoppet.animals.Cat;

import desktoppet.control.State;
import desktoppet.model.Animal;
import desktoppet.ui.Window;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Date;

import javax.swing.*;

public class Cat extends Animal
{
    private enum ActionState
    {
        WALK_RIGHT,
        WALK_LEFT,
        PLAY_RIGHT,
        PLAY_LEFT,
        SCRATCH
    };
    ActionState actionState = ActionState.WALK_RIGHT;
    double directionX = 1;
    double directionY = -1;
    static final double changeThreshold = 0.995;
    static final double clawThreshold = 0.998;
    static final int speed = 1;
    private long scratchStartTime = 0;

    ImageIcon walk_right = null;
    ImageIcon walk_left = null;
    ImageIcon play_left = null;
    ImageIcon play_right = null;
    ImageIcon scratch_gif = null;
    ImageIcon scratch_static = null;
    Window window = null;

    public Cat(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        System.out.println("Cat created");
        try{
            //read in image and resize
            walk_right = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/right_150.gif"));
            walk_right.setImage(walk_right.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT)); //resize the image
            walk_left = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/left_150.gif"));
            walk_left.setImage(walk_left.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
            play_left = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/left_play_.gif"));
            play_left.setImage(play_left.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
            play_right = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/right_play_.gif"));
            play_right.setImage(play_right.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
            scratch_gif = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/scratch.gif"));
            scratch_gif.setImage(scratch_gif.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)); 
            scratch_static = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/scratch.png"));
            scratch_static.setImage(scratch_static.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

            this.setIcon(walk_right);
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
    }

    

    @Override
    public void update(State state)
    {
        // stall the cat for a while after scratching
        if(actionState == ActionState.SCRATCH)
        {
            if(System.currentTimeMillis() - scratchStartTime < 4000)
            {
                return;
            }
            else
            {
                if(directionX > 0)
                {
                    actionState = ActionState.WALK_RIGHT;
                    this.setIcon(walk_right);
                }
                else
                {
                    actionState = ActionState.WALK_LEFT;
                    this.setIcon(walk_left);
                }
            }
        }
        int screenWidth = state.getScreenWidth();
        int screenHeight = state.getScreenHeight();

        //check if the mouse is on the cat
        int mouseX = state.getMouseX();
        int mouseY = state.getMouseY();
        if(mouseX>getX() && mouseX<getX()+getWidth()/2 && mouseY>getY() && mouseY<getY()+getHeight() && this.getIcon() != play_right){
            actionState = ActionState.PLAY_RIGHT;
            this.setIcon(play_left);
            return;
        }else if(mouseX > getX() + getWidth()/2 && mouseX < getX() + getWidth() && mouseY > getY() && mouseY < getY() + getHeight()  && this.getIcon() != play_left){
            actionState = ActionState.PLAY_LEFT;
            this.setIcon(play_right);
            return;
        }else{
            if(actionState != ActionState.WALK_LEFT && actionState != ActionState.WALK_RIGHT)
            {
                if(directionX > 0)
                {
                    actionState = ActionState.WALK_RIGHT;
                    this.setIcon(walk_right);
                }
                else
                {
                    actionState = ActionState.WALK_LEFT;
                    this.setIcon(walk_left);
                }
            }
        }

        //randomly change the direction
        double probability = Math.random();

        //randomly scratch the screen
        if(probability >= clawThreshold){
            actionState = ActionState.SCRATCH;
            System.out.println("Scratching");
            scratchStartTime = System.currentTimeMillis();
            this.setIcon(scratch_gif);
            ClawMark mark = new ClawMark(getX(), getY(), 100, 100, 8); //width, height, existTime
            state.getWorldRef().addAnimal(mark);
            return;
        }

        if(probability >= changeThreshold){
            double randX = Math.random();
            double randY = Math.random();
            directionX = (randX / Math.sqrt(randX*randX + randY*randY))*2;
            directionY = (randY / Math.sqrt(randX*randX + randY*randY))*2;
            if(directionX > 0)
            {
                actionState = ActionState.WALK_RIGHT;
                this.setIcon(walk_right);
            }
            else
            {
                actionState = ActionState.WALK_LEFT;
                this.setIcon(walk_left);
            }
            System.out.println("Direction changed: "+directionX+" "+directionY);
        }

        

        //check if the cat hits the edge
        if(getX() > screenWidth-this.getWidth() || getX() < 0 || getY() > screenHeight-this.getHeight() || getY() < 0){
            directionX = -directionX;
            directionY = -directionY;
            if(directionX > 0)
            {
                actionState = ActionState.WALK_RIGHT;
                this.setIcon(walk_right);
            }
            else
            {
                actionState = ActionState.WALK_LEFT;
                this.setIcon(walk_left);
            }
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
