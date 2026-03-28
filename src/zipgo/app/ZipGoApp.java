package zipgo.app;

import zipgo.model.*;
import zipgo.service.RideManager;
import zipgo.storage.FileManager;
import zipgo.exception.NoDriverAvailableException;

import java.util.Scanner;

public class ZipGoApp {

    public static void main(String[] args) {

        RideManager rideManager = new RideManager();
        Scanner sc = new Scanner(System.in);

        // Setup data
        Vehicle car1 = new Car("WB01AB1234");
        Vehicle bike1 = new Bike("WB02CD5678");

        Rider r1 = new Rider("R1", "Anurag", "9000000001");
        rideManager.addRider(r1);

        Driver d1 = new Driver("D1", "Rahul", "9000000002", car1);
        Driver d2 = new Driver("D2", "Amit", "9000000003", bike1);

        rideManager.addDriver(d1);
        rideManager.addDriver(d2);

        System.out.println("Welcome to ZipGo");

        while (true) {
            System.out.println("\nLogin as:");
            System.out.println("1. Rider");
            System.out.println("2. Driver");
            System.out.println("3. Exit");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter Rider ID: ");
                String riderId = sc.next();

                Rider rider = rideManager.getRider(riderId);
                if (rider == null) {
                    System.out.println("Invalid Rider ID");
                    continue;
                }

                riderMenu(rideManager, rider, sc);

            } else if (choice == 2) {
                System.out.print("Enter Driver ID: ");
                String driverId = sc.next();

                Driver driver = rideManager.getDriver(driverId);
                if (driver == null) {
                    System.out.println("Invalid Driver ID");
                    continue;
                }

                driverMenu(driver, sc);

            } else {
                break;
            }
        }

        FileManager.saveRides(rideManager.getRides());
        sc.close();
    }

    // Rider Menu
    private static void riderMenu(RideManager rm, Rider rider, Scanner sc) {
        System.out.println("\nWelcome Rider: " + rider.getName());

        System.out.print("Enter distance: ");
        double distance = sc.nextDouble();

        try {
            Ride ride = rm.requestRide("Ride" + System.currentTimeMillis(), rider.getUserId(), distance);

            System.out.println("Driver Assigned!");
            ride.displayRideDetails();

            System.out.println("Starting Ride...");
            rm.startRide(ride.getRideId());

            System.out.println("Completing Ride...");
            rm.completeRide(ride.getRideId());

            ride.displayRideDetails();

        } catch (NoDriverAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    // Driver Menu
    private static void driverMenu(Driver driver, Scanner sc) {
        System.out.println("\nWelcome Driver: " + driver.getName());
        System.out.println("1. Set Available");
        System.out.println("2. Set Unavailable");

        int ch = sc.nextInt();

        if (ch == 1) {
            driver.setAvailable(true);
            System.out.println("You are now AVAILABLE");
        } else {
            driver.setAvailable(false);
            System.out.println("You are now UNAVAILABLE");
        }
    }
}