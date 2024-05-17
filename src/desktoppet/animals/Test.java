package desktoppet.animals;

import desktoppet.control.State;
import desktoppet.model.Animal;

import java.awt.Graphics;

public class Test extends Animal
{
    public Test(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }

    @Override
    public void update(State state)
    {

    }

    @Override
    public void paintContent(Graphics g)
    {
        
        int x = 50; // x-coordinate of the circle's center
        int y = 50; // y-coordinate of the circle's center
        int radius = 30; // radius of the circle
        
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}
