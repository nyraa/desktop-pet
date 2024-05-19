package desktoppet.control;

public class State
{
    private int mouseX;
    private int mouseY;
    private int screenWidth;
    private int screenHeight;
    public State()
    {
        this.mouseX = 0;
        this.mouseY = 0;
        this.screenWidth = 0;
        this.screenHeight = 0;
    }

    // getter and setter
    public void setMouse(int x, int y)
    {
        this.mouseX = x;
        this.mouseY = y;
    }
    public int getMouseX()
    {
        return this.mouseX;
    }
    public int getMouseY()
    {
        return this.mouseY;
    }
    public void setScreenSize(int width, int height)
    {
        this.screenWidth = width;
        this.screenHeight = height;
    }
    public int getScreenWidht()
    {
        return this.screenWidth;
    }
    public int getScreenHeight()
    {
        return this.screenHeight;
    }
}
