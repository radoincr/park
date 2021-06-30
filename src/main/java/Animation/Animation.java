package Animation;

import Parking.Car;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fatah
 */
public class Animation {

    static boolean notExit = true;

    public static void moveIN(Car c, int xMax) {

        if (notExit == true) {
            while (c.x <= xMax) {
                c.x += 11;
                c.setLocation(c.x, c.y);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            notExit = false;
        } else {
            while (c.x <= xMax + 174) {
                c.x += 11;
                c.setLocation(c.x, c.y);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            notExit = true;
        }

    }

    public static void parking(Car c) {
        while (c.y >= 30) {
            c.setIconparking();
            c.y -= 10;
            c.setLocation(c.x, c.y);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public static void exitParking(Car c) {

        while (c.y <= 290) {
            c.y += 10;
            c.setLocation(c.x, c.y);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public static void moveOut(Car c) {
        while (c.x <= 1080) {
            c.setIconExitparking();
            c.x += 10;
            c.setLocation(c.x, c.y);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        c.x = 90;
        c.y = 260;

    }
}