package desktoppet.animals.Cat;

import java.awt.Image;

import java.awt.Graphics;
import javax.swing.ImageIcon;

import desktoppet.control.State;
import desktoppet.model.Animal;

import java.util.Date;

public class ClawMark extends Animal
{
    ImageIcon clawMark_gif = null;
    ImageIcon clawMark_static = null;
    double createTime = 0;
    int existTime = 0;

    public ClawMark(int x, int y, int width, int height, int existTime)
    {
        super(x, y, width, height);
        clawMark_gif = new ImageIcon(getClass().getResource("clawMark.gif"));
        clawMark_gif.setImage(clawMark_gif.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        clawMark_static = new ImageIcon(getClass().getResource("clawMark.png"));
        clawMark_static.setImage(clawMark_static.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        this.existTime = existTime;
        this.setIcon(clawMark_gif);
        createTime = new Date().getTime();
        System.out.println(createTime);
    }

    @Override
    public void paintContent(Graphics g){}

    @Override
    public void update(State state){
        System.out.println(createTime);
        Date now = new Date();
        if((int)((now.getTime() - createTime)) >= 3600){
            this.setIcon(clawMark_static);
        }
        else this.setIcon(clawMark_gif);
    }
}
