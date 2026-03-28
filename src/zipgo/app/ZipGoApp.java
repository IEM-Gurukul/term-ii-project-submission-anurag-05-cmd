package zipgo.app;

import zipgo.model.*;
import zipgo.service.RideManager;
import zipgo.storage.FileManager;
import zipgo.exception.*;

import java.util.Scanner;

public class ZipGoApp {

    public static void main(String[] args) throws InterruptedException {

        RideManager rm = new RideManager();
        Scanner sc = new Scanner(System.in);

        Vehicle car = new Car("WB01AB1234");
        Vehicle bike = new Bike("WB02CD5678");

        Rider r1 = new Rider("R1", "Anurag", "9000000001");
        rm.addRider(r1);

        rm.addDriver(new Driver("D1", "Rahul", "9000000002", car));
        rm.addDriver(new Driver("D2", "Amit", "9000000003", bike));

        System.out.println("Welcome to ZipGo");

        while (true) {
            System.out.println("\n1. Book Ride");
            System.out.println("2. View Report");
            System.out.println("3. Exit");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter Rider ID: ");
                String id = sc.next();

                Rider rider = rm.getRider(id);
                if (rider == null) {
                    System.out.println("Invalid ID");
                    continue;
                }

                System.out.print("Enter distance: ");
                double dist = sc.nextDouble();

                try {
                    Ride ride = rm.requestRide("R" + System.currentTimeMillis(), id, dist);

                    System.out.println("\nDriver assigned:");
                    ride.displayRideDetails();

                    System.out.print("Cancel ride? (y/n): ");
                    String cancel = sc.next();

                    if (cancel.equalsIgnoreCase("y")) {
                        rm.cancelRide(ride.getRideId());
                        ride.displayRideDetails();
                        continue;
                    }

                    System.out.println("\nStarting ride...");
                    Thread.sleep(2000);

                    rm.startRide(ride.getRideId());
                    ride.displayRideDetails();

                    System.out.println("\nRide in progress...");
                    Thread.sleep(3000);

                    rm.completeRide(ride.getRideId());
                    System.out.println("\nRide completed");
                    ride.displayRideDetails();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else if (choice == 2) {
                System.out.println("\nTotal rides: " + rm.getTotalRides());
                System.out.println("Total earnings: ₹" + rm.getTotalEarnings());
            } else {
                break;
            }
        }

        FileManager.saveRides(rm.getRides());
        sc.close();
    }
}