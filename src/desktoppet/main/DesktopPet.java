package desktoppet.main;

import desktoppet.animals.Cat.Cat;
import desktoppet.control.World;
import desktoppet.control.loader.AnimalLoader;


public class DesktopPet
{
    public static void main(String[] args)
    {
        World world = new World();
        Cat cat = new Cat(100, 100, 100, 100);
        world.addAnimal(cat);
        world.start();
    }
}

