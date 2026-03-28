package zipgo.service;

import zipgo.model.*;
import zipgo.exception.*;

import java.util.*;

public class RideManager {

    private Map<String, Rider> riders = new HashMap<>();
    private Map<String, Driver> drivers = new HashMap<>();
    private List<Ride> rides = new ArrayList<>();

    public void addRider(Rider rider) {
        riders.put(rider.getUserId(), rider);
    }

    public void addDriver(Driver driver) {
        drivers.put(driver.getUserId(), driver);
    }

    public Rider getRider(String riderId) {
        return riders.get(riderId);
    }

    public Driver getDriver(String driverId) {
        return drivers.get(driverId);
    }

    public Ride requestRide(String rideId, String riderId, double distance)
            throws NoDriverAvailableException {

        Rider rider = riders.get(riderId);
        if (rider == null) {
            throw new IllegalArgumentException("Rider not found");
        }

        Driver driver = findAvailableDriver();
        if (driver == null) {
            throw new NoDriverAvailableException("No drivers available");
        }

        Ride ride = new Ride(rideId, rider, distance);
        ride.assignDriver(driver, driver.getVehicle());
        rides.add(ride);

        return ride;
    }

    private Driver findAvailableDriver() {
        for (Driver d : drivers.values()) {
            if (d.isAvailable()) return d;
        }
        return null;
    }

    public void startRide(String rideId) throws InvalidRideStateException {
        Ride ride = findRideById(rideId);
        if (ride != null) ride.startRide();
    }

    public void completeRide(String rideId) throws InvalidRideStateException {
        Ride ride = findRideById(rideId);
        if (ride != null) ride.completeRide();
    }

    public void cancelRide(String rideId) throws InvalidRideStateException {
        Ride ride = findRideById(rideId);
        if (ride != null) ride.cancelRide();
    }

    public List<Ride> getRides() {
        return rides;
    }

    private Ride findRideById(String id) {
        for (Ride r : rides) {
            if (r.getRideId().equals(id)) return r;
        }
        return null;
    }
}