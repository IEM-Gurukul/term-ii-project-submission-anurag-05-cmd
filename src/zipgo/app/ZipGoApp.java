package zipgo.app;

import zipgo.model.*;
import zipgo.service.RideManager;
import zipgo.storage.FileManager;
import zipgo.exception.NoDriverAvailableException;

public class ZipGoApp {

    public static void main(String[] args) {

        RideManager rideManager = new RideManager();

        // Create Vehicles
        Vehicle car1 = new Car("WB01AB1234");
        Vehicle bike1 = new Bike("WB02CD5678");

        // Register Riders
        Rider r1 = new Rider("R1", "Anurag", "9000000001");
        rideManager.addRider(r1);

        // Register Drivers with Vehicles
        Driver d1 = new Driver("D1", "Rahul", "9000000002", car1);
        Driver d2 = new Driver("D2", "Amit", "9000000003", bike1);

        rideManager.addDriver(d1);
        rideManager.addDriver(d2);

        try {
            System.out.println("\nRIDE REQUESTED");
            Ride ride = rideManager.requestRide("Ride1", "R1", 10.5);
            ride.displayRideDetails();

            System.out.println("\nRIDE STARTED");
            rideManager.startRide("Ride1");
            ride.displayRideDetails();

            System.out.println("\nRIDE COMPLETED");
            rideManager.completeRide("Ride1");
            ride.displayRideDetails();

        } catch (NoDriverAvailableException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nALL RIDES:");
        rideManager.displayAllRides();

        // Save to CSV
        FileManager.saveRides(rideManager.getRides());
    }
}