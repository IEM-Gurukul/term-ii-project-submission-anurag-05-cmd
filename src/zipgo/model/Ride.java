package zipgo.model;

public class Ride {
    private String rideId;
    private Rider rider;
    private Driver driver;
    private Vehicle vehicle;
    private double distance;
    private double fare;
    private RideStatus status;

    public Ride(String rideId, Rider rider, double distance) {
        this.rideId = rideId;
        this.rider = rider;
        this.distance = distance;
        this.status = RideStatus.REQUESTED;
    }

    public String getRideId() {
        return rideId;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getDistance() {
        return distance;
    }

    public double getFare() {
        return fare;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void assignDriver(Driver driver, Vehicle vehicle) {
        this.driver = driver;
        this.vehicle = vehicle;
        this.status = RideStatus.ACCEPTED;
        driver.setAvailable(false);
    }

    public void startRide() {
        this.status = RideStatus.IN_PROGRESS;
    }

    public void completeRide() {
        this.fare = vehicle.calculateFare(distance);
        this.status = RideStatus.COMPLETED;
        driver.setAvailable(true);
    }

    public void cancelRide() {
        this.status = RideStatus.CANCELLED;
        if (driver != null) {
            driver.setAvailable(true);
        }
    }

    public void displayRideDetails() {
        System.out.println("Ride ID: " + rideId +
                ", Rider: " + rider.getName() +
                ", Driver: " + (driver != null ? driver.getName() : "Not Assigned") +
                ", Distance: " + distance +
                ", Fare: " + fare +
                ", Status: " + status);
    }
}