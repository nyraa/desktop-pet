package desktoppet;

import desktoppet.animals.Test;

public class Pet
{
    public static void main(String[] args)
    {
        Window window = new Window();
        Test test = new Test(0, 0, 100, 100);
        window.add(test);
        window.setVisible(true);
    }
}