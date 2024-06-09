package desktoppet.main;

import desktoppet.animals.Test;
import desktoppet.animals.DVD.DVD;
import desktoppet.model.Animal;
import desktoppet.ui.Window;

import java.awt.Dimension;
import java.awt.Toolkit;

public class DesktopPet
{
    public static void main(String[] args)
    {
        Window window = new Window();
        setAnimal(window, "cat", 0, 0, 100, 100);
        DVD dvd = new DVD(0, 0);
        window.add(dvd);
        window.setVisible(true);
    }

    public static void setAnimal(Window window, String animalType, int x, int y, int width, int height)
    {
        Animal animal = null;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        y = screenSize.height - height;
        if(animalType.equals("cat"))
        {
            animal = new Test(x, y, width, height);
        }
        
        window.add(animal);
    }
    
}

