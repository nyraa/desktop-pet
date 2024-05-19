package desktoppet.animals;

import desktoppet.control.State;
import desktoppet.model.Animal;

import java.awt.Graphics;

public class Test extends Animal
{
    int controlDirection = 1;

    public Test(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }

    @Override
    public void update(State state)
    {
        //chaeck if the circle is out of the screen
        if (getX() > (state.getScreenWidht() - this.getWidth()) || getX() < 0)
        {
            controlDirection *= -1;
        }
        //move the circle
        setLocation(getX() + 5*controlDirection, getY());
    }

    @Override
    public void paintContent(Graphics g)
    {
        int x = 50; // x-coordinate of the circle's center
        int y = 50; // y-coordinate of the circle's center
        int radius = 30; // radius of the circle
        
        g.setColor(java.awt.Color.RED);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}
