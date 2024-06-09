package desktoppet.control;

import desktoppet.ui.Window;
import desktoppet.model.Animal;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;

public class World
{
    private Window window;
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
            Container contentPane = window.getContentPane();
            Component[] components = contentPane.getComponents();
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
        window = new Window();
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
        window.add(animal);
    }
    public void deleteAnimal(Animal animal)
    {
        window.remove(animal);
    }
    public Window getWindow()
    {
        return window;
    }
}
