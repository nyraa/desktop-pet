package desktoppet;

import javax.swing.JComponent;
import java.awt.Graphics;

public abstract class Animal extends JComponent
{
    public Animal()
    {
        super();
    }
    public Animal(int x, int y, int width, int height)
    {
        super();
        setOpaque(false);

        this.setLocation(x, y);
        this.setSize(width, height);
    }

    // abstract method
    abstract public void update(State state);
    abstract public void paintContent(Graphics g);
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        paintContent(g);
    }
}
