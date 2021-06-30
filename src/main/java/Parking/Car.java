package Parking;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Car extends JLabel {

    ImageIcon car ;
    public int id ;
    public int x;
    public int y;
    int  i;

    public int getId() {
        return id;
    }

    public Car(int id, int y, int i)
    {
        this.id=id;
        this.x=90 ;
        this.y=y ;
        this.i = i;
        car=new ImageIcon("C:\\Users\\Lenovo\\Desktop\\db\\db\\src\\main\\resources\\car"+ (this.id - (this.id-i++)) +".png");

        this.setIcon(car);
        Dimension size = this.getPreferredSize();
        setBounds(x, y, size.width, size.height);
    }


    public void setIconparking()
    {
        car=new ImageIcon("C:\\Users\\Lenovo\\Desktop\\db\\db\\src\\main\\resources\\car"+(this.id - (this.id-i))+"1.png");
        this.setIcon(car);
        Dimension size = this.getPreferredSize();
        setBounds(x, y, size.width, size.height);
    }
    public void setIconExitparking()
    {
        car=new ImageIcon("C:\\Users\\Lenovo\\Desktop\\db\\db\\src\\main\\resources\\car"+(this.id - (this.id-i))+".png");
        this.setIcon(car);
        Dimension size = this.getPreferredSize();
        setBounds(x, y, size.width, size.height);
    }

}


