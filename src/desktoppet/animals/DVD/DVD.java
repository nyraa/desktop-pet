package desktoppet.animals.DVD;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;

import desktoppet.model.Animal;
import desktoppet.control.State;

public class DVD extends Animal
{
    private static final int width = 320;
    private static final int height = 145;
    private BufferedImage DVDIcon;
    private final int volacity = 5;
    private int speedX = 5;
    private int speedY = 5;
    private Color color = Color.RED;
    // image compose
    private BufferedImage tmpImage;
    private Graphics2D tmpG;
    public DVD(int x, int y)
    {
        super(x, y, width, height);
        try
        {
            BufferedImage tmpDVDIcon = ImageIO.read(getClass().getResource("DVD.png"));
            // resize DVDIcon to width and height
            DVDIcon = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = DVDIcon.createGraphics();
            g.drawImage(tmpDVDIcon, 0, 0, width, height, null);
            g.dispose();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        tmpImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        tmpG = tmpImage.createGraphics();
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
        tmpG.setComposite(java.awt.AlphaComposite.SrcOver);
        tmpG.drawImage(DVDIcon, 0, 0, width, height, null);
        tmpG.setColor(color);
        tmpG.setComposite(java.awt.AlphaComposite.SrcIn);
        tmpG.fillRect(0, 0, width, height);
        g.drawImage(tmpImage, 0, 0, width, height, null);
    }
}
