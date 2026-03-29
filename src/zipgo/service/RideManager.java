package zipgo.service;

import zipgo.model.*;
import zipgo.exception.*;

import java.util.*;

public class RideManager {

    private Map<String, Rider> riders = new HashMap<>();
    private Map<String, Driver> drivers = new HashMap<>();

    private List<Ride> rides = new ArrayList<>();
    private Queue<Ride> rideQueue = new LinkedList<>();

    public synchronized void addRider(Rider rider) {
        riders.put(rider.getUserId(), rider);
    }

    public synchronized void addDriver(Driver driver) {
        drivers.put(driver.getUserId(), driver);
    }

    public Rider getRider(String id) {
        return riders.get(id);
    }

    public Driver getDriver(String id) {
        return drivers.get(id);
    }

    public synchronized Ride requestRide(String rideId, String riderId, double distance) {
        Rider rider = riders.get(riderId);
        if (rider == null) throw new IllegalArgumentException("Rider not found");

        Ride ride = new Ride(rideId, rider, distance);
        rideQueue.add(ride);
        rides.add(ride);

        System.out.println("Ride requested. Waiting for driver...");

        return ride;
    }

    public synchronized Ride assignRideToDriver(Driver driver) {
        if (!driver.isAvailable()) return null;

        Ride ride = rideQueue.poll();
        if (ride == null) return null;

        ride.assignDriver(driver, driver.getVehicle());
        return ride;
    }

    public synchronized void startRide(String rideId) throws InvalidRideStateException {
        Ride r = findRideById(rideId);
        if (r != null) r.startRide();
    }

    public synchronized void completeRide(String rideId) throws InvalidRideStateException {
        Ride r = findRideById(rideId);
        if (r != null) r.completeRide();
    }

    public synchronized void cancelRide(String rideId) throws InvalidRideStateException {
        Ride r = findRideById(rideId);
        if (r != null) r.cancelRide();
    }

    public int getTotalRides() {
        return rides.size();
    }

    public double getTotalEarnings() {
        double total = 0;
        for (Ride r : rides) {
            if (r.getStatus() == RideStatus.COMPLETED) {
                total += r.getFare();
            }
        }
        return total;
    }

    private Ride findRideById(String id) {
        for (Ride r : rides) {
            if (r.getRideId().equals(id)) return r;
        }
        return null;
    }
}