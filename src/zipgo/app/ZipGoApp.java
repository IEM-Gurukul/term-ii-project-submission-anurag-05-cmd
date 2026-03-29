package zipgo.app;

import zipgo.model.*;
import zipgo.service.RideManager;

import java.util.Scanner;

public class ZipGoApp {

    public static void main(String[] args) {

        RideManager rm = new RideManager();
        Scanner sc = new Scanner(System.in);

        Vehicle car = new Car("WB01AB1234");

        Rider rider = new Rider("R1", "Anurag", "9000000001");
        Driver driver = new Driver("D1", "Rahul", "9000000002", car);

        rm.addRider(rider);
        rm.addDriver(driver);

        Thread driverThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);

                    if (driver.isAvailable()) {
                        Ride ride = rm.assignRideToDriver(driver);

                        if (ride != null) {
                            System.out.println("\nDriver accepted ride: " + ride.getRideId());

                            Thread.sleep(2000);
                            rm.startRide(ride.getRideId());
                            System.out.println("Ride started");

                            Thread.sleep(3000);
                            rm.completeRide(ride.getRideId());
                            System.out.println("Ride completed");

                            ride.displayRideDetails();
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        driverThread.start();

        System.out.println("ZipGo is running");

        while (true) {
            System.out.println("\n1. Request Ride");
            System.out.println("2. Toggle Driver Availability");
            System.out.println("3. Show Report");
            System.out.println("4. Exit");

            int ch = sc.nextInt();

            if (ch == 1) {
                System.out.print("Enter distance: ");
                double dist = sc.nextDouble();

                rm.requestRide("R" + System.currentTimeMillis(), "R1", dist);

            } else if (ch == 2) {
                driver.setAvailable(!driver.isAvailable());
                System.out.println("Driver availability: " + driver.isAvailable());

            } else if (ch == 3) {
                System.out.println("Total rides: " + rm.getTotalRides());
                System.out.println("Total earnings: ₹" + rm.getTotalEarnings());
            } else {
                break;
            }
        }

        sc.close();
    }
}