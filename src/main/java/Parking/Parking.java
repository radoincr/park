package Parking;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Semaphore;


public class Parking extends JPanel {
    ImageIcon image;
    static boolean[] blocEtat ; //true=full & false=empty
    static Semaphore semaphoreEntree ;
    static Semaphore semaphoreSortie ;

    public Parking(){
        semaphoreEntree =new Semaphore(1,true);
        semaphoreSortie =new Semaphore(1,false);
        blocEtat =new boolean[4];
        for(int i=0 ; i<blocEtat.length;i++)
        {
            blocEtat[i]=false ;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        image =new ImageIcon("C:\\Users\\Lenovo\\Desktop\\db\\db\\src\\main\\resources\\road.png");
        image.paintIcon(this, g, WIDTH,WIDTH);

    }

    }

