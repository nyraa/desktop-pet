package desktoppet.animals.Cat;

import java.awt.Image;

import java.awt.Graphics;
import javax.swing.ImageIcon;

import desktoppet.control.State;
import desktoppet.model.Animal;

public class ClawMark extends Animal
{
    ImageIcon clawMark_gif = null;
    long createTime = 0;
    int existTime = 0;
    private boolean deleteFlag = false;
    private boolean cleanFlag = false;

    public ClawMark(int x, int y, int width, int height, int existTime)
    {
        super(x, y, width, height);
        clawMark_gif = new ImageIcon(getClass().getResource("/res/desktoppet/animals/Cat/clawMark_noloop.gif"));
        clawMark_gif.setImage(clawMark_gif.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        this.existTime = existTime;
        this.setIcon(clawMark_gif);
        createTime = System.currentTimeMillis();
        System.out.println("ClawMark created");
    }

    @Override
    public void paintContent(Graphics g)
    {
        if(deleteFlag)
        {
            System.out.println("ClawMark graphic cleared");
            // mark as clean
            cleanFlag = true;
        }
    }

    @Override
    public void update(State state){
        if(cleanFlag)
        {
            state.getWorldRef().deleteAnimal(this);
            System.out.println("ClawMark deleted");

            // escape from this condition for safety
            cleanFlag = false;
            return;
        }
        if(System.currentTimeMillis() - createTime > existTime * 1000 && !deleteFlag)
        {
            System.out.println("ClawMark marked for deletion");

            // remove the claw mark safely
            this.setIcon(null);
            this.repaint();

            // wait for clean signal
            this.deleteFlag = true;
        }
    }
}
