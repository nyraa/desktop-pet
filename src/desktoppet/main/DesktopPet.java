package desktoppet.main;

import desktoppet.animals.Cat.Cat;
import desktoppet.animals.DVD.DVD;
import desktoppet.control.World;


public class DesktopPet
{
    public static void main(String[] args)
    {
        World world = new World();
        DVD dvd = new DVD(0, 0);
        world.addAnimal(dvd);
        Cat cat = new Cat(0, 0, 100, 100);
        world.addAnimal(cat);
        world.start();
    }
}

