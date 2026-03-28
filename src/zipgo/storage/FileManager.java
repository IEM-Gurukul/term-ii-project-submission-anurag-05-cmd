package zipgo.storage;

import zipgo.model.Ride;

import java.io.*;
import java.util.List;

public class FileManager {

    private static final String FILE_NAME = "rides.csv";

    // Save all rides to CSV file
    public static void saveRides(List<Ride> rides) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            // Write header
            writer.write("RideID,RiderID,DriverID,Distance,Fare,Status");
            writer.newLine();

            // Write ride data
            for (Ride ride : rides) {
                writer.write(formatRide(ride));
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving rides: " + e.getMessage());
        }
    }

    // Convert ride object to CSV format
    private static String formatRide(Ride ride) {
        return ride.getRideId() + "," +
               ride.getRider().getUserId() + "," +
               (ride.getDriver() != null ? ride.getDriver().getUserId() : "null") + "," +
               ride.getDistance() + "," +
               ride.getFare() + "," +
               ride.getStatus();
    }
}