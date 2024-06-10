package desktoppet.main;

import desktoppet.animals.Cat.Cat;
import desktoppet.control.World;

public class DesktopPet
{
    public static void main(String[] args)
    {
        World world = new World();
        Cat.entry(world);   // cat should spawn themselves
        world.start();
    }
}

