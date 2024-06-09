package desktoppet.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import desktoppet.control.World;


public class Window extends JFrame
{
    World worldRef;
    public Window(World worldRef)
    {
        super("Window");

        this.worldRef = worldRef;

        // disable window title
        setUndecorated(true);

        // set transparent background
        setBackground(new Color(0, true));

        // set window to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // set window to always on top
        setAlwaysOnTop(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setLayout(null);

        // hook event when window is closed
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                SettingsWindow settingsWindow = new SettingsWindow(worldRef);
                settingsWindow.setVisible(true);
            }
        });
    }
}