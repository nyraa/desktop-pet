package desktoppet.control;

import desktoppet.ui.Window;
import desktoppet.model.Animal;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import java.awt.*;

public class World
{
    private Window window;
    private JPanel panel;
    private Timer timer;
    private State state;
    private TimerTask updateTask = new TimerTask()
    {
        @Override
        public void run()
        {
            // set screen size
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            state.setScreenSize(screenSize.width, screenSize.height);

            // set mouse position
            Point mousePosition = MouseInfo.getPointerInfo().getLocation();
            state.setMouse(mousePosition.x, mousePosition.y);

            // update animals
            Component[] components = getComponents();
            for (Component component : components)
            {
                if(component instanceof Animal)
                {
                    Animal animal = (Animal)component;
                    animal.update(state);
                }
            }
        }
    };
    public World()
    {
        window = new Window(this);
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        // panel.setBackground(new Color(0, 0, 0, 0));
        window.add(panel, BorderLayout.CENTER);
        timer = new Timer();
        state = new State(this);
    }
    public void start()
    {
        timer.scheduleAtFixedRate(updateTask, 0, 1000/60);
        window.setVisible(true);
    }
    public void stop()
    {
        window.setVisible(false);
        timer.cancel();
    }
    public void addAnimal(Animal animal)
    {
        panel.add(animal);
    }
    public void deleteAnimal(Animal animal)
    {
        panel.remove(animal);
    }
    public Window getWindow()
    {
        return window;
    }
    public Component[] getComponents()
    {
        return panel.getComponents();
    }
}
