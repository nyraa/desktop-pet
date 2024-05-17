package desktoppet;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Graphics;

public abstract class Animal extends JComponent
{
    private int x;
    private int y;
    private int width;
    private int height;
    public Animal()
    {
        super();
        setOpaque(false);
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    // getter and setter
    public void move(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public void moveX(int x)
    {
        this.x = x;
    }
    public void moveY(int y)
    {
        this.y = y;
    }
    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }
    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
    public Dimension getPreferredSize()
    {
        return new Dimension(this.width, this.height);
    }

    // abstract method
    abstract public void update(State state);
    @Override
    public abstract void paintComponent(Graphics g);
}
