package desktoppet.ui;

import java.awt.*;
import javax.swing.*;


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

        setLayout(null);
    }
}