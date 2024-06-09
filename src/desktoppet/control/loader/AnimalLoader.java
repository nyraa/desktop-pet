package desktoppet.control.loader;

import desktoppet.control.World;

import java.util.jar.*;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class AnimalLoader
{
    public static void LoadAnimalFromJar(String path, World world) throws Exception
    {
        Class<?> loadedClass = LoadJar(path);
        Method entryMethod = loadedClass.getMethod("entry", World.class);
        entryMethod.invoke(null, world);
    }
    private static Class<?> LoadJar(String path) throws Exception
    {
        JarFile jarFile = new JarFile(path);
        Manifest manifest = jarFile.getManifest();
        String mainClass = manifest.getMainAttributes().getValue("Main-Class");
        jarFile.close();
        if(mainClass != null)
        {
            URL jarUrl = new File(path).toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[] {jarUrl}, AnimalLoader.class.getClassLoader());

            Class<?> loadedClass = classLoader.loadClass(mainClass);
            classLoader.close();
            return loadedClass;
        }
        else throw new Exception("Main-Class not found in manifest");
    }
}
