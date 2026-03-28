package zipgo.model;

public class Bike extends Vehicle {

    public Bike(String vehicleNumber) {
        super(vehicleNumber, 8.0);
    }

    @Override
    public double calculateFare(double distance) {
        return distance * getBaseFarePerKm();
    }
}