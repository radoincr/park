
import DataBase.CarDAO;
import Parking.Car;
import Parking.Parking;
import Animation.*;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


import java.util.Random;



public class MyThread implements Runnable{

        /**
         * time to wait after acquiring a "rampe"
         */
        TimeUnit unit = null;
        long sleepTime = 0;
        Car car;
        ImageIcon image;
        static boolean notExit = false;
        Bar bar;

        /**
         * Initial Available spaces
         */
        private static int NBR_PLACES = 2;
        private static int NBR_RAMPE = 1;

        /**
         * Semaphores
         */
        private static Semaphore semPlace = new Semaphore(NBR_PLACES, true);
        private static Semaphore semRampe = new Semaphore(NBR_RAMPE, true);
        private static CarDAO carDao;

        public MyThread(Car car, long time, TimeUnit unit, Bar b) {
            this.unit = unit;
            this.car = car;
            this.sleepTime = time;
            this.bar = b;
            try {
                this.carDao = new CarDAO();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        /**
         * reference time
         */
        private static final long referenceTime = System.currentTimeMillis();

        private String getAccesVoitureDesc() {
            return "[" + (System.currentTimeMillis() - referenceTime) + "] (Proc : " + Thread.currentThread().getName() + ")";
        }

        public void run() {
            Random rand = new Random(); //instance of random class
            int upperbound = 25;
            //generate random values from 0-24
            int int_random = rand.nextInt(upperbound);
            try {
                System.out.println(getAccesVoitureDesc() + " veut rentrer dans le parking !");
                this.entrer_parking();

                this.unit.sleep(6000);
                System.out.println(getAccesVoitureDesc() + " veut sortir du parking !");
                this.sortir_parking();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        public void entrer_parking() throws InterruptedException {
            try {
                semPlace.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getAccesVoitureDesc() + " a donnÃ© sa carte, ATTENTE");
            try {
                this.unit.sleep(this.sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(getAccesVoitureDesc() + " a fini son attente");
            }
            semRampe.acquire();

            System.out.println(getAccesVoitureDesc() + " passe sur la rampe");
            semRampe.release();
            bar.BarUp();


            Animation.moveIN(this.car, 342);
            try {
                this.carDao.saveEnter(this.car);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Main.reloadDataTable();
            System.out.println(getAccesVoitureDesc() + " est descendu de la rampe et est gare a sa place !");
            bar.BarDow();
            Animation.parking(this.car);



        }

        public void sortir_parking() throws InterruptedException {
            semRampe.acquire();
            Animation.exitParking(this.car);
            System.out.println(getAccesVoitureDesc() + " passe sur la rampe (pour sortir)");

            semRampe.release();
            Animation.moveOut(this.car);
            try {
                this.carDao.saveSortie(this.car.getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Main.reloadDataTable();
            System.out.println(getAccesVoitureDesc() + " est descendu de la rampe (pour sortir)");
            semPlace.release();
            System.out.println(getAccesVoitureDesc() + " est sorti du parking...Place liberÃ©e !");
        }


}
