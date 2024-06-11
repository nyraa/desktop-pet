package desktoppet.animals.Cat;

import desktoppet.control.State;
import desktoppet.control.World;
import desktoppet.model.Animal;
import desktoppet.animals.Mouse.Mouse;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Component;

import javax.swing.*;

public class Cat extends Animal
{
    private enum ActionState
    {
        WALK_LEFT,
        WALK_RIGHT,
        PLAY_LEFT,
        PLAY_RIGHT,
        SCRATCH, 
        CHASE,
        CATCH
    };
    private ActionState actionState = ActionState.WALK_RIGHT;
    public double directionX = 1;
    public double directionY = -1;
    private static final double changeThreshold = 0.995;
    private static final double clawThreshold = 0.998;
    private static final int distanceThreshold = 300;
    private int speed = 1;
    private long scratchStartTime = 0;
    private long catchStartTime = 0;
    private double catToMouseDistance = 0;

    private ImageIcon walk_right = null;
    private ImageIcon walk_left = null;
    private ImageIcon run_left = null;
    private ImageIcon run_right = null;
    private ImageIcon play_left = null;
    private ImageIcon play_right = null;
    private ImageIcon scratch_gif = null;
    private ImageIcon scratch_static = null;
    private ImageIcon catched_left = null;
    private ImageIcon catched_right = null;

