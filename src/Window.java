import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Window extends JFrame
{
    public Window()
    {
        super("Window");

        // disable window title
        setUndecorated(true);

        // set transparent background
        setBackground(new Color(0, true));

        // set window to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // set window to always on top
        setAlwaysOnTop(true);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}