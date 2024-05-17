package desktoppet.main;

import desktoppet.animals.Test;
import desktoppet.ui.Window;

public class DesktopPet
{
    public static void main(String[] args)
    {
        Window window = new Window();
        Test test = new Test(0, 0, 100, 100);
        window.add(test);
        window.setVisible(true);
    }
}