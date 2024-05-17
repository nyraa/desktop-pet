public class State
{
    private int mouseX;
    private int mouseY;
    public State()
    {
        this.mouseX = 0;
        this.mouseY = 0;
    }

    // getter and setter
    public void setMouse(int x, int y)
    {
        this.mouseX = x;
        this.mouseY = y;
    }
}