    public Cat(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        System.out.println("Cat created");
        try{
            //read in image and resize
            walk_right = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/right_150.gif"));
            walk_right.setImage(walk_right.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)); //resize the image
            walk_left = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/left_150.gif"));
            walk_left.setImage(walk_left.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
            run_left = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/left_100.gif"));
            run_left.setImage(run_left.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
            run_right = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/right_100.gif"));
            run_right.setImage(run_right.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
            play_left = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/left_play.gif"));
            play_left.setImage(play_left.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
            play_right = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/right_play.gif"));
            play_right.setImage(play_right.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
            scratch_gif = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/scratch.gif"));
            scratch_gif.setImage(scratch_gif.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)); 
            catched_left = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/catched_left.png"));
            catched_left.setImage(catched_left.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
            catched_right = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/catched_right.png"));
            catched_right.setImage(catched_right.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));

            this.setIcon(walk_right);
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
    }
    public static void entry(World world)
    {
        Cat cat = new Cat(100, 100, 200, 200);
        world.addAnimal(cat);
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
                actionState = directionX>0?ActionState.WALK_RIGHT:ActionState.WALK_LEFT;
                this.setIcon(directionX>0?walk_right:walk_left);
                System.out.println("set icon to walking, line 82");
            }
        }
        int screenWidth = state.getScreenWidth();
        int screenHeight = state.getScreenHeight();

        //check if the cursor is on the cat
        int mouseX = state.getMouseX();
        int mouseY = state.getMouseY();
        if(mouseX>getX() && mouseX<getX()+getWidth()/2 && mouseY>getY() && mouseY<getY()+getHeight()){
            if(actionState != ActionState.PLAY_LEFT){
                this.setIcon(play_left);
                actionState = ActionState.PLAY_LEFT;
            } 
            return;
        }else if(mouseX>getX()+getWidth()/2 && mouseX<getX()+getWidth() && mouseY>getY() && mouseY<getY()+getHeight()){
            if(actionState != ActionState.PLAY_RIGHT){
                this.setIcon(play_right);
                actionState = ActionState.PLAY_RIGHT;
            }
            return;
        }else{
            //reset icon to walking ones if not currently walking
            if(!(directionX>0 && actionState == ActionState.WALK_RIGHT) && 
               !(directionX<0 && actionState == ActionState.WALK_LEFT)){
                actionState = directionX>0?ActionState.WALK_RIGHT:ActionState.WALK_LEFT;
                this.setIcon(directionX>0?walk_right:walk_left);
            }
        }

        
        double probability = Math.random();

        //randomly scratch the screen
        if(probability >= clawThreshold && actionState != ActionState.CHASE){
            actionState = ActionState.SCRATCH;
            System.out.println("Scratching");
            scratchStartTime = System.currentTimeMillis();
            this.setIcon(scratch_gif);
            ClawMark mark = new ClawMark(getX(), getY(), 190, 190, 8); //width, height, existTime
            state.getWorldRef().addAnimal(mark);
            return;
        }

        //randomly change the direction
        if(probability >= changeThreshold){
            double randX = Math.random();
            double randY = Math.random();
            directionX = (randX / Math.sqrt(randX*randX + randY*randY))*2;
            directionY = (randY / Math.sqrt(randX*randX + randY*randY))*2;
            if(directionX > 0 && actionState != ActionState.WALK_RIGHT){
                this.setIcon(walk_right);
                actionState = ActionState.WALK_RIGHT;
                System.out.println("set icon to walk right");
            }
            else if(directionX < 0 && actionState != ActionState.WALK_LEFT){
                this.setIcon(walk_left);
                actionState = ActionState.WALK_LEFT;
                System.out.println("set icon to walk left");
            }
            System.out.println("Direction changed: "+directionX+" "+directionY);
        }

        //check if the cat hits the edge
        if(getX() > screenWidth-this.getWidth() || getX() < 0 || getY() > screenHeight-this.getHeight() || getY() < 0){
            directionX = -directionX;
            directionY = -directionY;
            this.setIcon(directionX>0?walk_right:walk_left);
            actionState = directionX>0?ActionState.WALK_RIGHT:ActionState.WALK_LEFT;
            System.out.println("hit edge, Direction changed: "+directionX+" "+directionY);
        }

        checkMouseChaseByCat(state.getComponents());

        //move the instance
        setLocation(getX() + speed*(int)directionX, getY()+ speed*(int)directionY);

    }
        
    @Override
    public void paintContent(Graphics g){}
    
    //determine chasing relative actions, including chasing and catching
    public void checkMouseChaseByCat(Component[] components){
        if(actionState == ActionState.CATCH){
            //recover the cat after catching the mouse for 2 seconds
            if(System.currentTimeMillis() - catchStartTime > 2000){
                actionState = directionX>0?ActionState.WALK_RIGHT:ActionState.WALK_LEFT;
                this.setIcon(directionX>0?walk_right:walk_left);
            }else{
                return;
            }
        }else{ 
            //check the relative position of the mouse and the cat
            for (Component component : components)
            {
                if(component instanceof Mouse)
                {
                    Mouse mouse = (Mouse)component;
                    catToMouseDistance = Math.sqrt(Math.pow((mouse.getX() - this.getX()), 2) + Math.pow((mouse.getY() - this.getY()), 2));
                    //check every frame if the mouse is being CHASED by the cat
                    if(mouse.isVisible() == true && catToMouseDistance < distanceThreshold)
                    {
                        System.out.println("Chasing mouse");
                        directionX = (mouse.getX() - this.getX()) / (0.5*catToMouseDistance);
                        directionY = (mouse.getY() - this.getY()) / (0.5*catToMouseDistance);
                        if(actionState != ActionState.CHASE){
                            this.setIcon(directionX > 0?run_right:run_left);
                            actionState = ActionState.CHASE;
                            speed = 2;
                        }
                    }else if(mouse.isVisible() == false || catToMouseDistance > distanceThreshold){
                        //change from chasing to walking
                        if(actionState == ActionState.CHASE || speed == 2){
                            this.setIcon(directionX>0?walk_right:walk_left);
                            System.out.println("Stop chasing");
                            actionState = directionX>0?ActionState.WALK_RIGHT:ActionState.WALK_LEFT;
                            speed = 1;
                        }
                    }

                    //check if the mouse is CAUGHT by the cat
                    if(mouse.isVisible() == true && 
                    this.getX() < mouse.getX() + mouse.getWidth()/2 && 
                    this.getX() + this.getWidth()/2 > mouse.getX() && 
                    this.getY() < mouse.getY() + mouse.getHeight()/2 && 
                    this.getY() + this.getHeight()/2 > mouse.getY() &&
                    actionState != ActionState.CATCH)
                    {
                        System.out.println("Mouse caught by cat");
                        actionState = ActionState.CATCH;
                        catchStartTime = System.currentTimeMillis();
                        this.setIcon(directionX>0?catched_right:catched_left);
                    }
                    return;
                }
            }
            System.out.println("No mouse found");
        }
    }
}
