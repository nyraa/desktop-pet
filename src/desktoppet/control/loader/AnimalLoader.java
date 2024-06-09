package desktoppet.control.loader;

import java.util.jar.*;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class AnimalLoader
{
    public static Class<?> LoadAnimalJar(String path)
    {
        try
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
                // Object instance = loadedClass.getConstructor().newInstance();
                Method entryMethod = loadedClass.getMethod("entry");
                entryMethod.invoke(null);
                classLoader.close();
                return loadedClass;
            }
            else
            {
                System.err.println("Main-Class not found in manifest file");
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
