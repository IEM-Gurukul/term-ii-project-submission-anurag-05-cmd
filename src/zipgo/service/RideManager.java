package zipgo.service;

import zipgo.model.*;
import zipgo.exception.NoDriverAvailableException;

import java.util.*;

public class RideManager {

    private Map<String, Rider> riders = new HashMap<>();
    private Map<String, Driver> drivers = new HashMap<>();
    private List<Ride> rides = new ArrayList<>();

    // Register Rider
    public void addRider(Rider rider) {
        riders.put(rider.getUserId(), rider);
    }

    // Register Driver
    public void addDriver(Driver driver) {
        drivers.put(driver.getUserId(), driver);
    }

    // Request Ride
    public Ride requestRide(String rideId, String riderId, double distance)
            throws NoDriverAvailableException {

        Rider rider = riders.get(riderId);

        if (rider == null) {
            throw new IllegalArgumentException("Rider not found");
        }

        Driver availableDriver = findAvailableDriver();

        if (availableDriver == null) {
            throw new NoDriverAvailableException("No drivers available at the moment");
        }

        Vehicle vehicle = availableDriver.getVehicle();

        Ride ride = new Ride(rideId, rider, distance);
        ride.assignDriver(availableDriver, vehicle);

        rides.add(ride);

        return ride;
    }

    // Find available driver
    private Driver findAvailableDriver() {
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable()) {
                return driver;
            }
        }
        return null;
    }

    // Start Ride
    public void startRide(String rideId) {
        Ride ride = findRideById(rideId);
        if (ride != null) {
            ride.startRide();
        }
    }

    // Complete Ride
    public void completeRide(String rideId) {
        Ride ride = findRideById(rideId);
        if (ride != null) {
            ride.completeRide();
        }
    }

    // Display all rides
    public void displayAllRides() {
        for (Ride ride : rides) {
            ride.displayRideDetails();
        }
    }

    // Getter for file saving
    public List<Ride> getRides() {
        return rides;
    }

    // Helper method
    private Ride findRideById(String rideId) {
        for (Ride ride : rides) {
            if (ride.getRideId().equals(rideId)) {
                return ride;
            }
        }
        return null;
    }
}