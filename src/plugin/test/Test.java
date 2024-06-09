package plugin.test;

import java.awt.Graphics;

import desktoppet.control.State;
import desktoppet.model.Animal;
import java.io.*;

public class Test extends Animal
{
    public Test()
    {
        super(0, 0, 0, 0);
        System.out.println("Test class construced");
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("somefile.txt");
        if (inputStream != null) {
            System.out.println("File found");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("File not found");
        }
    }
    public static void entry()
    {
        System.out.println("Test entry");
        Test test = new Test();
    }
    @Override
    public void update(State state)
    {
        
    }

    @Override
    public void paintContent(Graphics g)
    {
        
    }
}
