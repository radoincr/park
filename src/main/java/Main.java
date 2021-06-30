import Animation.Bar;
import DataBase.CarDAO;
import DataBase.CarDB;
import Parking.Parking;
import Parking.Car;


import javax.management.openmbean.CompositeData;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    private static JFrame frame1;
    private static JTable jt;
    private static CarDAO carDAO;

    public static Boolean reloadDataTable(){

        frame1.getContentPane().removeAll();
        frame1.repaint();
        jt.repaint();
        String column[]={"ID","CAR_ID","SITUATION","TIME ENTRER", "TIME SORTIE", "DUREE"};
        JTable jt = new JTable(setData(),column);
        jt.setBounds(500,600,600,400);
        JScrollPane sp=new JScrollPane(jt);
        frame1.add(sp);
        frame1.setSize(600,450);
        frame1.setLocation(930,400);
        frame1.setVisible(true);
        return true;
    }
    public static String[][] setData(){
        ArrayList<CarDB> st = null;
        try {
            carDAO = new CarDAO();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            st =  carDAO.getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String data[][] = new String[st.size()][6];

        int i = 0;
        for (CarDB car:
             st) {
                data[i][0] = String.valueOf(car.getId());
                data[i][1] = String.valueOf(car.getCar_id());
                data[i][2] = String.valueOf(car.getSituation());
                data[i][3] = String.valueOf(car.getTime_entrer());
                data[i][4] = String.valueOf(car.getTime_sortie());
                if(car.getTime_sortie() != null)
                    data[i][5] = String.valueOf(new Time(car.getTime_sortie().getTime() - car.getTime_entrer().getTime()));
                else
                    data[i][5] = "null";

            i++;
        }
        return data;
    }

    public static boolean setCarId(int car_id){
        try {
            FileWriter myWriter = new FileWriter("C:\\Users\\Lenovo\\Desktop\\db\\db\\src\\main\\resources\\car_id.txt");
            myWriter.write(String.valueOf(car_id));
            myWriter.close();
            return true;

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }

    public static int getCarId(){

        int car_id = 0;
        try {
            File myObj = new File("C:\\Users\\Lenovo\\Desktop\\db\\db\\src\\main\\resources\\car_id.txt");
            Scanner myReader = new Scanner(myObj);
            car_id = Integer.valueOf(myReader.nextLine());
            myReader.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            System.out.println("An error occurred.");
        }
        setCarId(car_id+1);
        return car_id;
    }


        public static void main(String[] args) {

        JFrame frame = new JFrame("Projet Parking");

        Parking panel = new Parking();
        frame.setContentPane(panel);
        panel.setLayout(null);
        frame.setSize(1080, 550);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame1 = new JFrame("Data Table");

        String column[]={"ID","CAR_ID","SITUATION","TIME ENTRER", "TIME SORTIE", "DUREE"};
        jt=new JTable(setData(),column);
        jt.setBounds(500,600,600,400);
        JScrollPane sp=new JScrollPane(jt);
        frame1.add(sp);
        frame1.setSize(600,450);
        frame1.setLocation(930,400);

        Car c1 = new Car(getCarId(), 160,1);
        panel.add(c1);
        Car c2 = new Car(getCarId(), 220, 2);
        panel.add(c2);
        Car c3 = new Car(getCarId(), 300, 3);
        panel.add(c3);
        Car c4 = new Car(getCarId(), 360, 4);
        panel.add(c4);

        Bar bar = new Bar();
        panel.add(bar);

        Thread p1 = new Thread(new MyThread(c1, 10000, TimeUnit.MILLISECONDS, bar), "voiture 1");
        Thread p2 = new Thread(new MyThread(c2, 5000, TimeUnit.MILLISECONDS, bar), "voiture 2");
        Thread p3 = new Thread(new MyThread(c3, 2000, TimeUnit.MILLISECONDS, bar), "voiture 3");
        Thread p4 = new Thread(new MyThread(c4, 500, TimeUnit.MILLISECONDS, bar), "voiture 4");
        p1.start();
        p2.start();
        p3.start();
        p4.start();

        frame.setVisible(true);
        frame1.setVisible(true);
    }
}
