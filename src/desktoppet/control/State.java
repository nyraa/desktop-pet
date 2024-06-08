package desktoppet.control;

import desktoppet.ui.Window;
public class State
{
    private int mouseX;
    private int mouseY;
    public Window window;
    public State(Window window)
    {
        this.mouseX = 0;
        this.mouseY = 0;
        this.window = window;
    }

    // getter and setter
    public void setMouse(int x, int y)
    {
        this.mouseX = x;
        this.mouseY = y;
    }
}
