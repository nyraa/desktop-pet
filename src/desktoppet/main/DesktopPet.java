package desktoppet.main;

import desktoppet.animals.Test;
import desktoppet.animals.Cat;

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
        window.setVisible(true);
    }

    public static void setAnimal(Window window, String animalType, int x, int y, int width, int height)
    {
        Animal animal = null;
        Animal test  = null;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        y = screenSize.height - height;
        if(animalType.equals("cat"))
        {
            test = new Test(x, y, width, height);
            animal = new Cat(x, y, width, height);
        }
        
        window.add(animal);
    }
    
}

