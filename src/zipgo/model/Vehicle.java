package zipgo.model;

public abstract class Vehicle {
    private String vehicleNumber;
    private double baseFarePerKm;

    public Vehicle(String vehicleNumber, double baseFarePerKm) {
        this.vehicleNumber = vehicleNumber;
        this.baseFarePerKm = baseFarePerKm;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public double getBaseFarePerKm() {
        return baseFarePerKm;
    }

    public abstract double calculateFare(double distance);
}