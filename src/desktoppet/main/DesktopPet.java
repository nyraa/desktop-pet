package desktoppet.main;

import desktoppet.animals.DVD.DVD;
import desktoppet.control.World;
import desktoppet.ui.Window;


public class DesktopPet
{
    public static void main(String[] args)
    {
        World world = new World();
        DVD dvd = new DVD(0, 0);
        world.addAnimal(dvd);
        world.start();
    }
}

