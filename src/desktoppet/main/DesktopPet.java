package desktoppet.main;

import desktoppet.control.World;
import desktoppet.model.Animal;

import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


public class DesktopPet
{
    public static void main(String[] args)
    {
        System.out.println("Classpaths:");
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(System.getProperty("path.separator"));
        for (String entry : classpathEntries) {
            System.out.println(entry);
        }

        System.out.println("Current Working Directory (CWD):");
        String cwd = System.getProperty("user.dir");
        System.out.println(cwd);
        
        World world = new World();
        loadAnimals(world);
        world.start();
    }
    public static void loadAnimals(World world)
    {
        System.out.println("Loading animals");
        Reflections reflections = new Reflections("desktoppet.animals");

        Set<Class<? extends Animal>> classes = reflections.getSubTypesOf(Animal.class);
        for(Class<? extends Animal> clazz : classes)
        {
            try
            {
                Method entryMethod = clazz.getMethod("entry", World.class);
                if(Modifier.isStatic(entryMethod.getModifiers()))
                {
                    System.out.println("Loading " + clazz.getName());
                    entryMethod.invoke(null, world);
                }
            }
            catch(NoSuchMethodException e)
            {
                System.out.println("No entry method found for class " + clazz.getName());
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("Animals loaded");
    }
}

