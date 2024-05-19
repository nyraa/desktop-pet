package desktoppet.ui;

import java.awt.*;
import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;

import desktoppet.control.State;
import desktoppet.model.Animal;


public class Window extends JFrame
{
    Timer timer = new Timer();
    State state = new desktoppet.control.State();
    
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            state.setScreenSize(screenSize.width, screenSize.height);
            // update the window
            Container contentPane = getContentPane();
            Component[] components = contentPane.getComponents();
            for (Component component : components) {
                Animal animal = (Animal)component;
                animal.update(state);
            }
        }
    };


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

        //set timer
        timer.scheduleAtFixedRate(task, 0, 1000/60);
    }


}