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
        WALK,
        PLAY_LEFT,
        PLAY_RIGHT,
        SCRATCH, 
        CHASE,
        CATCH
    };
    private enum Direction
    {
        LEFT,
        RIGHT
    };
    private Direction animateDirection = Direction.RIGHT;
    private ActionState actionState = ActionState.WALK;
    private double directionX = 1;  // cos(theta)
    private double directionY = -1; // sin(theta)
    private double x = 0;           // x coordinate
    private double y = 0;           // y coordinate
    private static final double changeThreshold = 0.995;
    private static final double clawThreshold = 0.998;
    private static final int distanceThreshold = 300;
    private static final int walkingSpeed = 1;
    private static final int runningSpeed = 2;
    private int currentSpeed = 1;
    private long scratchStartTime = 0;
    private long catchStartTime = 0;
    private double catToMouseDistanceX = 0;
    private double catToMouseDistanceY = 0;

    private ImageIcon walk_right = null;
    private ImageIcon walk_left = null;
    private ImageIcon run_left = null;
    private ImageIcon run_right = null;
    private ImageIcon play_left = null;
    private ImageIcon play_right = null;
    private ImageIcon scratch_gif = null;
    private ImageIcon catched_left = null;
    private ImageIcon catched_right = null;

    public Cat(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        x = x;
        x = y;
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
    
    public double getDirectionX()
    {
        return directionX;
    }
    public double getDirectionY()
    {
        return directionY;
    }


    @Override
    public void update(State state)
    {
        changeState(state);
        changeDirection(state);
        walk();
        changeIcon();
    }

    private void changeState(State state)
    {
        // stall part: scratch, play, catch
        currentSpeed = walkingSpeed;
        if(actionState == ActionState.SCRATCH)
        {
            if(System.currentTimeMillis() - scratchStartTime < 3600)
            {
                return;
            }
        }
        if(actionState == ActionState.CATCH)
        {
            if(System.currentTimeMillis() - catchStartTime < 2000)
            {
                return;
            }
        }

        // if cursor on cat
        int mouseX = state.getMouseX();
        int mouseY = state.getMouseY();
        if(mouseX > getX() && mouseX < getX() + getWidth() / 2 && mouseY > getY() && mouseY < getY() + getHeight())
        {
            actionState = ActionState.PLAY_LEFT;
            return;
        }
        else if(mouseX >= getX() + getWidth() / 2 && mouseX < getX() + getWidth() && mouseY > getY() && mouseY < getY() + getHeight())
        {
            actionState = ActionState.PLAY_RIGHT;
            return;
        }

        // if mouse is near
        Component[] components = state.getComponents();
        for (Component component : components)
        {
            if(component instanceof Mouse)
            {
                Mouse mouse = (Mouse)component;
                if(mouse.isVisible())
                {
                    double catToMouseDistance = Math.sqrt(Math.pow((mouse.getX() - this.getX()), 2) + Math.pow((mouse.getY() - this.getY()), 2));
                    if(catToMouseDistance < distanceThreshold)
                    {
                        actionState = ActionState.CHASE;
                        currentSpeed = runningSpeed;
                        catToMouseDistanceX = mouse.getX() - this.getX();
                        catToMouseDistanceY = mouse.getY() - this.getY();
                        return;
                    }
                }
            }
        }
        actionState = ActionState.WALK;
    }
    private void walk()
    {
        if(actionState == ActionState.WALK || actionState == ActionState.CHASE)
        {
            if(directionX > 0)
            {
                animateDirection = Direction.RIGHT;
            }
            else
            {
                animateDirection = Direction.LEFT;
            }
            x += currentSpeed * directionX;
            y += currentSpeed * directionY;
            setLocation((int)x, (int)y);
        }
    }

    private void changeDirection(State state)
    {
        double random = Math.random();

        // if cat is chasing mouse
        if(actionState == ActionState.CHASE)
        {
            directionX = catToMouseDistanceX / Math.sqrt(Math.pow(catToMouseDistanceX, 2) + Math.pow(catToMouseDistanceY, 2));
            directionY = catToMouseDistanceY / Math.sqrt(Math.pow(catToMouseDistanceX, 2) + Math.pow(catToMouseDistanceY, 2));
            return;
        }
        
        // touch screen border
        boolean touchBorder = false;
        if(getX() < 0)
        {
            directionX = Math.abs(directionX);
            touchBorder = true;
        }
        if(getX() + getWidth() > state.getScreenWidth())
        {
            directionX = -Math.abs(directionX);
            touchBorder = true;
        }
        if(getY() < 0)
        {
            directionY = Math.abs(directionY);
            touchBorder = true;
        }
        if(getY() + getHeight() > state.getScreenHeight())
        {
            directionY = -Math.abs(directionY);
            touchBorder = true;
        }
        if(touchBorder)
        {
            return;
        }

        // if change direction
        if(random > changeThreshold)
        {
            // from -1 to 1
            int deg = (int)(Math.random() * 360);
            directionX = Math.cos(Math.toRadians(deg));
            directionY = Math.sin(Math.toRadians(deg));
            System.out.println("cat Direction changed: "+directionX+" "+directionY);
            return;
        }
    }

    @Override
    public void setIcon(Icon icon)
    {
        if(getIcon() != icon)
        {
            super.setIcon(icon);
        }
    }

    private void changeIcon()
    {
        switch(actionState)
        {
            case WALK:
                if(animateDirection == Direction.RIGHT)
                {
                    setIcon(walk_right);
                }
                else
                {
                    setIcon(walk_left);
                }
                break;
            case PLAY_LEFT:
                setIcon(play_left);
                break;
            case PLAY_RIGHT:
                setIcon(play_right);
                break;
            case SCRATCH:
                setIcon(scratch_gif);
                break;
            case CHASE:
                if(animateDirection == Direction.RIGHT)
                {
                    setIcon(run_right);
                }
                else
                {
                    setIcon(run_left);
                }
                break;
            case CATCH:
                if(animateDirection == Direction.RIGHT)
                {
                    setIcon(catched_right);
                }
                else
                {
                    setIcon(catched_left);
                }
                break;
        }
    }
        
    @Override
    public void paintContent(Graphics g){}
}
