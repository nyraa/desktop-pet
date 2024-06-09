package desktoppet.animals.DVD;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;

import desktoppet.model.Animal;
import desktoppet.control.State;

public class DVD extends Animal
{
    private ImageIcon icon;
    private final int volacity = 5;
    private int speedX = 5;
    private int speedY = 5;
    private Color color = Color.RED;
    public DVD(int x, int y)
    {
        super(x, y, 320, 145);
        icon = new ImageIcon(getClass().getResource("DVD.png"));
    }
    public void update(State state)
    {
        if(getX() > (state.getScreenWidth() - this.getWidth()))
        {
            speedX = -volacity;
            changeColor();
        }
        if(getX() < 0)
        {
            speedX = volacity;
            changeColor();
        }
        if(getY() > (state.getScreenHeight() - this.getHeight()))
        {
            speedY = -volacity;
            changeColor();
        }
        if(getY() < 0)
        {
            speedY = volacity;
            changeColor();
        }
        setLocation(getX() + speedX, getY() + speedY);
    }
    private void changeColor()
    {
        // change to a random color
        int r = (int)(Math.random() * 256);
        int g = (int)(Math.random() * 256);
        int b = (int)(Math.random() * 256);
        color = new Color(r, g, b);
    }
    public void paintContent(Graphics g)
    {
        // draw icon with color
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
