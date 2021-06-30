package Animation;

import javax.swing.*;
import java.awt.*;

public class Bar extends JLabel {

        ImageIcon bar ;
        int id ;
        int x;
        int y;
        public Bar()
        {

            this.x=250 ;
            this.y=161;

            bar=new ImageIcon("C:\\Users\\Lenovo\\Desktop\\db\\db\\src\\main\\resources\\bar.png");//bar
            this.setIcon(bar);
            Dimension size = this.getPreferredSize();
            setBounds(x, y, size.width, size.height);

        }
        public void BarDow()
        {

            this.x=250 ;
            this.y=161 ;
            bar=new ImageIcon("C:\\Users\\Lenovo\\Desktop\\db\\db\\src\\main\\resources\\bar.png");//bar
            this.setIcon(bar);
            Dimension size = this.getPreferredSize();
            setBounds(x, y, size.width, size.height);

        }
        public void BarUp()
        {

            this.x=250 ;
            this.y=161 ;
            bar=new ImageIcon("C:\\Users\\Lenovo\\Desktop\\db\\db\\src\\main\\resources\\bar.png");//bar
            this.setIcon(bar);
            //Dimension size = this.getPreferredSize();
            setBounds(x, y, 20,20 );

        }



        public void setIconparking(int i)
        {
            bar=new ImageIcon("C:\\Users\\Lenovo\\Desktop\\db\\db\\src\\main\\resources\\bar.png");//bar
            this.setIcon(bar);
        }
        public void setIconExitparking(int id)
        {
            bar=new ImageIcon("C:\\Users\\Lenovo\\Desktop\\db\\db\\src\\main\\resources\\car"+id+".png");
            this.setIcon(bar);
        }



}
